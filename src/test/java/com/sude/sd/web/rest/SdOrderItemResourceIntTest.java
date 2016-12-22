package com.sude.sd.web.rest;

import com.sude.sd.SudeApp;

import com.sude.sd.domain.SdOrderItem;
import com.sude.sd.repository.SdOrderItemRepository;
import com.sude.sd.service.SdOrderItemService;
import com.sude.sd.repository.search.SdOrderItemSearchRepository;

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
 * Test class for the SdOrderItemResource REST controller.
 *
 * @see SdOrderItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SudeApp.class)
public class SdOrderItemResourceIntTest {

	private static final String DEFAULT_ID = "AAAAA";
	
    private static final String DEFAULT_ORDER_NO = "AAAAA";
    private static final String UPDATED_ORDER_NO = "BBBBB";

    private static final String DEFAULT_ORDER_HEADER_NO = "AAAAA";
    private static final String UPDATED_ORDER_HEADER_NO = "BBBBB";

    private static final LocalDate DEFAULT_ITEM_NO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ITEM_NO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CONSIGN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CONSIGN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FROM_STATION = "AAAAA";
    private static final String UPDATED_FROM_STATION = "BBBBB";

    private static final String DEFAULT_TO_STATION = "AAAAA";
    private static final String UPDATED_TO_STATION = "BBBBB";

    private static final String DEFAULT_MIDDLE_STATION = "AAAAA";
    private static final String UPDATED_MIDDLE_STATION = "BBBBB";

    private static final String DEFAULT_CONSIGNER_ID = "AAAAA";
    private static final String UPDATED_CONSIGNER_ID = "BBBBB";

    private static final String DEFAULT_CONSIGNER_NAME = "AAAAA";
    private static final String UPDATED_CONSIGNER_NAME = "BBBBB";

    private static final String DEFAULT_CONSIGNER_ADDRESS = "AAAAA";
    private static final String UPDATED_CONSIGNER_ADDRESS = "BBBBB";

    private static final String DEFAULT_CONSIGNER_PHONE = "AAAAA";
    private static final String UPDATED_CONSIGNER_PHONE = "BBBBB";

    private static final Long DEFAULT_CONSIGNER_MB_PHONE = 1L;
    private static final Long UPDATED_CONSIGNER_MB_PHONE = 2L;

    private static final String DEFAULT_CONSIGNEE_ID = "AAAAA";
    private static final String UPDATED_CONSIGNEE_ID = "BBBBB";

    private static final String DEFAULT_CONSIGNEE_NAME = "AAAAA";
    private static final String UPDATED_CONSIGNEE_NAME = "BBBBB";

    private static final String DEFAULT_CONSIGNEE_PHONE = "AAAAA";
    private static final String UPDATED_CONSIGNEE_PHONE = "BBBBB";

    private static final Long DEFAULT_CONSIGNEE_MB_PHONE = 1L;
    private static final Long UPDATED_CONSIGNEE_MB_PHONE = 2L;

    private static final String DEFAULT_CONSIGNEE_ADDRESS = "AAAAA";
    private static final String UPDATED_CONSIGNEE_ADDRESS = "BBBBB";

    private static final Long DEFAULT_BANK_NO = 1L;
    private static final Long UPDATED_BANK_NO = 2L;

    private static final String DEFAULT_BANK_NAME = "AAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBB";

    private static final String DEFAULT_OPEN_NAME = "AAAAA";
    private static final String UPDATED_OPEN_NAME = "BBBBB";

    private static final String DEFAULT_ID_CARD = "AAAAA";
    private static final String UPDATED_ID_CARD = "BBBBB";

    private static final String DEFAULT_PAY_TYPE = "AAAAA";
    private static final String UPDATED_PAY_TYPE = "BBBBB";

    private static final BigDecimal DEFAULT_CASH_PAY = new BigDecimal(1);
    private static final BigDecimal UPDATED_CASH_PAY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_FETCH_PAY = new BigDecimal(1);
    private static final BigDecimal UPDATED_FETCH_PAY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_RECEIPT_PAY = new BigDecimal(1);
    private static final BigDecimal UPDATED_RECEIPT_PAY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MONTH_PAY = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTH_PAY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CHARGE_PAY = new BigDecimal(1);
    private static final BigDecimal UPDATED_CHARGE_PAY = new BigDecimal(2);

    private static final String DEFAULT_TRANSPORT_TYPE = "AAAAA";
    private static final String UPDATED_TRANSPORT_TYPE = "BBBBB";

    private static final String DEFAULT_BACK_REQUIRE = "AAAAA";
    private static final String UPDATED_BACK_REQUIRE = "BBBBB";

    private static final String DEFAULT_HAND_OVER_TYPE = "AAAAA";
    private static final String UPDATED_HAND_OVER_TYPE = "BBBBB";

    private static final String DEFAULT_OTHER_PAY = "AAAAA";
    private static final String UPDATED_OTHER_PAY = "BBBBB";

