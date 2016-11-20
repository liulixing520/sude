package com.sude.sd.web.rest;

import com.sude.sd.SudeApp;

import com.sude.sd.domain.SdItemInfo;
import com.sude.sd.repository.SdItemInfoRepository;
import com.sude.sd.service.SdItemInfoService;
import com.sude.sd.repository.search.SdItemInfoSearchRepository;

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
 * Test class for the SdItemInfoResource REST controller.
 *
 * @see SdItemInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SudeApp.class)
public class SdItemInfoResourceIntTest {

    private static final String DEFAULT_ORDER_NO = "AAAAA";
    private static final String UPDATED_ORDER_NO = "BBBBB";

    private static final String DEFAULT_TRAD_NAME = "AAAAA";
    private static final String UPDATED_TRAD_NAME = "BBBBB";

    private static final Long DEFAULT_ITEM_NUM = 1L;
    private static final Long UPDATED_ITEM_NUM = 2L;

    private static final Long DEFAULT_WEIGHT = 1L;
    private static final Long UPDATED_WEIGHT = 2L;

    private static final Long DEFAULT_VOLUME = 1L;
    private static final Long UPDATED_VOLUME = 2L;

    private static final String DEFAULT_ITEM_UNIT = "AAAAA";
    private static final String UPDATED_ITEM_UNIT = "BBBBB";

    private static final BigDecimal DEFAULT_FREIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_FREIGHT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_KICK_BACK = new BigDecimal(1);
    private static final BigDecimal UPDATED_KICK_BACK = new BigDecimal(2);

    private static final BigDecimal DEFAULT_COD = new BigDecimal(1);
    private static final BigDecimal UPDATED_COD = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DELIVERY_EXPENSE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DELIVERY_EXPENSE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CLAIMING_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CLAIMING_VALUE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PREMIUM = new BigDecimal(1);
    private static final BigDecimal UPDATED_PREMIUM = new BigDecimal(2);

    @Inject
    private SdItemInfoRepository sdItemInfoRepository;

    @Inject
    private SdItemInfoService sdItemInfoService;

    @Inject
    private SdItemInfoSearchRepository sdItemInfoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSdItemInfoMockMvc;

