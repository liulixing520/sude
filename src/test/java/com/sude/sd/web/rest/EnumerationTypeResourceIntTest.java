package com.sude.sd.web.rest;

import com.sude.sd.SudeApp;

import com.sude.sd.domain.EnumerationType;
import com.sude.sd.repository.EnumerationTypeRepository;
import com.sude.sd.service.EnumerationTypeService;
import com.sude.sd.repository.search.EnumerationTypeSearchRepository;

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
 * Test class for the EnumerationTypeResource REST controller.
 *
 * @see EnumerationTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SudeApp.class)
public class EnumerationTypeResourceIntTest {

	private static final String DEFAULT_ID = "AAAAA";
	private static final String UPDATED_ID = "BBBBB";
	
    private static final String DEFAULT_IS_DELETE = "AAAAA";
    private static final String UPDATED_IS_DELETE = "BBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private EnumerationTypeRepository enumerationTypeRepository;

    @Inject
    private EnumerationTypeService enumerationTypeService;

    @Inject
    private EnumerationTypeSearchRepository enumerationTypeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEnumerationTypeMockMvc;

    private EnumerationType enumerationType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EnumerationTypeResource enumerationTypeResource = new EnumerationTypeResource();
        ReflectionTestUtils.setField(enumerationTypeResource, "enumerationTypeService", enumerationTypeService);
        this.restEnumerationTypeMockMvc = MockMvcBuilders.standaloneSetup(enumerationTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnumerationType createEntity(EntityManager em) {
        EnumerationType enumerationType = new EnumerationType()
        		.id(DEFAULT_ID)
                .isDelete(DEFAULT_IS_DELETE)
                .description(DEFAULT_DESCRIPTION);
        return enumerationType;
    }

    @Before
    public void initTest() {
        enumerationTypeSearchRepository.deleteAll();
        enumerationType = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnumerationType() throws Exception {
        int databaseSizeBeforeCreate = enumerationTypeRepository.findAll().size();

        // Create the EnumerationType

        restEnumerationTypeMockMvc.perform(post("/api/enumeration-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enumerationType)))
                .andExpect(status().isCreated());

        // Validate the EnumerationType in the database
        List<EnumerationType> enumerationTypes = enumerationTypeRepository.findAll();
        assertThat(enumerationTypes).hasSize(databaseSizeBeforeCreate + 1);
        EnumerationType testEnumerationType = enumerationTypes.get(enumerationTypes.size() - 1);
        assertThat(testEnumerationType.getId()).isEqualTo(DEFAULT_ID);
        assertThat(testEnumerationType.getIsDelete()).isEqualTo(DEFAULT_IS_DELETE);
        assertThat(testEnumerationType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the EnumerationType in ElasticSearch
        EnumerationType enumerationTypeEs = enumerationTypeSearchRepository.findOne(testEnumerationType.getId());
        assertThat(enumerationTypeEs).isEqualToComparingFieldByField(testEnumerationType);
    }

    @Test
    @Transactional
    public void getAllEnumerationTypes() throws Exception {
        // Initialize the database
        enumerationTypeRepository.saveAndFlush(enumerationType);

        // Get all the enumerationTypes
        restEnumerationTypeMockMvc.perform(get("/api/enumeration-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(enumerationType.getId().toString())))
                .andExpect(jsonPath("$.[*].isDelete").value(hasItem(DEFAULT_IS_DELETE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getEnumerationType() throws Exception {
        // Initialize the database
        enumerationTypeRepository.saveAndFlush(enumerationType);

        // Get the enumerationType
        restEnumerationTypeMockMvc.perform(get("/api/enumeration-types/{id}", enumerationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enumerationType.getId().toString()))
            .andExpect(jsonPath("$.isDelete").value(DEFAULT_IS_DELETE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEnumerationType() throws Exception {
        // Get the enumerationType
        restEnumerationTypeMockMvc.perform(get("/api/enumeration-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnumerationType() throws Exception {
        // Initialize the database
        enumerationTypeService.save(enumerationType);

        int databaseSizeBeforeUpdate = enumerationTypeRepository.findAll().size();

        // Update the enumerationType
        EnumerationType updatedEnumerationType = enumerationTypeRepository.findOne(enumerationType.getId());
        updatedEnumerationType
                .isDelete(UPDATED_IS_DELETE)
                .description(UPDATED_DESCRIPTION);

        restEnumerationTypeMockMvc.perform(put("/api/enumeration-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEnumerationType)))
                .andExpect(status().isOk());

        // Validate the EnumerationType in the database
        List<EnumerationType> enumerationTypes = enumerationTypeRepository.findAll();
        assertThat(enumerationTypes).hasSize(databaseSizeBeforeUpdate);
        EnumerationType testEnumerationType = enumerationTypes.get(enumerationTypes.size() - 1);
        assertThat(testEnumerationType.getIsDelete()).isEqualTo(UPDATED_IS_DELETE);
        assertThat(testEnumerationType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the EnumerationType in ElasticSearch
        EnumerationType enumerationTypeEs = enumerationTypeSearchRepository.findOne(testEnumerationType.getId());
        assertThat(enumerationTypeEs).isEqualToComparingFieldByField(testEnumerationType);
    }

    @Test
    @Transactional
    public void deleteEnumerationType() throws Exception {
        // Initialize the database
        enumerationTypeService.save(enumerationType);

        int databaseSizeBeforeDelete = enumerationTypeRepository.findAll().size();

        // Get the enumerationType
        restEnumerationTypeMockMvc.perform(delete("/api/enumeration-types/{id}", enumerationType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean enumerationTypeExistsInEs = enumerationTypeSearchRepository.exists(enumerationType.getId());
        assertThat(enumerationTypeExistsInEs).isFalse();

        // Validate the database is empty
        List<EnumerationType> enumerationTypes = enumerationTypeRepository.findAll();
        assertThat(enumerationTypes).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEnumerationType() throws Exception {
        // Initialize the database
        enumerationTypeService.save(enumerationType);

        // Search the enumerationType
        restEnumerationTypeMockMvc.perform(get("/api/_search/enumeration-types?query=id:" + enumerationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enumerationType.getId().toString())))
            .andExpect(jsonPath("$.[*].isDelete").value(hasItem(DEFAULT_IS_DELETE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
}
