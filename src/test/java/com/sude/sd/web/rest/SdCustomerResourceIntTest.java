package com.sude.sd.web.rest;

import com.sude.sd.SudeApp;

import com.sude.sd.domain.SdCustomer;
import com.sude.sd.repository.SdCustomerRepository;
import com.sude.sd.service.SdCustomerService;
import com.sude.sd.repository.search.SdCustomerSearchRepository;

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
 * Test class for the SdCustomerResource REST controller.
 *
 * @see SdCustomerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SudeApp.class)
public class SdCustomerResourceIntTest {

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBB";

    private static final String DEFAULT_CUSTOMER_NM = "AAAAA";
    private static final String UPDATED_CUSTOMER_NM = "BBBBB";

    private static final String DEFAULT_SEX = "AAAAA";
    private static final String UPDATED_SEX = "BBBBB";

    private static final Long DEFAULT_MOBILE_PHONE = 1L;
    private static final Long UPDATED_MOBILE_PHONE = 2L;

    private static final String DEFAULT_ID_CARD = "AAAAA";
    private static final String UPDATED_ID_CARD = "BBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";

    private static final String DEFAULT_BANK = "AAAAA";
    private static final String UPDATED_BANK = "BBBBB";

    private static final Long DEFAULT_BANK_NO = 1L;
    private static final Long UPDATED_BANK_NO = 2L;

    private static final String DEFAULT_BANK_OPEN_NAME = "AAAAA";
    private static final String UPDATED_BANK_OPEN_NAME = "BBBBB";

    private static final String DEFAULT_COMPANY = "AAAAA";
    private static final String UPDATED_COMPANY = "BBBBB";

    private static final String DEFAULT_CUST_TYPE = "AAAAA";
    private static final String UPDATED_CUST_TYPE = "BBBBB";

    private static final String DEFAULT_REMARK = "AAAAA";
    private static final String UPDATED_REMARK = "BBBBB";

    @Inject
    private SdCustomerRepository sdCustomerRepository;

    @Inject
    private SdCustomerService sdCustomerService;

    @Inject
    private SdCustomerSearchRepository sdCustomerSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSdCustomerMockMvc;

