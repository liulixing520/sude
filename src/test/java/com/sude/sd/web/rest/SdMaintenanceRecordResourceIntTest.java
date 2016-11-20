package com.sude.sd.web.rest;

import com.sude.sd.SudeApp;

import com.sude.sd.domain.SdMaintenanceRecord;
import com.sude.sd.repository.SdMaintenanceRecordRepository;
import com.sude.sd.service.SdMaintenanceRecordService;
import com.sude.sd.repository.search.SdMaintenanceRecordSearchRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SdMaintenanceRecordResource REST controller.
 *
 * @see SdMaintenanceRecordResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SudeApp.class)
public class SdMaintenanceRecordResourceIntTest {

    private static final String DEFAULT_CAR_NO = "AAAAA";
    private static final String UPDATED_CAR_NO = "BBBBB";

    private static final LocalDate DEFAULT_MAINTAIN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MAINTAIN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SENDER = "AAAAA";
    private static final String UPDATED_SENDER = "BBBBB";

    private static final String DEFAULT_SEND_REASON = "AAAAA";
    private static final String UPDATED_SEND_REASON = "BBBBB";

    private static final String DEFAULT_REPAIRER = "AAAAA";
    private static final String UPDATED_REPAIRER = "BBBBB";

    private static final String DEFAULT_REPAI_RESULT = "AAAAA";
    private static final String UPDATED_REPAI_RESULT = "BBBBB";

    private static final BigDecimal DEFAULT_REPAI_COSTS = new BigDecimal(1);
    private static final BigDecimal UPDATED_REPAI_COSTS = new BigDecimal(2);

    private static final String DEFAULT_REMARK = "AAAAA";
    private static final String UPDATED_REMARK = "BBBBB";

    @Inject
    private SdMaintenanceRecordRepository sdMaintenanceRecordRepository;

    @Inject
    private SdMaintenanceRecordService sdMaintenanceRecordService;

    @Inject
    private SdMaintenanceRecordSearchRepository sdMaintenanceRecordSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSdMaintenanceRecordMockMvc;

    private SdMaintenanceRecord sdMaintenanceRecord;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SdMaintenanceRecordResource sdMaintenanceRecordResource = new SdMaintenanceRecordResource();
        ReflectionTestUtils.setField(sdMaintenanceRecordResource, "sdMaintenanceRecordService", sdMaintenanceRecordService);
        this.restSdMaintenanceRecordMockMvc = MockMvcBuilders.standaloneSetup(sdMaintenanceRecordResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SdMaintenanceRecord createEntity(EntityManager em) {
        SdMaintenanceRecord sdMaintenanceRecord = new SdMaintenanceRecord()
                .carNo(DEFAULT_CAR_NO)
                .maintainDate(DEFAULT_MAINTAIN_DATE)
                .sender(DEFAULT_SENDER)
                .sendReason(DEFAULT_SEND_REASON)
                .repairer(DEFAULT_REPAIRER)
                .repaiResult(DEFAULT_REPAI_RESULT)
                .repaiCosts(DEFAULT_REPAI_COSTS)
                .remark(DEFAULT_REMARK);
        return sdMaintenanceRecord;
    }

    @Before
    public void initTest() {
        sdMaintenanceRecordSearchRepository.deleteAll();
        sdMaintenanceRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createSdMaintenanceRecord() throws Exception {
        int databaseSizeBeforeCreate = sdMaintenanceRecordRepository.findAll().size();

        // Create the SdMaintenanceRecord

        restSdMaintenanceRecordMockMvc.perform(post("/api/sd-maintenance-records")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sdMaintenanceRecord)))
                .andExpect(status().isCreated());

        // Validate the SdMaintenanceRecord in the database
        List<SdMaintenanceRecord> sdMaintenanceRecords = sdMaintenanceRecordRepository.findAll();
        assertThat(sdMaintenanceRecords).hasSize(databaseSizeBeforeCreate + 1);
        SdMaintenanceRecord testSdMaintenanceRecord = sdMaintenanceRecords.get(sdMaintenanceRecords.size() - 1);
        assertThat(testSdMaintenanceRecord.getCarNo()).isEqualTo(DEFAULT_CAR_NO);
        assertThat(testSdMaintenanceRecord.getMaintainDate()).isEqualTo(DEFAULT_MAINTAIN_DATE);
        assertThat(testSdMaintenanceRecord.getSender()).isEqualTo(DEFAULT_SENDER);
        assertThat(testSdMaintenanceRecord.getSendReason()).isEqualTo(DEFAULT_SEND_REASON);
        assertThat(testSdMaintenanceRecord.getRepairer()).isEqualTo(DEFAULT_REPAIRER);
        assertThat(testSdMaintenanceRecord.getRepaiResult()).isEqualTo(DEFAULT_REPAI_RESULT);
        assertThat(testSdMaintenanceRecord.getRepaiCosts()).isEqualTo(DEFAULT_REPAI_COSTS);
        assertThat(testSdMaintenanceRecord.getRemark()).isEqualTo(DEFAULT_REMARK);

        // Validate the SdMaintenanceRecord in ElasticSearch
        SdMaintenanceRecord sdMaintenanceRecordEs = sdMaintenanceRecordSearchRepository.findOne(testSdMaintenanceRecord.getId());
        assertThat(sdMaintenanceRecordEs).isEqualToComparingFieldByField(testSdMaintenanceRecord);
    }