    private SdItemInfo sdItemInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SdItemInfoResource sdItemInfoResource = new SdItemInfoResource();
        ReflectionTestUtils.setField(sdItemInfoResource, "sdItemInfoService", sdItemInfoService);
        this.restSdItemInfoMockMvc = MockMvcBuilders.standaloneSetup(sdItemInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SdItemInfo createEntity(EntityManager em) {
        SdItemInfo sdItemInfo = new SdItemInfo()
                .orderNo(DEFAULT_ORDER_NO)
                .tradName(DEFAULT_TRAD_NAME)
                .itemNum(DEFAULT_ITEM_NUM)
                .weight(DEFAULT_WEIGHT)
                .volume(DEFAULT_VOLUME)
                .itemUnit(DEFAULT_ITEM_UNIT)
                .freight(DEFAULT_FREIGHT)
                .kickBack(DEFAULT_KICK_BACK)
                .cod(DEFAULT_COD)
                .deliveryExpense(DEFAULT_DELIVERY_EXPENSE)
                .claimingValue(DEFAULT_CLAIMING_VALUE)
                .premium(DEFAULT_PREMIUM);
        return sdItemInfo;
    }

    @Before
    public void initTest() {
        sdItemInfoSearchRepository.deleteAll();
        sdItemInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createSdItemInfo() throws Exception {
        int databaseSizeBeforeCreate = sdItemInfoRepository.findAll().size();

        // Create the SdItemInfo

        restSdItemInfoMockMvc.perform(post("/api/sd-item-infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sdItemInfo)))
                .andExpect(status().isCreated());

        // Validate the SdItemInfo in the database
        List<SdItemInfo> sdItemInfos = sdItemInfoRepository.findAll();
        assertThat(sdItemInfos).hasSize(databaseSizeBeforeCreate + 1);
        SdItemInfo testSdItemInfo = sdItemInfos.get(sdItemInfos.size() - 1);
        assertThat(testSdItemInfo.getOrderNo()).isEqualTo(DEFAULT_ORDER_NO);
        assertThat(testSdItemInfo.getTradName()).isEqualTo(DEFAULT_TRAD_NAME);
        assertThat(testSdItemInfo.getItemNum()).isEqualTo(DEFAULT_ITEM_NUM);
        assertThat(testSdItemInfo.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testSdItemInfo.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testSdItemInfo.getItemUnit()).isEqualTo(DEFAULT_ITEM_UNIT);
        assertThat(testSdItemInfo.getFreight()).isEqualTo(DEFAULT_FREIGHT);
        assertThat(testSdItemInfo.getKickBack()).isEqualTo(DEFAULT_KICK_BACK);
        assertThat(testSdItemInfo.getCod()).isEqualTo(DEFAULT_COD);
        assertThat(testSdItemInfo.getDeliveryExpense()).isEqualTo(DEFAULT_DELIVERY_EXPENSE);
        assertThat(testSdItemInfo.getClaimingValue()).isEqualTo(DEFAULT_CLAIMING_VALUE);
        assertThat(testSdItemInfo.getPremium()).isEqualTo(DEFAULT_PREMIUM);

        // Validate the SdItemInfo in ElasticSearch
        SdItemInfo sdItemInfoEs = sdItemInfoSearchRepository.findOne(testSdItemInfo.getId());
        assertThat(sdItemInfoEs).isEqualToComparingFieldByField(testSdItemInfo);
    }

    @Test
    @Transactional
    public void getAllSdItemInfos() throws Exception {
        // Initialize the database
        sdItemInfoRepository.saveAndFlush(sdItemInfo);

        // Get all the sdItemInfos
        restSdItemInfoMockMvc.perform(get("/api/sd-item-infos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sdItemInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO.toString())))
                .andExpect(jsonPath("$.[*].tradName").value(hasItem(DEFAULT_TRAD_NAME.toString())))
                .andExpect(jsonPath("$.[*].itemNum").value(hasItem(DEFAULT_ITEM_NUM.intValue())))
                .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
                .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.intValue())))
                .andExpect(jsonPath("$.[*].itemUnit").value(hasItem(DEFAULT_ITEM_UNIT.toString())))
                .andExpect(jsonPath("$.[*].freight").value(hasItem(DEFAULT_FREIGHT.intValue())))
                .andExpect(jsonPath("$.[*].kickBack").value(hasItem(DEFAULT_KICK_BACK.intValue())))
                .andExpect(jsonPath("$.[*].cod").value(hasItem(DEFAULT_COD.intValue())))
                .andExpect(jsonPath("$.[*].deliveryExpense").value(hasItem(DEFAULT_DELIVERY_EXPENSE.intValue())))
                .andExpect(jsonPath("$.[*].claimingValue").value(hasItem(DEFAULT_CLAIMING_VALUE.intValue())))
                .andExpect(jsonPath("$.[*].premium").value(hasItem(DEFAULT_PREMIUM.intValue())));
    }

    @Test
    @Transactional
    public void getSdItemInfo() throws Exception {
        // Initialize the database
        sdItemInfoRepository.saveAndFlush(sdItemInfo);

        // Get the sdItemInfo
        restSdItemInfoMockMvc.perform(get("/api/sd-item-infos/{id}", sdItemInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sdItemInfo.getId().intValue()))
            .andExpect(jsonPath("$.orderNo").value(DEFAULT_ORDER_NO.toString()))
            .andExpect(jsonPath("$.tradName").value(DEFAULT_TRAD_NAME.toString()))
            .andExpect(jsonPath("$.itemNum").value(DEFAULT_ITEM_NUM.intValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.intValue()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.intValue()))
            .andExpect(jsonPath("$.itemUnit").value(DEFAULT_ITEM_UNIT.toString()))
            .andExpect(jsonPath("$.freight").value(DEFAULT_FREIGHT.intValue()))
            .andExpect(jsonPath("$.kickBack").value(DEFAULT_KICK_BACK.intValue()))
            .andExpect(jsonPath("$.cod").value(DEFAULT_COD.intValue()))
            .andExpect(jsonPath("$.deliveryExpense").value(DEFAULT_DELIVERY_EXPENSE.intValue()))
            .andExpect(jsonPath("$.claimingValue").value(DEFAULT_CLAIMING_VALUE.intValue()))
            .andExpect(jsonPath("$.premium").value(DEFAULT_PREMIUM.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSdItemInfo() throws Exception {
        // Get the sdItemInfo
        restSdItemInfoMockMvc.perform(get("/api/sd-item-infos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSdItemInfo() throws Exception {
        // Initialize the database
        sdItemInfoService.save(sdItemInfo);

        int databaseSizeBeforeUpdate = sdItemInfoRepository.findAll().size();

        // Update the sdItemInfo
        SdItemInfo updatedSdItemInfo = sdItemInfoRepository.findOne(sdItemInfo.getId());
        updatedSdItemInfo
                .orderNo(UPDATED_ORDER_NO)
                .tradName(UPDATED_TRAD_NAME)
                .itemNum(UPDATED_ITEM_NUM)
                .weight(UPDATED_WEIGHT)
                .volume(UPDATED_VOLUME)
                .itemUnit(UPDATED_ITEM_UNIT)
                .freight(UPDATED_FREIGHT)
                .kickBack(UPDATED_KICK_BACK)
                .cod(UPDATED_COD)
                .deliveryExpense(UPDATED_DELIVERY_EXPENSE)
                .claimingValue(UPDATED_CLAIMING_VALUE)
                .premium(UPDATED_PREMIUM);

        restSdItemInfoMockMvc.perform(put("/api/sd-item-infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSdItemInfo)))
                .andExpect(status().isOk());

        // Validate the SdItemInfo in the database
        List<SdItemInfo> sdItemInfos = sdItemInfoRepository.findAll();
        assertThat(sdItemInfos).hasSize(databaseSizeBeforeUpdate);
        SdItemInfo testSdItemInfo = sdItemInfos.get(sdItemInfos.size() - 1);
        assertThat(testSdItemInfo.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testSdItemInfo.getTradName()).isEqualTo(UPDATED_TRAD_NAME);
        assertThat(testSdItemInfo.getItemNum()).isEqualTo(UPDATED_ITEM_NUM);
        assertThat(testSdItemInfo.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testSdItemInfo.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testSdItemInfo.getItemUnit()).isEqualTo(UPDATED_ITEM_UNIT);
        assertThat(testSdItemInfo.getFreight()).isEqualTo(UPDATED_FREIGHT);
        assertThat(testSdItemInfo.getKickBack()).isEqualTo(UPDATED_KICK_BACK);
        assertThat(testSdItemInfo.getCod()).isEqualTo(UPDATED_COD);
        assertThat(testSdItemInfo.getDeliveryExpense()).isEqualTo(UPDATED_DELIVERY_EXPENSE);
        assertThat(testSdItemInfo.getClaimingValue()).isEqualTo(UPDATED_CLAIMING_VALUE);
        assertThat(testSdItemInfo.getPremium()).isEqualTo(UPDATED_PREMIUM);

        // Validate the SdItemInfo in ElasticSearch
        SdItemInfo sdItemInfoEs = sdItemInfoSearchRepository.findOne(testSdItemInfo.getId());
        assertThat(sdItemInfoEs).isEqualToComparingFieldByField(testSdItemInfo);
    }

    @Test
    @Transactional
    public void deleteSdItemInfo() throws Exception {
        // Initialize the database
        sdItemInfoService.save(sdItemInfo);

        int databaseSizeBeforeDelete = sdItemInfoRepository.findAll().size();

        // Get the sdItemInfo
        restSdItemInfoMockMvc.perform(delete("/api/sd-item-infos/{id}", sdItemInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean sdItemInfoExistsInEs = sdItemInfoSearchRepository.exists(sdItemInfo.getId());
        assertThat(sdItemInfoExistsInEs).isFalse();

        // Validate the database is empty
        List<SdItemInfo> sdItemInfos = sdItemInfoRepository.findAll();
        assertThat(sdItemInfos).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSdItemInfo() throws Exception {
        // Initialize the database
        sdItemInfoService.save(sdItemInfo);

        // Search the sdItemInfo
        restSdItemInfoMockMvc.perform(get("/api/_search/sd-item-infos?query=id:" + sdItemInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sdItemInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO.toString())))
            .andExpect(jsonPath("$.[*].tradName").value(hasItem(DEFAULT_TRAD_NAME.toString())))
            .andExpect(jsonPath("$.[*].itemNum").value(hasItem(DEFAULT_ITEM_NUM.intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.intValue())))
            .andExpect(jsonPath("$.[*].itemUnit").value(hasItem(DEFAULT_ITEM_UNIT.toString())))
            .andExpect(jsonPath("$.[*].freight").value(hasItem(DEFAULT_FREIGHT.intValue())))
            .andExpect(jsonPath("$.[*].kickBack").value(hasItem(DEFAULT_KICK_BACK.intValue())))
            .andExpect(jsonPath("$.[*].cod").value(hasItem(DEFAULT_COD.intValue())))
            .andExpect(jsonPath("$.[*].deliveryExpense").value(hasItem(DEFAULT_DELIVERY_EXPENSE.intValue())))
            .andExpect(jsonPath("$.[*].claimingValue").value(hasItem(DEFAULT_CLAIMING_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].premium").value(hasItem(DEFAULT_PREMIUM.intValue())));
    }
}
