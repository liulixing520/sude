package com.sude.sd.web.rest;

import com.sude.sd.SudeApp;

import com.sude.sd.domain.SdBalance;
import com.sude.sd.repository.SdBalanceRepository;
import com.sude.sd.service.SdBalanceService;
import com.sude.sd.repository.search.SdBalanceSearchRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SdBalanceResource REST controller.
 *
 * @see SdBalanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SudeApp.class)
public class SdBalanceResourceIntTest {

    private static final String DEFAULT_ORDER_NO = "AAAAA";
    private static final String UPDATED_ORDER_NO = "BBBBB";

    private static final String DEFAULT_SUMMARY = "AAAAA";
    private static final String UPDATED_SUMMARY = "BBBBB";

    private static final BigDecimal DEFAULT_MONEY = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONEY = new BigDecimal(2);

    private static final String DEFAULT_REMARK = "AAAAA";
    private static final String UPDATED_REMARK = "BBBBB";

    @Inject
    private SdBalanceRepository sdBalanceRepository;

    @Inject
    private SdBalanceService sdBalanceService;

    @Inject
    private SdBalanceSearchRepository sdBalanceSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSdBalanceMockMvc;

    private SdBalance sdBalance;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SdBalanceResource sdBalanceResource = new SdBalanceResource();
        ReflectionTestUtils.setField(sdBalanceResource, "sdBalanceService", sdBalanceService);
        this.restSdBalanceMockMvc = MockMvcBuilders.standaloneSetup(sdBalanceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SdBalance createEntity(EntityManager em) {
        SdBalance sdBalance = new SdBalance()
                .orderNo(DEFAULT_ORDER_NO)
                .summary(DEFAULT_SUMMARY)
                .money(DEFAULT_MONEY)
                .remark(DEFAULT_REMARK);
        return sdBalance;
    }

    @Before
    public void initTest() {
        sdBalanceSearchRepository.deleteAll();
        sdBalance = createEntity(em);
    }

    @Test
    @Transactional
    public void createSdBalance() throws Exception {
        int databaseSizeBeforeCreate = sdBalanceRepository.findAll().size();

        // Create the SdBalance

        restSdBalanceMockMvc.perform(post("/api/sd-balances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sdBalance)))
                .andExpect(status().isCreated());

        // Validate the SdBalance in the database
        List<SdBalance> sdBalances = sdBalanceRepository.findAll();
        assertThat(sdBalances).hasSize(databaseSizeBeforeCreate + 1);
        SdBalance testSdBalance = sdBalances.get(sdBalances.size() - 1);
        assertThat(testSdBalance.getOrderNo()).isEqualTo(DEFAULT_ORDER_NO);
        assertThat(testSdBalance.getSummary()).isEqualTo(DEFAULT_SUMMARY);
        assertThat(testSdBalance.getMoney()).isEqualTo(DEFAULT_MONEY);
        assertThat(testSdBalance.getRemark()).isEqualTo(DEFAULT_REMARK);

        // Validate the SdBalance in ElasticSearch
        SdBalance sdBalanceEs = sdBalanceSearchRepository.findOne(testSdBalance.getId());
        assertThat(sdBalanceEs).isEqualToComparingFieldByField(testSdBalance);
    }

    @Test
    @Transactional
    public void getAllSdBalances() throws Exception {
        // Initialize the database
        sdBalanceRepository.saveAndFlush(sdBalance);

        // Get all the sdBalances
        restSdBalanceMockMvc.perform(get("/api/sd-balances?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sdBalance.getId().intValue())))
                .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO.toString())))
                .andExpect(jsonPath("$.[*].summary").value(hasItem(DEFAULT_SUMMARY.toString())))
                .andExpect(jsonPath("$.[*].money").value(hasItem(DEFAULT_MONEY.intValue())))
                .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }

    @Test
    @Transactional
    public void getSdBalance() throws Exception {
        // Initialize the database
        sdBalanceRepository.saveAndFlush(sdBalance);

        // Get the sdBalance
        restSdBalanceMockMvc.perform(get("/api/sd-balances/{id}", sdBalance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sdBalance.getId().intValue()))
            .andExpect(jsonPath("$.orderNo").value(DEFAULT_ORDER_NO.toString()))
            .andExpect(jsonPath("$.summary").value(DEFAULT_SUMMARY.toString()))
            .andExpect(jsonPath("$.money").value(DEFAULT_MONEY.intValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSdBalance() throws Exception {
        // Get the sdBalance
        restSdBalanceMockMvc.perform(get("/api/sd-balances/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSdBalance() throws Exception {
        // Initialize the database
        sdBalanceService.save(sdBalance);

        int databaseSizeBeforeUpdate = sdBalanceRepository.findAll().size();

        // Update the sdBalance
        SdBalance updatedSdBalance = sdBalanceRepository.findOne(sdBalance.getId());
        updatedSdBalance
                .orderNo(UPDATED_ORDER_NO)
                .summary(UPDATED_SUMMARY)
                .money(UPDATED_MONEY)
                .remark(UPDATED_REMARK);

        restSdBalanceMockMvc.perform(put("/api/sd-balances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSdBalance)))
                .andExpect(status().isOk());

        // Validate the SdBalance in the database
        List<SdBalance> sdBalances = sdBalanceRepository.findAll();
        assertThat(sdBalances).hasSize(databaseSizeBeforeUpdate);
        SdBalance testSdBalance = sdBalances.get(sdBalances.size() - 1);
        assertThat(testSdBalance.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testSdBalance.getSummary()).isEqualTo(UPDATED_SUMMARY);
        assertThat(testSdBalance.getMoney()).isEqualTo(UPDATED_MONEY);
        assertThat(testSdBalance.getRemark()).isEqualTo(UPDATED_REMARK);

        // Validate the SdBalance in ElasticSearch
        SdBalance sdBalanceEs = sdBalanceSearchRepository.findOne(testSdBalance.getId());
        assertThat(sdBalanceEs).isEqualToComparingFieldByField(testSdBalance);
    }

    @Test
    @Transactional
    public void deleteSdBalance() throws Exception {
        // Initialize the database
        sdBalanceService.save(sdBalance);

        int databaseSizeBeforeDelete = sdBalanceRepository.findAll().size();

        // Get the sdBalance
        restSdBalanceMockMvc.perform(delete("/api/sd-balances/{id}", sdBalance.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean sdBalanceExistsInEs = sdBalanceSearchRepository.exists(sdBalance.getId());
        assertThat(sdBalanceExistsInEs).isFalse();

        // Validate the database is empty
        List<SdBalance> sdBalances = sdBalanceRepository.findAll();
        assertThat(sdBalances).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSdBalance() throws Exception {
        // Initialize the database
        sdBalanceService.save(sdBalance);

        // Search the sdBalance
        restSdBalanceMockMvc.perform(get("/api/_search/sd-balances?query=id:" + sdBalance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sdBalance.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO.toString())))
            .andExpect(jsonPath("$.[*].summary").value(hasItem(DEFAULT_SUMMARY.toString())))
            .andExpect(jsonPath("$.[*].money").value(hasItem(DEFAULT_MONEY.intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }
}
