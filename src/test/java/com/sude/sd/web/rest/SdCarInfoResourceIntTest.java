package com.sude.sd.web.rest;

import com.sude.sd.SudeApp;

import com.sude.sd.domain.SdCarInfo;
import com.sude.sd.repository.SdCarInfoRepository;
import com.sude.sd.service.SdCarInfoService;
import com.sude.sd.repository.search.SdCarInfoSearchRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SdCarInfoResource REST controller.
 *
 * @see SdCarInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SudeApp.class)
public class SdCarInfoResourceIntTest {

    private static final String DEFAULT_CAR_NO = "AAAAA";
    private static final String UPDATED_CAR_NO = "BBBBB";

    private static final String DEFAULT_CAR_TYPE = "AAAAA";
    private static final String UPDATED_CAR_TYPE = "BBBBB";

    private static final String DEFAULT_ENGINE_NUMBER = "AAAAA";
    private static final String UPDATED_ENGINE_NUMBER = "BBBBB";

    private static final LocalDate DEFAULT_BUY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BUY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CHECK_LOAD = "AAAAA";
    private static final String UPDATED_CHECK_LOAD = "BBBBB";

    private static final String DEFAULT_CHECK_VOLUME = "AAAAA";
    private static final String UPDATED_CHECK_VOLUME = "BBBBB";

    private static final Long DEFAULT_CAR_LENGTH = 1L;
    private static final Long UPDATED_CAR_LENGTH = 2L;

    private static final Long DEFAULT_CAR_WIDTH = 1L;
    private static final Long UPDATED_CAR_WIDTH = 2L;

    private static final Long DEFAULT_CAR_HEIGHT = 1L;
    private static final Long UPDATED_CAR_HEIGHT = 2L;

    private static final String DEFAULT_VEHICLE_NO = "AAAAA";
    private static final String UPDATED_VEHICLE_NO = "BBBBB";

    private static final String DEFAULT_POLICY_NO = "AAAAA";
    private static final String UPDATED_POLICY_NO = "BBBBB";

    private static final String DEFAULT_CARRIER = "AAAAA";
    private static final String UPDATED_CARRIER = "BBBBB";

    private static final String DEFAULT_RUN_NUMBER = "AAAAA";
    private static final String UPDATED_RUN_NUMBER = "BBBBB";

    @Inject
    private SdCarInfoRepository sdCarInfoRepository;

    @Inject
    private SdCarInfoService sdCarInfoService;

    @Inject
    private SdCarInfoSearchRepository sdCarInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSdCarInfoMockMvc;