    private static final String DEFAULT_PAY_EXPLAIN = "AAAAA";
    private static final String UPDATED_PAY_EXPLAIN = "BBBBB";

    private static final String DEFAULT_REMARK = "AAAAA";
    private static final String UPDATED_REMARK = "BBBBB";

    private static final String DEFAULT_KICK_BACK = "AAAAA";
    private static final String UPDATED_KICK_BACK = "BBBBB";

    private static final String DEFAULT_CASH_OWE = "AAAAA";
    private static final String UPDATED_CASH_OWE = "BBBBB";

    private static final String DEFAULT_REQUIRE_ITEM = "AAAAA";
    private static final String UPDATED_REQUIRE_ITEM = "BBBBB";

    private static final String DEFAULT_TAGGED = "AAAAA";
    private static final String UPDATED_TAGGED = "BBBBB";

    private static final String DEFAULT_ENVELOPES = "AAAAA";
    private static final String UPDATED_ENVELOPES = "BBBBB";

    private static final String DEFAULT_SALES_MAN = "AAAAA";
    private static final String UPDATED_SALES_MAN = "BBBBB";

    private static final String DEFAULT_OPERATOR = "AAAAA";
    private static final String UPDATED_OPERATOR = "BBBBB";

    private static final String DEFAULT_ORDER_STAT = "AAAAA";
    private static final String UPDATED_ORDER_STAT = "BBBBB";

    @Inject
    private SdOrderItemRepository sdOrderItemRepository;

    @Inject
    private SdOrderItemService sdOrderItemService;

    @Inject
    private SdOrderItemSearchRepository sdOrderItemSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSdOrderItemMockMvc;

