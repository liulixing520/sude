package com.sude.sd.web.rest;

import com.sude.sd.SudeApp;

import com.sude.sd.domain.SdCompany;
import com.sude.sd.repository.SdCompanyRepository;
import com.sude.sd.repository.search.SdCompanySearchRepository;

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
 * Test class for the SdCompanyResource REST controller.
 *
 * @see SdCompanyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SudeApp.class)
public class SdCompanyResourceIntTest {

    private static final String DEFAULT_COMPANY_NAME = "AAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBB";

    private static final String DEFAULT_PHONE = "AAAAA";
    private static final String UPDATED_PHONE = "BBBBB";

    private static final String DEFAULT_FAX = "AAAAA";
    private static final String UPDATED_FAX = "BBBBB";

    private static final String DEFAULT_POST_CODE = "AAAAA";
    private static final String UPDATED_POST_CODE = "BBBBB";

    @Inject
    private SdCompanyRepository sdCompanyRepository;

    @Inject
    private SdCompanySearchRepository sdCompanySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSdCompanyMockMvc;

    private SdCompany sdCompany;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SdCompanyResource sdCompanyResource = new SdCompanyResource();
        ReflectionTestUtils.setField(sdCompanyResource, "sdCompanySearchRepository", sdCompanySearchRepository);
        ReflectionTestUtils.setField(sdCompanyResource, "sdCompanyRepository", sdCompanyRepository);
        this.restSdCompanyMockMvc = MockMvcBuilders.standaloneSetup(sdCompanyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SdCompany createEntity(EntityManager em) {
        SdCompany sdCompany = new SdCompany()
                .companyName(DEFAULT_COMPANY_NAME)
                .phone(DEFAULT_PHONE)
                .fax(DEFAULT_FAX)
                .postCode(DEFAULT_POST_CODE);
        return sdCompany;
    }

    @Before
    public void initTest() {
        sdCompanySearchRepository.deleteAll();
        sdCompany = createEntity(em);
    }

    @Test
    @Transactional
    public void createSdCompany() throws Exception {
        int databaseSizeBeforeCreate = sdCompanyRepository.findAll().size();

        // Create the SdCompany

        restSdCompanyMockMvc.perform(post("/api/sd-companies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sdCompany)))
                .andExpect(status().isCreated());

        // Validate the SdCompany in the database
        List<SdCompany> sdCompanies = sdCompanyRepository.findAll();
        assertThat(sdCompanies).hasSize(databaseSizeBeforeCreate + 1);
        SdCompany testSdCompany = sdCompanies.get(sdCompanies.size() - 1);
        assertThat(testSdCompany.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testSdCompany.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testSdCompany.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testSdCompany.getPostCode()).isEqualTo(DEFAULT_POST_CODE);

        // Validate the SdCompany in ElasticSearch
        SdCompany sdCompanyEs = sdCompanySearchRepository.findOne(testSdCompany.getId());
        assertThat(sdCompanyEs).isEqualToComparingFieldByField(testSdCompany);
    }

    @Test
    @Transactional
    public void getAllSdCompanies() throws Exception {
        // Initialize the database
        sdCompanyRepository.saveAndFlush(sdCompany);

        // Get all the sdCompanies
        restSdCompanyMockMvc.perform(get("/api/sd-companies?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sdCompany.getId().intValue())))
                .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
                .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
                .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE.toString())));
    }

    @Test
    @Transactional
    public void getSdCompany() throws Exception {
        // Initialize the database
        sdCompanyRepository.saveAndFlush(sdCompany);

        // Get the sdCompany
        restSdCompanyMockMvc.perform(get("/api/sd-companies/{id}", sdCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sdCompany.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.postCode").value(DEFAULT_POST_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSdCompany() throws Exception {
        // Get the sdCompany
        restSdCompanyMockMvc.perform(get("/api/sd-companies/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSdCompany() throws Exception {
        // Initialize the database
        sdCompanyRepository.saveAndFlush(sdCompany);
        sdCompanySearchRepository.save(sdCompany);
        int databaseSizeBeforeUpdate = sdCompanyRepository.findAll().size();

        // Update the sdCompany
        SdCompany updatedSdCompany = sdCompanyRepository.findOne(sdCompany.getId());
        updatedSdCompany
                .companyName(UPDATED_COMPANY_NAME)
                .phone(UPDATED_PHONE)
                .fax(UPDATED_FAX)
                .postCode(UPDATED_POST_CODE);

        restSdCompanyMockMvc.perform(put("/api/sd-companies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSdCompany)))
                .andExpect(status().isOk());

        // Validate the SdCompany in the database
        List<SdCompany> sdCompanies = sdCompanyRepository.findAll();
        assertThat(sdCompanies).hasSize(databaseSizeBeforeUpdate);
        SdCompany testSdCompany = sdCompanies.get(sdCompanies.size() - 1);
        assertThat(testSdCompany.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testSdCompany.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testSdCompany.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testSdCompany.getPostCode()).isEqualTo(UPDATED_POST_CODE);

        // Validate the SdCompany in ElasticSearch
        SdCompany sdCompanyEs = sdCompanySearchRepository.findOne(testSdCompany.getId());
        assertThat(sdCompanyEs).isEqualToComparingFieldByField(testSdCompany);
    }

    @Test
    @Transactional
    public void deleteSdCompany() throws Exception {
        // Initialize the database
        sdCompanyRepository.saveAndFlush(sdCompany);
        sdCompanySearchRepository.save(sdCompany);
        int databaseSizeBeforeDelete = sdCompanyRepository.findAll().size();

        // Get the sdCompany
        restSdCompanyMockMvc.perform(delete("/api/sd-companies/{id}", sdCompany.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean sdCompanyExistsInEs = sdCompanySearchRepository.exists(sdCompany.getId());
        assertThat(sdCompanyExistsInEs).isFalse();

        // Validate the database is empty
        List<SdCompany> sdCompanies = sdCompanyRepository.findAll();
        assertThat(sdCompanies).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSdCompany() throws Exception {
        // Initialize the database
        sdCompanyRepository.saveAndFlush(sdCompany);
        sdCompanySearchRepository.save(sdCompany);

        // Search the sdCompany
        restSdCompanyMockMvc.perform(get("/api/_search/sd-companies?query=id:" + sdCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sdCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE.toString())));
    }
}
