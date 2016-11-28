package com.sude.sd.web.rest;

import com.sude.sd.SudeApp;

import com.sude.sd.domain.SdStation;
import com.sude.sd.repository.SdStationRepository;
import com.sude.sd.service.SdStationService;
import com.sude.sd.repository.search.SdStationSearchRepository;

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
 * Test class for the SdStationResource REST controller.
 *
 * @see SdStationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SudeApp.class)
public class SdStationResourceIntTest {

    private static final String DEFAULT_STATION_NAME = "AAAAA";
    private static final String UPDATED_STATION_NAME = "BBBBB";

    private static final String DEFAULT_STATION_NM = "AAAAA";
    private static final String UPDATED_STATION_NM = "BBBBB";

    @Inject
    private SdStationRepository sdStationRepository;

    @Inject
    private SdStationService sdStationService;

    @Inject
    private SdStationSearchRepository sdStationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSdStationMockMvc;

    private SdStation sdStation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SdStationResource sdStationResource = new SdStationResource();
        ReflectionTestUtils.setField(sdStationResource, "sdStationService", sdStationService);
        this.restSdStationMockMvc = MockMvcBuilders.standaloneSetup(sdStationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SdStation createEntity(EntityManager em) {
        SdStation sdStation = new SdStation()
                .stationName(DEFAULT_STATION_NAME)
                .stationNM(DEFAULT_STATION_NM);
        return sdStation;
    }

    @Before
    public void initTest() {
        sdStationSearchRepository.deleteAll();
        sdStation = createEntity(em);
    }

    @Test
    @Transactional
    public void createSdStation() throws Exception {
        int databaseSizeBeforeCreate = sdStationRepository.findAll().size();

        // Create the SdStation

        restSdStationMockMvc.perform(post("/api/sd-stations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sdStation)))
                .andExpect(status().isCreated());

        // Validate the SdStation in the database
        List<SdStation> sdStations = sdStationRepository.findAll();
        assertThat(sdStations).hasSize(databaseSizeBeforeCreate + 1);
        SdStation testSdStation = sdStations.get(sdStations.size() - 1);
        assertThat(testSdStation.getStationName()).isEqualTo(DEFAULT_STATION_NAME);
        assertThat(testSdStation.getStationNM()).isEqualTo(DEFAULT_STATION_NM);

        // Validate the SdStation in ElasticSearch
        SdStation sdStationEs = sdStationSearchRepository.findOne(testSdStation.getId());
        assertThat(sdStationEs).isEqualToComparingFieldByField(testSdStation);
    }

    @Test
    @Transactional
    public void getAllSdStations() throws Exception {
        // Initialize the database
        sdStationRepository.saveAndFlush(sdStation);

        // Get all the sdStations
        restSdStationMockMvc.perform(get("/api/sd-stations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sdStation.getId().intValue())))
                .andExpect(jsonPath("$.[*].stationName").value(hasItem(DEFAULT_STATION_NAME.toString())))
                .andExpect(jsonPath("$.[*].stationNM").value(hasItem(DEFAULT_STATION_NM.toString())));
    }

    @Test
    @Transactional
    public void getSdStation() throws Exception {
        // Initialize the database
        sdStationRepository.saveAndFlush(sdStation);

        // Get the sdStation
        restSdStationMockMvc.perform(get("/api/sd-stations/{id}", sdStation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sdStation.getId().intValue()))
            .andExpect(jsonPath("$.stationName").value(DEFAULT_STATION_NAME.toString()))
            .andExpect(jsonPath("$.stationNM").value(DEFAULT_STATION_NM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSdStation() throws Exception {
        // Get the sdStation
        restSdStationMockMvc.perform(get("/api/sd-stations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSdStation() throws Exception {
        // Initialize the database
        sdStationService.save(sdStation);

        int databaseSizeBeforeUpdate = sdStationRepository.findAll().size();

        // Update the sdStation
        SdStation updatedSdStation = sdStationRepository.findOne(sdStation.getId());
        updatedSdStation
                .stationName(UPDATED_STATION_NAME)
                .stationNM(UPDATED_STATION_NM);

        restSdStationMockMvc.perform(put("/api/sd-stations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSdStation)))
                .andExpect(status().isOk());

        // Validate the SdStation in the database
        List<SdStation> sdStations = sdStationRepository.findAll();
        assertThat(sdStations).hasSize(databaseSizeBeforeUpdate);
        SdStation testSdStation = sdStations.get(sdStations.size() - 1);
        assertThat(testSdStation.getStationName()).isEqualTo(UPDATED_STATION_NAME);
        assertThat(testSdStation.getStationNM()).isEqualTo(UPDATED_STATION_NM);

        // Validate the SdStation in ElasticSearch
        SdStation sdStationEs = sdStationSearchRepository.findOne(testSdStation.getId());
        assertThat(sdStationEs).isEqualToComparingFieldByField(testSdStation);
    }

    @Test
    @Transactional
    public void deleteSdStation() throws Exception {
        // Initialize the database
        sdStationService.save(sdStation);

        int databaseSizeBeforeDelete = sdStationRepository.findAll().size();

        // Get the sdStation
        restSdStationMockMvc.perform(delete("/api/sd-stations/{id}", sdStation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean sdStationExistsInEs = sdStationSearchRepository.exists(sdStation.getId());
        assertThat(sdStationExistsInEs).isFalse();

        // Validate the database is empty
        List<SdStation> sdStations = sdStationRepository.findAll();
        assertThat(sdStations).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSdStation() throws Exception {
        // Initialize the database
        sdStationService.save(sdStation);

        // Search the sdStation
        restSdStationMockMvc.perform(get("/api/_search/sd-stations?query=id:" + sdStation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sdStation.getId().intValue())))
            .andExpect(jsonPath("$.[*].stationName").value(hasItem(DEFAULT_STATION_NAME.toString())))
            .andExpect(jsonPath("$.[*].stationNM").value(hasItem(DEFAULT_STATION_NM.toString())));
    }
}