    private SdOrderItem sdOrderItem;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SdOrderItemResource sdOrderItemResource = new SdOrderItemResource();
        ReflectionTestUtils.setField(sdOrderItemResource, "sdOrderItemService", sdOrderItemService);
        this.restSdOrderItemMockMvc = MockMvcBuilders.standaloneSetup(sdOrderItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SdOrderItem createEntity(EntityManager em) {
        SdOrderItem sdOrderItem = new SdOrderItem()
        		.id(DEFAULT_ID)
                .orderHeaderNo(DEFAULT_ORDER_HEADER_NO)
                .consignDate(DEFAULT_CONSIGN_DATE)
                .fromStation(DEFAULT_FROM_STATION)
                .toStation(DEFAULT_TO_STATION)
                .middleStation(DEFAULT_MIDDLE_STATION)
                .consignerId(DEFAULT_CONSIGNER_ID)
                .consignerName(DEFAULT_CONSIGNER_NAME)
                .consignerAddress(DEFAULT_CONSIGNER_ADDRESS)
                .consignerPhone(DEFAULT_CONSIGNER_PHONE)
                .consignerMbPhone(DEFAULT_CONSIGNER_MB_PHONE)
                .consigneeId(DEFAULT_CONSIGNEE_ID)
                .consigneeName(DEFAULT_CONSIGNEE_NAME)
                .consigneePhone(DEFAULT_CONSIGNEE_PHONE)
                .consigneeMbPhone(DEFAULT_CONSIGNEE_MB_PHONE)
                .consigneeAddress(DEFAULT_CONSIGNEE_ADDRESS)
                .bankNo(DEFAULT_BANK_NO)
                .bankName(DEFAULT_BANK_NAME)
                .openName(DEFAULT_OPEN_NAME)
                .idCard(DEFAULT_ID_CARD)
                .payType(DEFAULT_PAY_TYPE)
                .cashPay(DEFAULT_CASH_PAY)
                .fetchPay(DEFAULT_FETCH_PAY)
                .receiptPay(DEFAULT_RECEIPT_PAY)
                .monthPay(DEFAULT_MONTH_PAY)
                .chargePay(DEFAULT_CHARGE_PAY)
                .transportType(DEFAULT_TRANSPORT_TYPE)
                .backRequire(DEFAULT_BACK_REQUIRE)
                .handOverType(DEFAULT_HAND_OVER_TYPE)
                .otherPay(DEFAULT_OTHER_PAY)
                .payExplain(DEFAULT_PAY_EXPLAIN)
                .remark(DEFAULT_REMARK)
                .kickBack(DEFAULT_KICK_BACK)
                .cashOwe(DEFAULT_CASH_OWE)
                .requireItem(DEFAULT_REQUIRE_ITEM)
                .tagged(DEFAULT_TAGGED)
                .envelopes(DEFAULT_ENVELOPES)
                .salesMan(DEFAULT_SALES_MAN)
                .operator(DEFAULT_OPERATOR)
                .orderStat(DEFAULT_ORDER_STAT);
        return sdOrderItem;
    }

    @Before
    public void initTest() {
        sdOrderItemSearchRepository.deleteAll();
        sdOrderItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createSdOrderItem() throws Exception {
        int databaseSizeBeforeCreate = sdOrderItemRepository.findAll().size();

        // Create the SdOrderItem

        restSdOrderItemMockMvc.perform(post("/api/sd-order-items")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sdOrderItem)))
                .andExpect(status().isCreated());

        // Validate the SdOrderItem in the database
        List<SdOrderItem> sdOrderItems = sdOrderItemRepository.findAll();
        assertThat(sdOrderItems).hasSize(databaseSizeBeforeCreate + 1);
        SdOrderItem testSdOrderItem = sdOrderItems.get(sdOrderItems.size() - 1);
        assertThat(testSdOrderItem.getId()).isEqualTo(DEFAULT_ID);
        assertThat(testSdOrderItem.getOrderHeaderNo()).isEqualTo(DEFAULT_ORDER_HEADER_NO);
        assertThat(testSdOrderItem.getConsignDate()).isEqualTo(DEFAULT_CONSIGN_DATE);
        assertThat(testSdOrderItem.getFromStation()).isEqualTo(DEFAULT_FROM_STATION);
        assertThat(testSdOrderItem.getToStation()).isEqualTo(DEFAULT_TO_STATION);
        assertThat(testSdOrderItem.getMiddleStation()).isEqualTo(DEFAULT_MIDDLE_STATION);
        assertThat(testSdOrderItem.getConsignerId()).isEqualTo(DEFAULT_CONSIGNER_ID);
        assertThat(testSdOrderItem.getConsignerName()).isEqualTo(DEFAULT_CONSIGNER_NAME);
        assertThat(testSdOrderItem.getConsignerAddress()).isEqualTo(DEFAULT_CONSIGNER_ADDRESS);
        assertThat(testSdOrderItem.getConsignerPhone()).isEqualTo(DEFAULT_CONSIGNER_PHONE);
        assertThat(testSdOrderItem.getConsignerMbPhone()).isEqualTo(DEFAULT_CONSIGNER_MB_PHONE);
        assertThat(testSdOrderItem.getConsigneeId()).isEqualTo(DEFAULT_CONSIGNEE_ID);
        assertThat(testSdOrderItem.getConsigneeName()).isEqualTo(DEFAULT_CONSIGNEE_NAME);
        assertThat(testSdOrderItem.getConsigneePhone()).isEqualTo(DEFAULT_CONSIGNEE_PHONE);
        assertThat(testSdOrderItem.getConsigneeMbPhone()).isEqualTo(DEFAULT_CONSIGNEE_MB_PHONE);
        assertThat(testSdOrderItem.getConsigneeAddress()).isEqualTo(DEFAULT_CONSIGNEE_ADDRESS);
        assertThat(testSdOrderItem.getBankNo()).isEqualTo(DEFAULT_BANK_NO);
        assertThat(testSdOrderItem.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testSdOrderItem.getOpenName()).isEqualTo(DEFAULT_OPEN_NAME);
        assertThat(testSdOrderItem.getIdCard()).isEqualTo(DEFAULT_ID_CARD);
        assertThat(testSdOrderItem.getPayType()).isEqualTo(DEFAULT_PAY_TYPE);
        assertThat(testSdOrderItem.getCashPay()).isEqualTo(DEFAULT_CASH_PAY);
        assertThat(testSdOrderItem.getFetchPay()).isEqualTo(DEFAULT_FETCH_PAY);
        assertThat(testSdOrderItem.getReceiptPay()).isEqualTo(DEFAULT_RECEIPT_PAY);
        assertThat(testSdOrderItem.getMonthPay()).isEqualTo(DEFAULT_MONTH_PAY);
        assertThat(testSdOrderItem.getChargePay()).isEqualTo(DEFAULT_CHARGE_PAY);
        assertThat(testSdOrderItem.getTransportType()).isEqualTo(DEFAULT_TRANSPORT_TYPE);
        assertThat(testSdOrderItem.getBackRequire()).isEqualTo(DEFAULT_BACK_REQUIRE);
        assertThat(testSdOrderItem.getHandOverType()).isEqualTo(DEFAULT_HAND_OVER_TYPE);
        assertThat(testSdOrderItem.getOtherPay()).isEqualTo(DEFAULT_OTHER_PAY);
        assertThat(testSdOrderItem.getPayExplain()).isEqualTo(DEFAULT_PAY_EXPLAIN);
        assertThat(testSdOrderItem.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testSdOrderItem.getKickBack()).isEqualTo(DEFAULT_KICK_BACK);
        assertThat(testSdOrderItem.getCashOwe()).isEqualTo(DEFAULT_CASH_OWE);
        assertThat(testSdOrderItem.getRequireItem()).isEqualTo(DEFAULT_REQUIRE_ITEM);
        assertThat(testSdOrderItem.getTagged()).isEqualTo(DEFAULT_TAGGED);
        assertThat(testSdOrderItem.getEnvelopes()).isEqualTo(DEFAULT_ENVELOPES);
        assertThat(testSdOrderItem.getSalesMan()).isEqualTo(DEFAULT_SALES_MAN);
        assertThat(testSdOrderItem.getOperator()).isEqualTo(DEFAULT_OPERATOR);
        assertThat(testSdOrderItem.getOrderStat()).isEqualTo(DEFAULT_ORDER_STAT);

        // Validate the SdOrderItem in ElasticSearch
        SdOrderItem sdOrderItemEs = sdOrderItemSearchRepository.findOne(testSdOrderItem.getId());
        assertThat(sdOrderItemEs).isEqualToComparingFieldByField(testSdOrderItem);
    }

