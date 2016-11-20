package com.sude.sd.web.rest;

import com.sude.sd.SudeApp;

import com.sude.sd.domain.SdDriver;
import com.sude.sd.repository.SdDriverRepository;
import com.sude.sd.service.SdDriverService;
import com.sude.sd.repository.search.SdDriverSearchRepository;

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
 * Test class for the SdDriverResource REST controller.
 *
 * @see SdDriverResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SudeApp.class)
public class SdDriverResourceIntTest {

    private static final String DEFAULT_DRIVER_NAME = "AAAAA";
    private static final String UPDATED_DRIVER_NAME = "BBBBB";

    private static final String DEFAULT_SEX = "AAAAA";
    private static final String UPDATED_SEX = "BBBBB";

    private static final Long DEFAULT_MOBILE_PHONE = 1L;
    private static final Long UPDATED_MOBILE_PHONE = 2L;

    private static final String DEFAULT_REMARK = "AAAAA";
    private static final String UPDATED_REMARK = "BBBBB";

    @Inject
    private SdDriverRepository sdDriverRepository;

    @Inject
    private SdDriverService sdDriverService;

    @Inject
    private SdDriverSearchRepository sdDriverSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSdDriverMockMvc;

    private SdDriver sdDriver;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SdDriverResource sdDriverResource = new SdDriverResource();
        ReflectionTestUtils.setField(sdDriverResource, "sdDriverService", sdDriverService);
        this.restSdDriverMockMvc = MockMvcBuilders.standaloneSetup(sdDriverResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SdDriver createEntity(EntityManager em) {
        SdDriver sdDriver = new SdDriver()
                .driverName(DEFAULT_DRIVER_NAME)
                .sex(DEFAULT_SEX)
                .mobilePhone(DEFAULT_MOBILE_PHONE)
                .remark(DEFAULT_REMARK);
        return sdDriver;
    }

    @Before
    public void initTest() {
        sdDriverSearchRepository.deleteAll();
        sdDriver = createEntity(em);
    }

    @Test
    @Transactional
    public void createSdDriver() throws Exception {
        int databaseSizeBeforeCreate = sdDriverRepository.findAll().size();

        // Create the SdDriver

        restSdDriverMockMvc.perform(post("/api/sd-drivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sdDriver)))
                .andExpect(status().isCreated());

        // Validate the SdDriver in the database
        List<SdDriver> sdDrivers = sdDriverRepository.findAll();
        assertThat(sdDrivers).hasSize(databaseSizeBeforeCreate + 1);
        SdDriver testSdDriver = sdDrivers.get(sdDrivers.size() - 1);
        assertThat(testSdDriver.getDriverName()).isEqualTo(DEFAULT_DRIVER_NAME);
        assertThat(testSdDriver.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testSdDriver.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testSdDriver.getRemark()).isEqualTo(DEFAULT_REMARK);

        // Validate the SdDriver in ElasticSearch
        SdDriver sdDriverEs = sdDriverSearchRepository.findOne(testSdDriver.getId());
        assertThat(sdDriverEs).isEqualToComparingFieldByField(testSdDriver);
    }

    @Test
    @Transactional
    public void getAllSdDrivers() throws Exception {
        // Initialize the database
        sdDriverRepository.saveAndFlush(sdDriver);

        // Get all the sdDrivers
        restSdDriverMockMvc.perform(get("/api/sd-drivers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sdDriver.getId().intValue())))
                .andExpect(jsonPath("$.[*].driverName").value(hasItem(DEFAULT_DRIVER_NAME.toString())))
                .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
                .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.intValue())))
                .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }

    @Test
    @Transactional
    public void getSdDriver() throws Exception {
        // Initialize the database
        sdDriverRepository.saveAndFlush(sdDriver);

        // Get the sdDriver
        restSdDriverMockMvc.perform(get("/api/sd-drivers/{id}", sdDriver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sdDriver.getId().intValue()))
            .andExpect(jsonPath("$.driverName").value(DEFAULT_DRIVER_NAME.toString()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE.intValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSdDriver() throws Exception {
        // Get the sdDriver
        restSdDriverMockMvc.perform(get("/api/sd-drivers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSdDriver() throws Exception {
        // Initialize the database
        sdDriverService.save(sdDriver);

        int databaseSizeBeforeUpdate = sdDriverRepository.findAll().size();

        // Update the sdDriver
        SdDriver updatedSdDriver = sdDriverRepository.findOne(sdDriver.getId());
        updatedSdDriver
                .driverName(UPDATED_DRIVER_NAME)
                .sex(UPDATED_SEX)
                .mobilePhone(UPDATED_MOBILE_PHONE)
                .remark(UPDATED_REMARK);

        restSdDriverMockMvc.perform(put("/api/sd-drivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSdDriver)))
                .andExpect(status().isOk());

        // Validate the SdDriver in the database
        List<SdDriver> sdDrivers = sdDriverRepository.findAll();
        assertThat(sdDrivers).hasSize(databaseSizeBeforeUpdate);
        SdDriver testSdDriver = sdDrivers.get(sdDrivers.size() - 1);
        assertThat(testSdDriver.getDriverName()).isEqualTo(UPDATED_DRIVER_NAME);
        assertThat(testSdDriver.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testSdDriver.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testSdDriver.getRemark()).isEqualTo(UPDATED_REMARK);

        // Validate the SdDriver in ElasticSearch
        SdDriver sdDriverEs = sdDriverSearchRepository.findOne(testSdDriver.getId());
        assertThat(sdDriverEs).isEqualToComparingFieldByField(testSdDriver);
    }

    @Test
    @Transactional
    public void deleteSdDriver() throws Exception {
        // Initialize the database
        sdDriverService.save(sdDriver);

        int databaseSizeBeforeDelete = sdDriverRepository.findAll().size();

        // Get the sdDriver
        restSdDriverMockMvc.perform(delete("/api/sd-drivers/{id}", sdDriver.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean sdDriverExistsInEs = sdDriverSearchRepository.exists(sdDriver.getId());
        assertThat(sdDriverExistsInEs).isFalse();

        // Validate the database is empty
        List<SdDriver> sdDrivers = sdDriverRepository.findAll();
        assertThat(sdDrivers).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSdDriver() throws Exception {
        // Initialize the database
        sdDriverService.save(sdDriver);

        // Search the sdDriver
        restSdDriverMockMvc.perform(get("/api/_search/sd-drivers?query=id:" + sdDriver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sdDriver.getId().intValue())))
            .andExpect(jsonPath("$.[*].driverName").value(hasItem(DEFAULT_DRIVER_NAME.toString())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }
}