    private SdCarInfo sdCarInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SdCarInfoResource sdCarInfoResource = new SdCarInfoResource();
        ReflectionTestUtils.setField(sdCarInfoResource, "sdCarInfoService", sdCarInfoService);
        this.restSdCarInfoMockMvc = MockMvcBuilders.standaloneSetup(sdCarInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SdCarInfo createEntity(EntityManager em) {
        SdCarInfo sdCarInfo = new SdCarInfo()
                .carType(DEFAULT_CAR_TYPE)
                .engineNumber(DEFAULT_ENGINE_NUMBER)
                .buyDate(DEFAULT_BUY_DATE)
                .checkLoad(DEFAULT_CHECK_LOAD)
                .checkVolume(DEFAULT_CHECK_VOLUME)
                .carLength(DEFAULT_CAR_LENGTH)
                .carWidth(DEFAULT_CAR_WIDTH)
                .carHeight(DEFAULT_CAR_HEIGHT)
                .vehicleNo(DEFAULT_VEHICLE_NO)
                .policyNo(DEFAULT_POLICY_NO)
                .carrier(DEFAULT_CARRIER)
                .runNumber(DEFAULT_RUN_NUMBER);
        return sdCarInfo;
    }

    @Before
    public void initTest() {
        sdCarInfoSearchRepository.deleteAll();
        sdCarInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createSdCarInfo() throws Exception {
        int databaseSizeBeforeCreate = sdCarInfoRepository.findAll().size();

        // Create the SdCarInfo

        restSdCarInfoMockMvc.perform(post("/api/sd-car-infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sdCarInfo)))
                .andExpect(status().isCreated());

        // Validate the SdCarInfo in the database
        List<SdCarInfo> sdCarInfos = sdCarInfoRepository.findAll();
        assertThat(sdCarInfos).hasSize(databaseSizeBeforeCreate + 1);
        SdCarInfo testSdCarInfo = sdCarInfos.get(sdCarInfos.size() - 1);
        assertThat(testSdCarInfo.getCarType()).isEqualTo(DEFAULT_CAR_TYPE);
        assertThat(testSdCarInfo.getEngineNumber()).isEqualTo(DEFAULT_ENGINE_NUMBER);
        assertThat(testSdCarInfo.getBuyDate()).isEqualTo(DEFAULT_BUY_DATE);
        assertThat(testSdCarInfo.getCheckLoad()).isEqualTo(DEFAULT_CHECK_LOAD);
        assertThat(testSdCarInfo.getCheckVolume()).isEqualTo(DEFAULT_CHECK_VOLUME);
        assertThat(testSdCarInfo.getCarLength()).isEqualTo(DEFAULT_CAR_LENGTH);
        assertThat(testSdCarInfo.getCarWidth()).isEqualTo(DEFAULT_CAR_WIDTH);
        assertThat(testSdCarInfo.getCarHeight()).isEqualTo(DEFAULT_CAR_HEIGHT);
        assertThat(testSdCarInfo.getVehicleNo()).isEqualTo(DEFAULT_VEHICLE_NO);
        assertThat(testSdCarInfo.getPolicyNo()).isEqualTo(DEFAULT_POLICY_NO);
        assertThat(testSdCarInfo.getCarrier()).isEqualTo(DEFAULT_CARRIER);
        assertThat(testSdCarInfo.getRunNumber()).isEqualTo(DEFAULT_RUN_NUMBER);

        // Validate the SdCarInfo in ElasticSearch
        SdCarInfo sdCarInfoEs = sdCarInfoSearchRepository.findOne(testSdCarInfo.getId());
        assertThat(sdCarInfoEs).isEqualToComparingFieldByField(testSdCarInfo);
    }

    @Test
    @Transactional
    public void getAllSdCarInfos() throws Exception {
        // Initialize the database
        sdCarInfoRepository.saveAndFlush(sdCarInfo);

        // Get all the sdCarInfos
        restSdCarInfoMockMvc.perform(get("/api/sd-car-infos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sdCarInfo.getId())))
                .andExpect(jsonPath("$.[*].carNo").value(hasItem(DEFAULT_CAR_NO.toString())))
                .andExpect(jsonPath("$.[*].carType").value(hasItem(DEFAULT_CAR_TYPE.toString())))
                .andExpect(jsonPath("$.[*].engineNumber").value(hasItem(DEFAULT_ENGINE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].buyDate").value(hasItem(DEFAULT_BUY_DATE.toString())))
                .andExpect(jsonPath("$.[*].checkLoad").value(hasItem(DEFAULT_CHECK_LOAD.toString())))
                .andExpect(jsonPath("$.[*].checkVolume").value(hasItem(DEFAULT_CHECK_VOLUME.toString())))
                .andExpect(jsonPath("$.[*].carLength").value(hasItem(DEFAULT_CAR_LENGTH.intValue())))
                .andExpect(jsonPath("$.[*].carWidth").value(hasItem(DEFAULT_CAR_WIDTH.intValue())))
                .andExpect(jsonPath("$.[*].carHeight").value(hasItem(DEFAULT_CAR_HEIGHT.intValue())))
                .andExpect(jsonPath("$.[*].vehicleNo").value(hasItem(DEFAULT_VEHICLE_NO.toString())))
                .andExpect(jsonPath("$.[*].policyNo").value(hasItem(DEFAULT_POLICY_NO.toString())))
                .andExpect(jsonPath("$.[*].carrier").value(hasItem(DEFAULT_CARRIER.toString())))
                .andExpect(jsonPath("$.[*].runNumber").value(hasItem(DEFAULT_RUN_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getSdCarInfo() throws Exception {
        // Initialize the database
        sdCarInfoRepository.saveAndFlush(sdCarInfo);

        // Get the sdCarInfo
        restSdCarInfoMockMvc.perform(get("/api/sd-car-infos/{id}", sdCarInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sdCarInfo.getId()))
            .andExpect(jsonPath("$.carNo").value(DEFAULT_CAR_NO.toString()))
            .andExpect(jsonPath("$.carType").value(DEFAULT_CAR_TYPE.toString()))
            .andExpect(jsonPath("$.engineNumber").value(DEFAULT_ENGINE_NUMBER.toString()))
            .andExpect(jsonPath("$.buyDate").value(DEFAULT_BUY_DATE.toString()))
            .andExpect(jsonPath("$.checkLoad").value(DEFAULT_CHECK_LOAD.toString()))
            .andExpect(jsonPath("$.checkVolume").value(DEFAULT_CHECK_VOLUME.toString()))
            .andExpect(jsonPath("$.carLength").value(DEFAULT_CAR_LENGTH.intValue()))
            .andExpect(jsonPath("$.carWidth").value(DEFAULT_CAR_WIDTH.intValue()))
            .andExpect(jsonPath("$.carHeight").value(DEFAULT_CAR_HEIGHT.intValue()))
            .andExpect(jsonPath("$.vehicleNo").value(DEFAULT_VEHICLE_NO.toString()))
            .andExpect(jsonPath("$.policyNo").value(DEFAULT_POLICY_NO.toString()))
            .andExpect(jsonPath("$.carrier").value(DEFAULT_CARRIER.toString()))
            .andExpect(jsonPath("$.runNumber").value(DEFAULT_RUN_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSdCarInfo() throws Exception {
        // Get the sdCarInfo
        restSdCarInfoMockMvc.perform(get("/api/sd-car-infos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSdCarInfo() throws Exception {
        // Initialize the database
        sdCarInfoService.save(sdCarInfo);

        int databaseSizeBeforeUpdate = sdCarInfoRepository.findAll().size();

        // Update the sdCarInfo
        SdCarInfo updatedSdCarInfo = sdCarInfoRepository.findOne(sdCarInfo.getId());
        updatedSdCarInfo
                .carType(UPDATED_CAR_TYPE)
                .engineNumber(UPDATED_ENGINE_NUMBER)
                .buyDate(UPDATED_BUY_DATE)
                .checkLoad(UPDATED_CHECK_LOAD)
                .checkVolume(UPDATED_CHECK_VOLUME)
                .carLength(UPDATED_CAR_LENGTH)
                .carWidth(UPDATED_CAR_WIDTH)
                .carHeight(UPDATED_CAR_HEIGHT)
                .vehicleNo(UPDATED_VEHICLE_NO)
                .policyNo(UPDATED_POLICY_NO)
                .carrier(UPDATED_CARRIER)
                .runNumber(UPDATED_RUN_NUMBER);

        restSdCarInfoMockMvc.perform(put("/api/sd-car-infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSdCarInfo)))
                .andExpect(status().isOk());

        // Validate the SdCarInfo in the database
        List<SdCarInfo> sdCarInfos = sdCarInfoRepository.findAll();
        assertThat(sdCarInfos).hasSize(databaseSizeBeforeUpdate);
        SdCarInfo testSdCarInfo = sdCarInfos.get(sdCarInfos.size() - 1);
        assertThat(testSdCarInfo.getCarType()).isEqualTo(UPDATED_CAR_TYPE);
        assertThat(testSdCarInfo.getEngineNumber()).isEqualTo(UPDATED_ENGINE_NUMBER);
        assertThat(testSdCarInfo.getBuyDate()).isEqualTo(UPDATED_BUY_DATE);
        assertThat(testSdCarInfo.getCheckLoad()).isEqualTo(UPDATED_CHECK_LOAD);
        assertThat(testSdCarInfo.getCheckVolume()).isEqualTo(UPDATED_CHECK_VOLUME);
        assertThat(testSdCarInfo.getCarLength()).isEqualTo(UPDATED_CAR_LENGTH);
        assertThat(testSdCarInfo.getCarWidth()).isEqualTo(UPDATED_CAR_WIDTH);
        assertThat(testSdCarInfo.getCarHeight()).isEqualTo(UPDATED_CAR_HEIGHT);
        assertThat(testSdCarInfo.getVehicleNo()).isEqualTo(UPDATED_VEHICLE_NO);
        assertThat(testSdCarInfo.getPolicyNo()).isEqualTo(UPDATED_POLICY_NO);
        assertThat(testSdCarInfo.getCarrier()).isEqualTo(UPDATED_CARRIER);
        assertThat(testSdCarInfo.getRunNumber()).isEqualTo(UPDATED_RUN_NUMBER);

        // Validate the SdCarInfo in ElasticSearch
        SdCarInfo sdCarInfoEs = sdCarInfoSearchRepository.findOne(testSdCarInfo.getId());
        assertThat(sdCarInfoEs).isEqualToComparingFieldByField(testSdCarInfo);
    }

    @Test
    @Transactional
    public void deleteSdCarInfo() throws Exception {
        // Initialize the database
        sdCarInfoService.save(sdCarInfo);

        int databaseSizeBeforeDelete = sdCarInfoRepository.findAll().size();

        // Get the sdCarInfo
        restSdCarInfoMockMvc.perform(delete("/api/sd-car-infos/{id}", sdCarInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean sdCarInfoExistsInEs = sdCarInfoSearchRepository.exists(sdCarInfo.getId());
        assertThat(sdCarInfoExistsInEs).isFalse();

        // Validate the database is empty
        List<SdCarInfo> sdCarInfos = sdCarInfoRepository.findAll();
        assertThat(sdCarInfos).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSdCarInfo() throws Exception {
        // Initialize the database
        sdCarInfoService.save(sdCarInfo);

        // Search the sdCarInfo
        restSdCarInfoMockMvc.perform(get("/api/_search/sd-car-infos?query=id:" + sdCarInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sdCarInfo.getId())))
            .andExpect(jsonPath("$.[*].carNo").value(hasItem(DEFAULT_CAR_NO.toString())))
            .andExpect(jsonPath("$.[*].carType").value(hasItem(DEFAULT_CAR_TYPE.toString())))
            .andExpect(jsonPath("$.[*].engineNumber").value(hasItem(DEFAULT_ENGINE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].buyDate").value(hasItem(DEFAULT_BUY_DATE.toString())))
            .andExpect(jsonPath("$.[*].checkLoad").value(hasItem(DEFAULT_CHECK_LOAD.toString())))
            .andExpect(jsonPath("$.[*].checkVolume").value(hasItem(DEFAULT_CHECK_VOLUME.toString())))
            .andExpect(jsonPath("$.[*].carLength").value(hasItem(DEFAULT_CAR_LENGTH.intValue())))
            .andExpect(jsonPath("$.[*].carWidth").value(hasItem(DEFAULT_CAR_WIDTH.intValue())))
            .andExpect(jsonPath("$.[*].carHeight").value(hasItem(DEFAULT_CAR_HEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].vehicleNo").value(hasItem(DEFAULT_VEHICLE_NO.toString())))
            .andExpect(jsonPath("$.[*].policyNo").value(hasItem(DEFAULT_POLICY_NO.toString())))
            .andExpect(jsonPath("$.[*].carrier").value(hasItem(DEFAULT_CARRIER.toString())))
            .andExpect(jsonPath("$.[*].runNumber").value(hasItem(DEFAULT_RUN_NUMBER.toString())));
    }
}