    @Test
    @Transactional
    public void getAllSdOrderItems() throws Exception {
        // Initialize the database
        sdOrderItemRepository.saveAndFlush(sdOrderItem);

        // Get all the sdOrderItems
        restSdOrderItemMockMvc.perform(get("/api/sd-order-items?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sdOrderItem.getId())))
                .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO.toString())))
                .andExpect(jsonPath("$.[*].orderHeaderNo").value(hasItem(DEFAULT_ORDER_HEADER_NO.toString())))
                .andExpect(jsonPath("$.[*].itemNo").value(hasItem(DEFAULT_ITEM_NO.toString())))
                .andExpect(jsonPath("$.[*].consignDate").value(hasItem(DEFAULT_CONSIGN_DATE.toString())))
                .andExpect(jsonPath("$.[*].fromStation").value(hasItem(DEFAULT_FROM_STATION.toString())))
                .andExpect(jsonPath("$.[*].toStation").value(hasItem(DEFAULT_TO_STATION.toString())))
                .andExpect(jsonPath("$.[*].middleStation").value(hasItem(DEFAULT_MIDDLE_STATION.toString())))
                .andExpect(jsonPath("$.[*].consignerId").value(hasItem(DEFAULT_CONSIGNER_ID.toString())))
                .andExpect(jsonPath("$.[*].consignerName").value(hasItem(DEFAULT_CONSIGNER_NAME.toString())))
                .andExpect(jsonPath("$.[*].consignerAddress").value(hasItem(DEFAULT_CONSIGNER_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].consignerPhone").value(hasItem(DEFAULT_CONSIGNER_PHONE.toString())))
                .andExpect(jsonPath("$.[*].consignerMbPhone").value(hasItem(DEFAULT_CONSIGNER_MB_PHONE.intValue())))
                .andExpect(jsonPath("$.[*].consigneeId").value(hasItem(DEFAULT_CONSIGNEE_ID.toString())))
                .andExpect(jsonPath("$.[*].consigneeName").value(hasItem(DEFAULT_CONSIGNEE_NAME.toString())))
                .andExpect(jsonPath("$.[*].consigneePhone").value(hasItem(DEFAULT_CONSIGNEE_PHONE.toString())))
                .andExpect(jsonPath("$.[*].consigneeMbPhone").value(hasItem(DEFAULT_CONSIGNEE_MB_PHONE.intValue())))
                .andExpect(jsonPath("$.[*].consigneeAddress").value(hasItem(DEFAULT_CONSIGNEE_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].bankNo").value(hasItem(DEFAULT_BANK_NO.intValue())))
                .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME.toString())))
                .andExpect(jsonPath("$.[*].openName").value(hasItem(DEFAULT_OPEN_NAME.toString())))
                .andExpect(jsonPath("$.[*].idCard").value(hasItem(DEFAULT_ID_CARD.toString())))
                .andExpect(jsonPath("$.[*].payType").value(hasItem(DEFAULT_PAY_TYPE.toString())))
                .andExpect(jsonPath("$.[*].cashPay").value(hasItem(DEFAULT_CASH_PAY.intValue())))
                .andExpect(jsonPath("$.[*].fetchPay").value(hasItem(DEFAULT_FETCH_PAY.intValue())))
                .andExpect(jsonPath("$.[*].receiptPay").value(hasItem(DEFAULT_RECEIPT_PAY.intValue())))
                .andExpect(jsonPath("$.[*].monthPay").value(hasItem(DEFAULT_MONTH_PAY.intValue())))
                .andExpect(jsonPath("$.[*].chargePay").value(hasItem(DEFAULT_CHARGE_PAY.intValue())))
                .andExpect(jsonPath("$.[*].transportType").value(hasItem(DEFAULT_TRANSPORT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].backRequire").value(hasItem(DEFAULT_BACK_REQUIRE.toString())))
                .andExpect(jsonPath("$.[*].handOverType").value(hasItem(DEFAULT_HAND_OVER_TYPE.toString())))
                .andExpect(jsonPath("$.[*].otherPay").value(hasItem(DEFAULT_OTHER_PAY.toString())))
                .andExpect(jsonPath("$.[*].payExplain").value(hasItem(DEFAULT_PAY_EXPLAIN.toString())))
                .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
                .andExpect(jsonPath("$.[*].kickBack").value(hasItem(DEFAULT_KICK_BACK.toString())))
                .andExpect(jsonPath("$.[*].cashOwe").value(hasItem(DEFAULT_CASH_OWE.toString())))
                .andExpect(jsonPath("$.[*].requireItem").value(hasItem(DEFAULT_REQUIRE_ITEM.toString())))
                .andExpect(jsonPath("$.[*].tagged").value(hasItem(DEFAULT_TAGGED.toString())))
                .andExpect(jsonPath("$.[*].envelopes").value(hasItem(DEFAULT_ENVELOPES.toString())))
                .andExpect(jsonPath("$.[*].salesMan").value(hasItem(DEFAULT_SALES_MAN.toString())))
                .andExpect(jsonPath("$.[*].operator").value(hasItem(DEFAULT_OPERATOR.toString())))
                .andExpect(jsonPath("$.[*].orderStat").value(hasItem(DEFAULT_ORDER_STAT.toString())));
    }

    @Test
    @Transactional
    public void getSdOrderItem() throws Exception {
        // Initialize the database
        sdOrderItemRepository.saveAndFlush(sdOrderItem);

        // Get the sdOrderItem
        restSdOrderItemMockMvc.perform(get("/api/sd-order-items/{id}", sdOrderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sdOrderItem.getId()))
            .andExpect(jsonPath("$.orderNo").value(DEFAULT_ORDER_NO.toString()))
            .andExpect(jsonPath("$.orderHeaderNo").value(DEFAULT_ORDER_HEADER_NO.toString()))
            .andExpect(jsonPath("$.itemNo").value(DEFAULT_ITEM_NO.toString()))
            .andExpect(jsonPath("$.consignDate").value(DEFAULT_CONSIGN_DATE.toString()))
            .andExpect(jsonPath("$.fromStation").value(DEFAULT_FROM_STATION.toString()))
            .andExpect(jsonPath("$.toStation").value(DEFAULT_TO_STATION.toString()))
            .andExpect(jsonPath("$.middleStation").value(DEFAULT_MIDDLE_STATION.toString()))
            .andExpect(jsonPath("$.consignerId").value(DEFAULT_CONSIGNER_ID.toString()))
            .andExpect(jsonPath("$.consignerName").value(DEFAULT_CONSIGNER_NAME.toString()))
            .andExpect(jsonPath("$.consignerAddress").value(DEFAULT_CONSIGNER_ADDRESS.toString()))
            .andExpect(jsonPath("$.consignerPhone").value(DEFAULT_CONSIGNER_PHONE.toString()))
            .andExpect(jsonPath("$.consignerMbPhone").value(DEFAULT_CONSIGNER_MB_PHONE.intValue()))
            .andExpect(jsonPath("$.consigneeId").value(DEFAULT_CONSIGNEE_ID.toString()))
            .andExpect(jsonPath("$.consigneeName").value(DEFAULT_CONSIGNEE_NAME.toString()))
            .andExpect(jsonPath("$.consigneePhone").value(DEFAULT_CONSIGNEE_PHONE.toString()))
            .andExpect(jsonPath("$.consigneeMbPhone").value(DEFAULT_CONSIGNEE_MB_PHONE.intValue()))
            .andExpect(jsonPath("$.consigneeAddress").value(DEFAULT_CONSIGNEE_ADDRESS.toString()))
            .andExpect(jsonPath("$.bankNo").value(DEFAULT_BANK_NO.intValue()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME.toString()))
            .andExpect(jsonPath("$.openName").value(DEFAULT_OPEN_NAME.toString()))
            .andExpect(jsonPath("$.idCard").value(DEFAULT_ID_CARD.toString()))
            .andExpect(jsonPath("$.payType").value(DEFAULT_PAY_TYPE.toString()))
            .andExpect(jsonPath("$.cashPay").value(DEFAULT_CASH_PAY.intValue()))
            .andExpect(jsonPath("$.fetchPay").value(DEFAULT_FETCH_PAY.intValue()))
            .andExpect(jsonPath("$.receiptPay").value(DEFAULT_RECEIPT_PAY.intValue()))
            .andExpect(jsonPath("$.monthPay").value(DEFAULT_MONTH_PAY.intValue()))
            .andExpect(jsonPath("$.chargePay").value(DEFAULT_CHARGE_PAY.intValue()))
            .andExpect(jsonPath("$.transportType").value(DEFAULT_TRANSPORT_TYPE.toString()))
            .andExpect(jsonPath("$.backRequire").value(DEFAULT_BACK_REQUIRE.toString()))
            .andExpect(jsonPath("$.handOverType").value(DEFAULT_HAND_OVER_TYPE.toString()))
            .andExpect(jsonPath("$.otherPay").value(DEFAULT_OTHER_PAY.toString()))
            .andExpect(jsonPath("$.payExplain").value(DEFAULT_PAY_EXPLAIN.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.kickBack").value(DEFAULT_KICK_BACK.toString()))
            .andExpect(jsonPath("$.cashOwe").value(DEFAULT_CASH_OWE.toString()))
            .andExpect(jsonPath("$.requireItem").value(DEFAULT_REQUIRE_ITEM.toString()))
            .andExpect(jsonPath("$.tagged").value(DEFAULT_TAGGED.toString()))
            .andExpect(jsonPath("$.envelopes").value(DEFAULT_ENVELOPES.toString()))
            .andExpect(jsonPath("$.salesMan").value(DEFAULT_SALES_MAN.toString()))
            .andExpect(jsonPath("$.operator").value(DEFAULT_OPERATOR.toString()))
            .andExpect(jsonPath("$.orderStat").value(DEFAULT_ORDER_STAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSdOrderItem() throws Exception {
        // Get the sdOrderItem
        restSdOrderItemMockMvc.perform(get("/api/sd-order-items/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSdOrderItem() throws Exception {
        // Initialize the database
        sdOrderItemService.save(sdOrderItem);

        int databaseSizeBeforeUpdate = sdOrderItemRepository.findAll().size();

        // Update the sdOrderItem
        SdOrderItem updatedSdOrderItem = sdOrderItemRepository.findOne(sdOrderItem.getId());
        updatedSdOrderItem
                .orderHeaderNo(UPDATED_ORDER_HEADER_NO)
                .consignDate(UPDATED_CONSIGN_DATE)
                .fromStation(UPDATED_FROM_STATION)
                .toStation(UPDATED_TO_STATION)
                .middleStation(UPDATED_MIDDLE_STATION)
                .consignerId(UPDATED_CONSIGNER_ID)
                .consignerName(UPDATED_CONSIGNER_NAME)
                .consignerAddress(UPDATED_CONSIGNER_ADDRESS)
                .consignerPhone(UPDATED_CONSIGNER_PHONE)
                .consignerMbPhone(UPDATED_CONSIGNER_MB_PHONE)
                .consigneeId(UPDATED_CONSIGNEE_ID)
                .consigneeName(UPDATED_CONSIGNEE_NAME)
                .consigneePhone(UPDATED_CONSIGNEE_PHONE)
                .consigneeMbPhone(UPDATED_CONSIGNEE_MB_PHONE)
                .consigneeAddress(UPDATED_CONSIGNEE_ADDRESS)
                .bankNo(UPDATED_BANK_NO)
                .bankName(UPDATED_BANK_NAME)
                .openName(UPDATED_OPEN_NAME)
                .idCard(UPDATED_ID_CARD)
                .payType(UPDATED_PAY_TYPE)
                .cashPay(UPDATED_CASH_PAY)
                .fetchPay(UPDATED_FETCH_PAY)
                .receiptPay(UPDATED_RECEIPT_PAY)
                .monthPay(UPDATED_MONTH_PAY)
                .chargePay(UPDATED_CHARGE_PAY)
                .transportType(UPDATED_TRANSPORT_TYPE)
                .backRequire(UPDATED_BACK_REQUIRE)
                .handOverType(UPDATED_HAND_OVER_TYPE)
                .otherPay(UPDATED_OTHER_PAY)
                .payExplain(UPDATED_PAY_EXPLAIN)
                .remark(UPDATED_REMARK)
                .kickBack(UPDATED_KICK_BACK)
                .cashOwe(UPDATED_CASH_OWE)
                .requireItem(UPDATED_REQUIRE_ITEM)
                .tagged(UPDATED_TAGGED)
                .envelopes(UPDATED_ENVELOPES)
                .salesMan(UPDATED_SALES_MAN)
                .operator(UPDATED_OPERATOR)
                .orderStat(UPDATED_ORDER_STAT);

        restSdOrderItemMockMvc.perform(put("/api/sd-order-items")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSdOrderItem)))
                .andExpect(status().isOk());

        // Validate the SdOrderItem in the database
        List<SdOrderItem> sdOrderItems = sdOrderItemRepository.findAll();
        assertThat(sdOrderItems).hasSize(databaseSizeBeforeUpdate);
        SdOrderItem testSdOrderItem = sdOrderItems.get(sdOrderItems.size() - 1);
        assertThat(testSdOrderItem.getOrderHeaderNo()).isEqualTo(UPDATED_ORDER_HEADER_NO);
        assertThat(testSdOrderItem.getConsignDate()).isEqualTo(UPDATED_CONSIGN_DATE);
        assertThat(testSdOrderItem.getFromStation()).isEqualTo(UPDATED_FROM_STATION);
        assertThat(testSdOrderItem.getToStation()).isEqualTo(UPDATED_TO_STATION);
        assertThat(testSdOrderItem.getMiddleStation()).isEqualTo(UPDATED_MIDDLE_STATION);
        assertThat(testSdOrderItem.getConsignerId()).isEqualTo(UPDATED_CONSIGNER_ID);
        assertThat(testSdOrderItem.getConsignerName()).isEqualTo(UPDATED_CONSIGNER_NAME);
        assertThat(testSdOrderItem.getConsignerAddress()).isEqualTo(UPDATED_CONSIGNER_ADDRESS);
        assertThat(testSdOrderItem.getConsignerPhone()).isEqualTo(UPDATED_CONSIGNER_PHONE);
        assertThat(testSdOrderItem.getConsignerMbPhone()).isEqualTo(UPDATED_CONSIGNER_MB_PHONE);
        assertThat(testSdOrderItem.getConsigneeId()).isEqualTo(UPDATED_CONSIGNEE_ID);
        assertThat(testSdOrderItem.getConsigneeName()).isEqualTo(UPDATED_CONSIGNEE_NAME);
        assertThat(testSdOrderItem.getConsigneePhone()).isEqualTo(UPDATED_CONSIGNEE_PHONE);
        assertThat(testSdOrderItem.getConsigneeMbPhone()).isEqualTo(UPDATED_CONSIGNEE_MB_PHONE);
        assertThat(testSdOrderItem.getConsigneeAddress()).isEqualTo(UPDATED_CONSIGNEE_ADDRESS);
        assertThat(testSdOrderItem.getBankNo()).isEqualTo(UPDATED_BANK_NO);
        assertThat(testSdOrderItem.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testSdOrderItem.getOpenName()).isEqualTo(UPDATED_OPEN_NAME);
        assertThat(testSdOrderItem.getIdCard()).isEqualTo(UPDATED_ID_CARD);
        assertThat(testSdOrderItem.getPayType()).isEqualTo(UPDATED_PAY_TYPE);
        assertThat(testSdOrderItem.getCashPay()).isEqualTo(UPDATED_CASH_PAY);
        assertThat(testSdOrderItem.getFetchPay()).isEqualTo(UPDATED_FETCH_PAY);
        assertThat(testSdOrderItem.getReceiptPay()).isEqualTo(UPDATED_RECEIPT_PAY);
        assertThat(testSdOrderItem.getMonthPay()).isEqualTo(UPDATED_MONTH_PAY);
        assertThat(testSdOrderItem.getChargePay()).isEqualTo(UPDATED_CHARGE_PAY);
        assertThat(testSdOrderItem.getTransportType()).isEqualTo(UPDATED_TRANSPORT_TYPE);
        assertThat(testSdOrderItem.getBackRequire()).isEqualTo(UPDATED_BACK_REQUIRE);
        assertThat(testSdOrderItem.getHandOverType()).isEqualTo(UPDATED_HAND_OVER_TYPE);
        assertThat(testSdOrderItem.getOtherPay()).isEqualTo(UPDATED_OTHER_PAY);
        assertThat(testSdOrderItem.getPayExplain()).isEqualTo(UPDATED_PAY_EXPLAIN);
        assertThat(testSdOrderItem.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testSdOrderItem.getKickBack()).isEqualTo(UPDATED_KICK_BACK);
        assertThat(testSdOrderItem.getCashOwe()).isEqualTo(UPDATED_CASH_OWE);
        assertThat(testSdOrderItem.getRequireItem()).isEqualTo(UPDATED_REQUIRE_ITEM);
        assertThat(testSdOrderItem.getTagged()).isEqualTo(UPDATED_TAGGED);
        assertThat(testSdOrderItem.getEnvelopes()).isEqualTo(UPDATED_ENVELOPES);
        assertThat(testSdOrderItem.getSalesMan()).isEqualTo(UPDATED_SALES_MAN);
        assertThat(testSdOrderItem.getOperator()).isEqualTo(UPDATED_OPERATOR);
        assertThat(testSdOrderItem.getOrderStat()).isEqualTo(UPDATED_ORDER_STAT);

        // Validate the SdOrderItem in ElasticSearch
        SdOrderItem sdOrderItemEs = sdOrderItemSearchRepository.findOne(testSdOrderItem.getId());
        assertThat(sdOrderItemEs).isEqualToComparingFieldByField(testSdOrderItem);
    }

    @Test
    @Transactional
    public void deleteSdOrderItem() throws Exception {
        // Initialize the database
        sdOrderItemService.save(sdOrderItem);

        int databaseSizeBeforeDelete = sdOrderItemRepository.findAll().size();

        // Get the sdOrderItem
        restSdOrderItemMockMvc.perform(delete("/api/sd-order-items/{id}", sdOrderItem.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean sdOrderItemExistsInEs = sdOrderItemSearchRepository.exists(sdOrderItem.getId());
        assertThat(sdOrderItemExistsInEs).isFalse();

        // Validate the database is empty
        List<SdOrderItem> sdOrderItems = sdOrderItemRepository.findAll();
        assertThat(sdOrderItems).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSdOrderItem() throws Exception {
        // Initialize the database
        sdOrderItemService.save(sdOrderItem);

        // Search the sdOrderItem
        restSdOrderItemMockMvc.perform(get("/api/_search/sd-order-items?query=id:" + sdOrderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sdOrderItem.getId())))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO.toString())))
            .andExpect(jsonPath("$.[*].orderHeaderNo").value(hasItem(DEFAULT_ORDER_HEADER_NO.toString())))
            .andExpect(jsonPath("$.[*].itemNo").value(hasItem(DEFAULT_ITEM_NO.toString())))
            .andExpect(jsonPath("$.[*].consignDate").value(hasItem(DEFAULT_CONSIGN_DATE.toString())))
            .andExpect(jsonPath("$.[*].fromStation").value(hasItem(DEFAULT_FROM_STATION.toString())))
            .andExpect(jsonPath("$.[*].toStation").value(hasItem(DEFAULT_TO_STATION.toString())))
            .andExpect(jsonPath("$.[*].middleStation").value(hasItem(DEFAULT_MIDDLE_STATION.toString())))
            .andExpect(jsonPath("$.[*].consignerId").value(hasItem(DEFAULT_CONSIGNER_ID.toString())))
            .andExpect(jsonPath("$.[*].consignerName").value(hasItem(DEFAULT_CONSIGNER_NAME.toString())))
            .andExpect(jsonPath("$.[*].consignerAddress").value(hasItem(DEFAULT_CONSIGNER_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].consignerPhone").value(hasItem(DEFAULT_CONSIGNER_PHONE.toString())))
            .andExpect(jsonPath("$.[*].consignerMbPhone").value(hasItem(DEFAULT_CONSIGNER_MB_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].consigneeId").value(hasItem(DEFAULT_CONSIGNEE_ID.toString())))
            .andExpect(jsonPath("$.[*].consigneeName").value(hasItem(DEFAULT_CONSIGNEE_NAME.toString())))
            .andExpect(jsonPath("$.[*].consigneePhone").value(hasItem(DEFAULT_CONSIGNEE_PHONE.toString())))
            .andExpect(jsonPath("$.[*].consigneeMbPhone").value(hasItem(DEFAULT_CONSIGNEE_MB_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].consigneeAddress").value(hasItem(DEFAULT_CONSIGNEE_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].bankNo").value(hasItem(DEFAULT_BANK_NO.intValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME.toString())))
            .andExpect(jsonPath("$.[*].openName").value(hasItem(DEFAULT_OPEN_NAME.toString())))
            .andExpect(jsonPath("$.[*].idCard").value(hasItem(DEFAULT_ID_CARD.toString())))
            .andExpect(jsonPath("$.[*].payType").value(hasItem(DEFAULT_PAY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].cashPay").value(hasItem(DEFAULT_CASH_PAY.intValue())))
            .andExpect(jsonPath("$.[*].fetchPay").value(hasItem(DEFAULT_FETCH_PAY.intValue())))
            .andExpect(jsonPath("$.[*].receiptPay").value(hasItem(DEFAULT_RECEIPT_PAY.intValue())))
            .andExpect(jsonPath("$.[*].monthPay").value(hasItem(DEFAULT_MONTH_PAY.intValue())))
            .andExpect(jsonPath("$.[*].chargePay").value(hasItem(DEFAULT_CHARGE_PAY.intValue())))
            .andExpect(jsonPath("$.[*].transportType").value(hasItem(DEFAULT_TRANSPORT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].backRequire").value(hasItem(DEFAULT_BACK_REQUIRE.toString())))
            .andExpect(jsonPath("$.[*].handOverType").value(hasItem(DEFAULT_HAND_OVER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].otherPay").value(hasItem(DEFAULT_OTHER_PAY.toString())))
            .andExpect(jsonPath("$.[*].payExplain").value(hasItem(DEFAULT_PAY_EXPLAIN.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].kickBack").value(hasItem(DEFAULT_KICK_BACK.toString())))
            .andExpect(jsonPath("$.[*].cashOwe").value(hasItem(DEFAULT_CASH_OWE.toString())))
            .andExpect(jsonPath("$.[*].requireItem").value(hasItem(DEFAULT_REQUIRE_ITEM.toString())))
            .andExpect(jsonPath("$.[*].tagged").value(hasItem(DEFAULT_TAGGED.toString())))
            .andExpect(jsonPath("$.[*].envelopes").value(hasItem(DEFAULT_ENVELOPES.toString())))
            .andExpect(jsonPath("$.[*].salesMan").value(hasItem(DEFAULT_SALES_MAN.toString())))
            .andExpect(jsonPath("$.[*].operator").value(hasItem(DEFAULT_OPERATOR.toString())))
            .andExpect(jsonPath("$.[*].orderStat").value(hasItem(DEFAULT_ORDER_STAT.toString())));
    }
}