    @Test
    @Transactional
    public void getAllSdMaintenanceRecords() throws Exception {
        // Initialize the database
        sdMaintenanceRecordRepository.saveAndFlush(sdMaintenanceRecord);

        // Get all the sdMaintenanceRecords
        restSdMaintenanceRecordMockMvc.perform(get("/api/sd-maintenance-records?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sdMaintenanceRecord.getId().intValue())))
                .andExpect(jsonPath("$.[*].carNo").value(hasItem(DEFAULT_CAR_NO.toString())))
                .andExpect(jsonPath("$.[*].maintainDate").value(hasItem(DEFAULT_MAINTAIN_DATE.toString())))
                .andExpect(jsonPath("$.[*].sender").value(hasItem(DEFAULT_SENDER.toString())))
                .andExpect(jsonPath("$.[*].sendReason").value(hasItem(DEFAULT_SEND_REASON.toString())))
                .andExpect(jsonPath("$.[*].repairer").value(hasItem(DEFAULT_REPAIRER.toString())))
                .andExpect(jsonPath("$.[*].repaiResult").value(hasItem(DEFAULT_REPAI_RESULT.toString())))
                .andExpect(jsonPath("$.[*].repaiCosts").value(hasItem(DEFAULT_REPAI_COSTS.intValue())))
                .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }

    @Test
    @Transactional
    public void getSdMaintenanceRecord() throws Exception {
        // Initialize the database
        sdMaintenanceRecordRepository.saveAndFlush(sdMaintenanceRecord);

        // Get the sdMaintenanceRecord
        restSdMaintenanceRecordMockMvc.perform(get("/api/sd-maintenance-records/{id}", sdMaintenanceRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sdMaintenanceRecord.getId().intValue()))
            .andExpect(jsonPath("$.carNo").value(DEFAULT_CAR_NO.toString()))
            .andExpect(jsonPath("$.maintainDate").value(DEFAULT_MAINTAIN_DATE.toString()))
            .andExpect(jsonPath("$.sender").value(DEFAULT_SENDER.toString()))
            .andExpect(jsonPath("$.sendReason").value(DEFAULT_SEND_REASON.toString()))
            .andExpect(jsonPath("$.repairer").value(DEFAULT_REPAIRER.toString()))
            .andExpect(jsonPath("$.repaiResult").value(DEFAULT_REPAI_RESULT.toString()))
            .andExpect(jsonPath("$.repaiCosts").value(DEFAULT_REPAI_COSTS.intValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSdMaintenanceRecord() throws Exception {
        // Get the sdMaintenanceRecord
        restSdMaintenanceRecordMockMvc.perform(get("/api/sd-maintenance-records/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSdMaintenanceRecord() throws Exception {
        // Initialize the database
        sdMaintenanceRecordService.save(sdMaintenanceRecord);

        int databaseSizeBeforeUpdate = sdMaintenanceRecordRepository.findAll().size();

        // Update the sdMaintenanceRecord
        SdMaintenanceRecord updatedSdMaintenanceRecord = sdMaintenanceRecordRepository.findOne(sdMaintenanceRecord.getId());
        updatedSdMaintenanceRecord
                .carNo(UPDATED_CAR_NO)
                .maintainDate(UPDATED_MAINTAIN_DATE)
                .sender(UPDATED_SENDER)
                .sendReason(UPDATED_SEND_REASON)
                .repairer(UPDATED_REPAIRER)
                .repaiResult(UPDATED_REPAI_RESULT)
                .repaiCosts(UPDATED_REPAI_COSTS)
                .remark(UPDATED_REMARK);

        restSdMaintenanceRecordMockMvc.perform(put("/api/sd-maintenance-records")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSdMaintenanceRecord)))
                .andExpect(status().isOk());

        // Validate the SdMaintenanceRecord in the database
        List<SdMaintenanceRecord> sdMaintenanceRecords = sdMaintenanceRecordRepository.findAll();
        assertThat(sdMaintenanceRecords).hasSize(databaseSizeBeforeUpdate);
        SdMaintenanceRecord testSdMaintenanceRecord = sdMaintenanceRecords.get(sdMaintenanceRecords.size() - 1);
        assertThat(testSdMaintenanceRecord.getCarNo()).isEqualTo(UPDATED_CAR_NO);
        assertThat(testSdMaintenanceRecord.getMaintainDate()).isEqualTo(UPDATED_MAINTAIN_DATE);
        assertThat(testSdMaintenanceRecord.getSender()).isEqualTo(UPDATED_SENDER);
        assertThat(testSdMaintenanceRecord.getSendReason()).isEqualTo(UPDATED_SEND_REASON);
        assertThat(testSdMaintenanceRecord.getRepairer()).isEqualTo(UPDATED_REPAIRER);
        assertThat(testSdMaintenanceRecord.getRepaiResult()).isEqualTo(UPDATED_REPAI_RESULT);
        assertThat(testSdMaintenanceRecord.getRepaiCosts()).isEqualTo(UPDATED_REPAI_COSTS);
        assertThat(testSdMaintenanceRecord.getRemark()).isEqualTo(UPDATED_REMARK);

        // Validate the SdMaintenanceRecord in ElasticSearch
        SdMaintenanceRecord sdMaintenanceRecordEs = sdMaintenanceRecordSearchRepository.findOne(testSdMaintenanceRecord.getId());
        assertThat(sdMaintenanceRecordEs).isEqualToComparingFieldByField(testSdMaintenanceRecord);
    }

    @Test
    @Transactional
    public void deleteSdMaintenanceRecord() throws Exception {
        // Initialize the database
        sdMaintenanceRecordService.save(sdMaintenanceRecord);

        int databaseSizeBeforeDelete = sdMaintenanceRecordRepository.findAll().size();

        // Get the sdMaintenanceRecord
        restSdMaintenanceRecordMockMvc.perform(delete("/api/sd-maintenance-records/{id}", sdMaintenanceRecord.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean sdMaintenanceRecordExistsInEs = sdMaintenanceRecordSearchRepository.exists(sdMaintenanceRecord.getId());
        assertThat(sdMaintenanceRecordExistsInEs).isFalse();

        // Validate the database is empty
        List<SdMaintenanceRecord> sdMaintenanceRecords = sdMaintenanceRecordRepository.findAll();
        assertThat(sdMaintenanceRecords).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSdMaintenanceRecord() throws Exception {
        // Initialize the database
        sdMaintenanceRecordService.save(sdMaintenanceRecord);

        // Search the sdMaintenanceRecord
        restSdMaintenanceRecordMockMvc.perform(get("/api/_search/sd-maintenance-records?query=id:" + sdMaintenanceRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sdMaintenanceRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].carNo").value(hasItem(DEFAULT_CAR_NO.toString())))
            .andExpect(jsonPath("$.[*].maintainDate").value(hasItem(DEFAULT_MAINTAIN_DATE.toString())))
            .andExpect(jsonPath("$.[*].sender").value(hasItem(DEFAULT_SENDER.toString())))
            .andExpect(jsonPath("$.[*].sendReason").value(hasItem(DEFAULT_SEND_REASON.toString())))
            .andExpect(jsonPath("$.[*].repairer").value(hasItem(DEFAULT_REPAIRER.toString())))
            .andExpect(jsonPath("$.[*].repaiResult").value(hasItem(DEFAULT_REPAI_RESULT.toString())))
            .andExpect(jsonPath("$.[*].repaiCosts").value(hasItem(DEFAULT_REPAI_COSTS.intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }
}