    private SdCustomer sdCustomer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SdCustomerResource sdCustomerResource = new SdCustomerResource();
        ReflectionTestUtils.setField(sdCustomerResource, "sdCustomerService", sdCustomerService);
        this.restSdCustomerMockMvc = MockMvcBuilders.standaloneSetup(sdCustomerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SdCustomer createEntity(EntityManager em) {
        SdCustomer sdCustomer = new SdCustomer()
                .customerName(DEFAULT_CUSTOMER_NAME)
                .customerNM(DEFAULT_CUSTOMER_NM)
                .sex(DEFAULT_SEX)
                .mobilePhone(DEFAULT_MOBILE_PHONE)
                .idCard(DEFAULT_ID_CARD)
                .address(DEFAULT_ADDRESS)
                .bank(DEFAULT_BANK)
                .bankNo(DEFAULT_BANK_NO)
                .bankOpenName(DEFAULT_BANK_OPEN_NAME)
                .company(DEFAULT_COMPANY)
                .custType(DEFAULT_CUST_TYPE)
                .remark(DEFAULT_REMARK);
        return sdCustomer;
    }

    @Before
    public void initTest() {
        sdCustomerSearchRepository.deleteAll();
        sdCustomer = createEntity(em);
    }

    @Test
    @Transactional
    public void createSdCustomer() throws Exception {
        int databaseSizeBeforeCreate = sdCustomerRepository.findAll().size();

        // Create the SdCustomer

        restSdCustomerMockMvc.perform(post("/api/sd-customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sdCustomer)))
                .andExpect(status().isCreated());

        // Validate the SdCustomer in the database
        List<SdCustomer> sdCustomers = sdCustomerRepository.findAll();
        assertThat(sdCustomers).hasSize(databaseSizeBeforeCreate + 1);
        SdCustomer testSdCustomer = sdCustomers.get(sdCustomers.size() - 1);
        assertThat(testSdCustomer.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testSdCustomer.getCustomerNM()).isEqualTo(DEFAULT_CUSTOMER_NM);
        assertThat(testSdCustomer.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testSdCustomer.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testSdCustomer.getIdCard()).isEqualTo(DEFAULT_ID_CARD);
        assertThat(testSdCustomer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testSdCustomer.getBank()).isEqualTo(DEFAULT_BANK);
        assertThat(testSdCustomer.getBankNo()).isEqualTo(DEFAULT_BANK_NO);
        assertThat(testSdCustomer.getBankOpenName()).isEqualTo(DEFAULT_BANK_OPEN_NAME);
        assertThat(testSdCustomer.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testSdCustomer.getCustType()).isEqualTo(DEFAULT_CUST_TYPE);
        assertThat(testSdCustomer.getRemark()).isEqualTo(DEFAULT_REMARK);

        // Validate the SdCustomer in ElasticSearch
        SdCustomer sdCustomerEs = sdCustomerSearchRepository.findOne(testSdCustomer.getId());
        assertThat(sdCustomerEs).isEqualToComparingFieldByField(testSdCustomer);
    }

    @Test
    @Transactional
    public void getAllSdCustomers() throws Exception {
        // Initialize the database
        sdCustomerRepository.saveAndFlush(sdCustomer);

        // Get all the sdCustomers
        restSdCustomerMockMvc.perform(get("/api/sd-customers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sdCustomer.getId().intValue())))
                .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME.toString())))
                .andExpect(jsonPath("$.[*].customerNM").value(hasItem(DEFAULT_CUSTOMER_NM.toString())))
                .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
                .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.intValue())))
                .andExpect(jsonPath("$.[*].idCard").value(hasItem(DEFAULT_ID_CARD.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].bank").value(hasItem(DEFAULT_BANK.toString())))
                .andExpect(jsonPath("$.[*].bankNo").value(hasItem(DEFAULT_BANK_NO.intValue())))
                .andExpect(jsonPath("$.[*].bankOpenName").value(hasItem(DEFAULT_BANK_OPEN_NAME.toString())))
                .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())))
                .andExpect(jsonPath("$.[*].custType").value(hasItem(DEFAULT_CUST_TYPE.toString())))
                .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }

    @Test
    @Transactional
    public void getSdCustomer() throws Exception {
        // Initialize the database
        sdCustomerRepository.saveAndFlush(sdCustomer);

        // Get the sdCustomer
        restSdCustomerMockMvc.perform(get("/api/sd-customers/{id}", sdCustomer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sdCustomer.getId().intValue()))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME.toString()))
            .andExpect(jsonPath("$.customerNM").value(DEFAULT_CUSTOMER_NM.toString()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE.intValue()))
            .andExpect(jsonPath("$.idCard").value(DEFAULT_ID_CARD.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.bank").value(DEFAULT_BANK.toString()))
            .andExpect(jsonPath("$.bankNo").value(DEFAULT_BANK_NO.intValue()))
            .andExpect(jsonPath("$.bankOpenName").value(DEFAULT_BANK_OPEN_NAME.toString()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY.toString()))
            .andExpect(jsonPath("$.custType").value(DEFAULT_CUST_TYPE.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSdCustomer() throws Exception {
        // Get the sdCustomer
        restSdCustomerMockMvc.perform(get("/api/sd-customers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSdCustomer() throws Exception {
        // Initialize the database
        sdCustomerService.save(sdCustomer);

        int databaseSizeBeforeUpdate = sdCustomerRepository.findAll().size();

        // Update the sdCustomer
        SdCustomer updatedSdCustomer = sdCustomerRepository.findOne(sdCustomer.getId());
        updatedSdCustomer
                .customerName(UPDATED_CUSTOMER_NAME)
                .customerNM(UPDATED_CUSTOMER_NM)
                .sex(UPDATED_SEX)
                .mobilePhone(UPDATED_MOBILE_PHONE)
                .idCard(UPDATED_ID_CARD)
                .address(UPDATED_ADDRESS)
                .bank(UPDATED_BANK)
                .bankNo(UPDATED_BANK_NO)
                .bankOpenName(UPDATED_BANK_OPEN_NAME)
                .company(UPDATED_COMPANY)
                .custType(UPDATED_CUST_TYPE)
                .remark(UPDATED_REMARK);

        restSdCustomerMockMvc.perform(put("/api/sd-customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSdCustomer)))
                .andExpect(status().isOk());

        // Validate the SdCustomer in the database
        List<SdCustomer> sdCustomers = sdCustomerRepository.findAll();
        assertThat(sdCustomers).hasSize(databaseSizeBeforeUpdate);
        SdCustomer testSdCustomer = sdCustomers.get(sdCustomers.size() - 1);
        assertThat(testSdCustomer.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testSdCustomer.getCustomerNM()).isEqualTo(UPDATED_CUSTOMER_NM);
        assertThat(testSdCustomer.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testSdCustomer.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testSdCustomer.getIdCard()).isEqualTo(UPDATED_ID_CARD);
        assertThat(testSdCustomer.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testSdCustomer.getBank()).isEqualTo(UPDATED_BANK);
        assertThat(testSdCustomer.getBankNo()).isEqualTo(UPDATED_BANK_NO);
        assertThat(testSdCustomer.getBankOpenName()).isEqualTo(UPDATED_BANK_OPEN_NAME);
        assertThat(testSdCustomer.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testSdCustomer.getCustType()).isEqualTo(UPDATED_CUST_TYPE);
        assertThat(testSdCustomer.getRemark()).isEqualTo(UPDATED_REMARK);

        // Validate the SdCustomer in ElasticSearch
        SdCustomer sdCustomerEs = sdCustomerSearchRepository.findOne(testSdCustomer.getId());
        assertThat(sdCustomerEs).isEqualToComparingFieldByField(testSdCustomer);
    }

    @Test
    @Transactional
    public void deleteSdCustomer() throws Exception {
        // Initialize the database
        sdCustomerService.save(sdCustomer);

        int databaseSizeBeforeDelete = sdCustomerRepository.findAll().size();

        // Get the sdCustomer
        restSdCustomerMockMvc.perform(delete("/api/sd-customers/{id}", sdCustomer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean sdCustomerExistsInEs = sdCustomerSearchRepository.exists(sdCustomer.getId());
        assertThat(sdCustomerExistsInEs).isFalse();

        // Validate the database is empty
        List<SdCustomer> sdCustomers = sdCustomerRepository.findAll();
        assertThat(sdCustomers).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSdCustomer() throws Exception {
        // Initialize the database
        sdCustomerService.save(sdCustomer);

        // Search the sdCustomer
        restSdCustomerMockMvc.perform(get("/api/_search/sd-customers?query=id:" + sdCustomer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sdCustomer.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME.toString())))
            .andExpect(jsonPath("$.[*].customerNM").value(hasItem(DEFAULT_CUSTOMER_NM.toString())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].idCard").value(hasItem(DEFAULT_ID_CARD.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].bank").value(hasItem(DEFAULT_BANK.toString())))
            .andExpect(jsonPath("$.[*].bankNo").value(hasItem(DEFAULT_BANK_NO.intValue())))
            .andExpect(jsonPath("$.[*].bankOpenName").value(hasItem(DEFAULT_BANK_OPEN_NAME.toString())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())))
            .andExpect(jsonPath("$.[*].custType").value(hasItem(DEFAULT_CUST_TYPE.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }
}
