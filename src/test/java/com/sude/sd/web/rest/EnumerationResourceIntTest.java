package com.sude.sd.web.rest;

import com.sude.sd.SudeApp;

import com.sude.sd.domain.Enumeration;
import com.sude.sd.repository.EnumerationRepository;
import com.sude.sd.service.EnumerationService;
import com.sude.sd.repository.search.EnumerationSearchRepository;

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
 * Test class for the EnumerationResource REST controller.
 *
 * @see EnumerationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SudeApp.class)
public class EnumerationResourceIntTest {

	private static final String DEFAULT_ID = "AAAAA";
	private static final String UPDATED_ID = "BBBBB";
	
    private static final String DEFAULT_IS_DELETE = "AAAAA";
    private static final String UPDATED_IS_DELETE = "BBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final String DEFAULT_ENUM_TYPE_ID = "AAAAA";
    private static final String UPDATED_ENUM_TYPE_ID = "BBBBB";

    @Inject
    private EnumerationRepository enumerationRepository;

    @Inject
    private EnumerationService enumerationService;

    @Inject
    private EnumerationSearchRepository enumerationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEnumerationMockMvc;

    private Enumeration enumeration;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EnumerationResource enumerationResource = new EnumerationResource();
        ReflectionTestUtils.setField(enumerationResource, "enumerationService", enumerationService);
        this.restEnumerationMockMvc = MockMvcBuilders.standaloneSetup(enumerationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enumeration createEntity(EntityManager em) {
        Enumeration enumeration = new Enumeration()
        		.id(DEFAULT_ID)
                .isDelete(DEFAULT_IS_DELETE)
                .description(DEFAULT_DESCRIPTION)
                .enumTypeId(DEFAULT_ENUM_TYPE_ID);
        return enumeration;
    }

    @Before
    public void initTest() {
        enumerationSearchRepository.deleteAll();
        enumeration = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnumeration() throws Exception {
        int databaseSizeBeforeCreate = enumerationRepository.findAll().size();

        // Create the Enumeration

        restEnumerationMockMvc.perform(post("/api/enumerations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enumeration)))
                .andExpect(status().isCreated());

        // Validate the Enumeration in the database
        List<Enumeration> enumerations = enumerationRepository.findAll();
        assertThat(enumerations).hasSize(databaseSizeBeforeCreate + 1);
        Enumeration testEnumeration = enumerations.get(enumerations.size() - 1);
        assertThat(testEnumeration.getIsDelete()).isEqualTo(DEFAULT_IS_DELETE);
        assertThat(testEnumeration.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEnumeration.getEnumTypeId()).isEqualTo(DEFAULT_ENUM_TYPE_ID);

        // Validate the Enumeration in ElasticSearch
        Enumeration enumerationEs = enumerationSearchRepository.findOne(testEnumeration.getId());
        assertThat(enumerationEs).isEqualToComparingFieldByField(testEnumeration);
    }

    @Test
    @Transactional
    public void getAllEnumerations() throws Exception {
        // Initialize the database
        enumerationRepository.saveAndFlush(enumeration);

        // Get all the enumerations
        restEnumerationMockMvc.perform(get("/api/enumerations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(enumeration.getId().toString())))
                .andExpect(jsonPath("$.[*].isDelete").value(hasItem(DEFAULT_IS_DELETE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].enumTypeId").value(hasItem(DEFAULT_ENUM_TYPE_ID.toString())));
    }

    @Test
    @Transactional
    public void getEnumeration() throws Exception {
        // Initialize the database
        enumerationRepository.saveAndFlush(enumeration);

        // Get the enumeration
        restEnumerationMockMvc.perform(get("/api/enumerations/{id}", enumeration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enumeration.getId().toString()))
            .andExpect(jsonPath("$.isDelete").value(DEFAULT_IS_DELETE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.enumTypeId").value(DEFAULT_ENUM_TYPE_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEnumeration() throws Exception {
        // Get the enumeration
        restEnumerationMockMvc.perform(get("/api/enumerations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnumeration() throws Exception {
        // Initialize the database
        enumerationService.save(enumeration);

        int databaseSizeBeforeUpdate = enumerationRepository.findAll().size();

        // Update the enumeration
        Enumeration updatedEnumeration = enumerationRepository.findOne(enumeration.getId());
        updatedEnumeration
        .isDelete(UPDATED_IS_DELETE)
                .description(UPDATED_DESCRIPTION)
                .enumTypeId(UPDATED_ENUM_TYPE_ID);

        restEnumerationMockMvc.perform(put("/api/enumerations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEnumeration)))
                .andExpect(status().isOk());

        // Validate the Enumeration in the database
        List<Enumeration> enumerations = enumerationRepository.findAll();
        assertThat(enumerations).hasSize(databaseSizeBeforeUpdate);
        Enumeration testEnumeration = enumerations.get(enumerations.size() - 1);
        assertThat(testEnumeration.getIsDelete()).isEqualTo(UPDATED_IS_DELETE);
        assertThat(testEnumeration.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEnumeration.getEnumTypeId()).isEqualTo(UPDATED_ENUM_TYPE_ID);

        // Validate the Enumeration in ElasticSearch
        Enumeration enumerationEs = enumerationSearchRepository.findOne(testEnumeration.getId());
        assertThat(enumerationEs).isEqualToComparingFieldByField(testEnumeration);
    }

    @Test
    @Transactional
    public void deleteEnumeration() throws Exception {
        // Initialize the database
        enumerationService.save(enumeration);

        int databaseSizeBeforeDelete = enumerationRepository.findAll().size();

        // Get the enumeration
        restEnumerationMockMvc.perform(delete("/api/enumerations/{id}", enumeration.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean enumerationExistsInEs = enumerationSearchRepository.exists(enumeration.getId());
        assertThat(enumerationExistsInEs).isFalse();

        // Validate the database is empty
        List<Enumeration> enumerations = enumerationRepository.findAll();
        assertThat(enumerations).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEnumeration() throws Exception {
        // Initialize the database
        enumerationService.save(enumeration);

        // Search the enumeration
        restEnumerationMockMvc.perform(get("/api/_search/enumerations?query=id:" + enumeration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enumeration.getId().toString())))
            .andExpect(jsonPath("$.[*].isDelete").value(hasItem(DEFAULT_IS_DELETE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].enumTypeId").value(hasItem(DEFAULT_ENUM_TYPE_ID.toString())));
    }
}
