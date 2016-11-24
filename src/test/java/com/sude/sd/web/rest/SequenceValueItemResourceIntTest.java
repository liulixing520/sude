package com.sude.sd.web.rest;

import com.sude.sd.SudeApp;

import com.sude.sd.domain.SequenceValueItem;
import com.sude.sd.repository.SequenceValueItemRepository;
import com.sude.sd.service.SequenceValueItemService;
import com.sude.sd.repository.search.SequenceValueItemSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SequenceValueItemResource REST controller.
 *
 * @see SequenceValueItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SudeApp.class)
public class SequenceValueItemResourceIntTest {

    private static final Long DEFAULT_SEQ_ID = 1L;
    private static final Long UPDATED_SEQ_ID = 2L;

    @Inject
    private SequenceValueItemRepository sequenceValueItemRepository;

    @Inject
    private SequenceValueItemService sequenceValueItemService;

    @Inject
    private SequenceValueItemSearchRepository sequenceValueItemSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSequenceValueItemMockMvc;

    private SequenceValueItem sequenceValueItem;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SequenceValueItemResource sequenceValueItemResource = new SequenceValueItemResource();
        ReflectionTestUtils.setField(sequenceValueItemResource, "sequenceValueItemService", sequenceValueItemService);
        this.restSequenceValueItemMockMvc = MockMvcBuilders.standaloneSetup(sequenceValueItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SequenceValueItem createEntity(EntityManager em) {
        SequenceValueItem sequenceValueItem = new SequenceValueItem()
                .seqId(DEFAULT_SEQ_ID);
        return sequenceValueItem;
    }

    @Before
    public void initTest() {
        sequenceValueItemSearchRepository.deleteAll();
        sequenceValueItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createSequenceValueItem() throws Exception {
        int databaseSizeBeforeCreate = sequenceValueItemRepository.findAll().size();

        // Create the SequenceValueItem

        restSequenceValueItemMockMvc.perform(post("/api/sequence-value-items")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sequenceValueItem)))
                .andExpect(status().isCreated());

        // Validate the SequenceValueItem in the database
        List<SequenceValueItem> sequenceValueItems = sequenceValueItemRepository.findAll();
        assertThat(sequenceValueItems).hasSize(databaseSizeBeforeCreate + 1);
        SequenceValueItem testSequenceValueItem = sequenceValueItems.get(sequenceValueItems.size() - 1);
        assertThat(testSequenceValueItem.getSeqId()).isEqualTo(DEFAULT_SEQ_ID);

        // Validate the SequenceValueItem in ElasticSearch
        SequenceValueItem sequenceValueItemEs = sequenceValueItemSearchRepository.findOne(testSequenceValueItem.getId());
        assertThat(sequenceValueItemEs).isEqualToComparingFieldByField(testSequenceValueItem);
    }

    @Test
    @Transactional
    public void getAllSequenceValueItems() throws Exception {
        // Initialize the database
        sequenceValueItemRepository.saveAndFlush(sequenceValueItem);

        // Get all the sequenceValueItems
        restSequenceValueItemMockMvc.perform(get("/api/sequence-value-items?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sequenceValueItem.getId().intValue())))
                .andExpect(jsonPath("$.[*].seqId").value(hasItem(DEFAULT_SEQ_ID.intValue())));
    }

    @Test
    @Transactional
    public void getSequenceValueItem() throws Exception {
        // Initialize the database
        sequenceValueItemRepository.saveAndFlush(sequenceValueItem);

        // Get the sequenceValueItem
        restSequenceValueItemMockMvc.perform(get("/api/sequence-value-items/{id}", sequenceValueItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sequenceValueItem.getId().intValue()))
            .andExpect(jsonPath("$.seqId").value(DEFAULT_SEQ_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSequenceValueItem() throws Exception {
        // Get the sequenceValueItem
        restSequenceValueItemMockMvc.perform(get("/api/sequence-value-items/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSequenceValueItem() throws Exception {
        // Initialize the database
        sequenceValueItemService.save(sequenceValueItem);

        int databaseSizeBeforeUpdate = sequenceValueItemRepository.findAll().size();

        // Update the sequenceValueItem
        SequenceValueItem updatedSequenceValueItem = sequenceValueItemRepository.findOne(sequenceValueItem.getId());
        updatedSequenceValueItem
                .seqId(UPDATED_SEQ_ID);

        restSequenceValueItemMockMvc.perform(put("/api/sequence-value-items")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSequenceValueItem)))
                .andExpect(status().isOk());

        // Validate the SequenceValueItem in the database
        List<SequenceValueItem> sequenceValueItems = sequenceValueItemRepository.findAll();
        assertThat(sequenceValueItems).hasSize(databaseSizeBeforeUpdate);
        SequenceValueItem testSequenceValueItem = sequenceValueItems.get(sequenceValueItems.size() - 1);
        assertThat(testSequenceValueItem.getSeqId()).isEqualTo(UPDATED_SEQ_ID);

        // Validate the SequenceValueItem in ElasticSearch
        SequenceValueItem sequenceValueItemEs = sequenceValueItemSearchRepository.findOne(testSequenceValueItem.getId());
        assertThat(sequenceValueItemEs).isEqualToComparingFieldByField(testSequenceValueItem);
    }

    @Test
    @Transactional
    public void deleteSequenceValueItem() throws Exception {
        // Initialize the database
        sequenceValueItemService.save(sequenceValueItem);

        int databaseSizeBeforeDelete = sequenceValueItemRepository.findAll().size();

        // Get the sequenceValueItem
        restSequenceValueItemMockMvc.perform(delete("/api/sequence-value-items/{id}", sequenceValueItem.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean sequenceValueItemExistsInEs = sequenceValueItemSearchRepository.exists(sequenceValueItem.getId());
        assertThat(sequenceValueItemExistsInEs).isFalse();

        // Validate the database is empty
        List<SequenceValueItem> sequenceValueItems = sequenceValueItemRepository.findAll();
        assertThat(sequenceValueItems).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSequenceValueItem() throws Exception {
        // Initialize the database
        sequenceValueItemService.save(sequenceValueItem);

        // Search the sequenceValueItem
        restSequenceValueItemMockMvc.perform(get("/api/_search/sequence-value-items?query=id:" + sequenceValueItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sequenceValueItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].seqId").value(hasItem(DEFAULT_SEQ_ID.intValue())));
    }
}
