package com.sude.sd.web.rest;

import com.sude.sd.SudeApp;

import com.sude.sd.domain.SdOrderHeader;
import com.sude.sd.repository.SdOrderHeaderRepository;
import com.sude.sd.service.SdOrderHeaderService;
import com.sude.sd.repository.search.SdOrderHeaderSearchRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SdOrderHeaderResource REST controller.
 *
 * @see SdOrderHeaderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SudeApp.class)
public class SdOrderHeaderResourceIntTest {

    private static final String DEFAULT_ORDER_HEADER_NO = "AAAAA";
    private static final String UPDATED_ORDER_HEADER_NO = "BBBBB";

    private static final String DEFAULT_CAR_NO = "AAAAA";
    private static final String UPDATED_CAR_NO = "BBBBB";

    private static final String DEFAULT_DRIVER_NAME = "AAAAA";
    private static final String UPDATED_DRIVER_NAME = "BBBBB";

    private static final Long DEFAULT_MOBILE_PHONE = 1L;
    private static final Long UPDATED_MOBILE_PHONE = 2L;

    private static final String DEFAULT_DEPART_BATCH = "AAAAA";
    private static final String UPDATED_DEPART_BATCH = "BBBBB";

    private static final String DEFAULT_FROM_STATION = "AAAAA";
    private static final String UPDATED_FROM_STATION = "BBBBB";

    private static final String DEFAULT_TO_STATION = "AAAAA";
    private static final String UPDATED_TO_STATION = "BBBBB";

    private static final String DEFAULT_UNLOAD_PLACE = "AAAAA";
    private static final String UPDATED_UNLOAD_PLACE = "BBBBB";

    private static final BigDecimal DEFAULT_CASH_PAY = new BigDecimal(1);
    private static final BigDecimal UPDATED_CASH_PAY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DRIVER_COLLECTION = new BigDecimal(1);
    private static final BigDecimal UPDATED_DRIVER_COLLECTION = new BigDecimal(2);

    private static final BigDecimal DEFAULT_HANDLING_CHARGES = new BigDecimal(1);
    private static final BigDecimal UPDATED_HANDLING_CHARGES = new BigDecimal(2);

    private static final BigDecimal DEFAULT_RECEIVE_SHIPMENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_RECEIVE_SHIPMENT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_OTHER_PAY = new BigDecimal(1);
    private static final BigDecimal UPDATED_OTHER_PAY = new BigDecimal(2);

    private static final ZonedDateTime DEFAULT_DEPARTURE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DEPARTURE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DEPARTURE_TIME_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_DEPARTURE_TIME);

    private static final Long DEFAULT_WEIGHT = 1L;
    private static final Long UPDATED_WEIGHT = 2L;

    private static final Long DEFAULT_PRACTICAL = 1L;
    private static final Long UPDATED_PRACTICAL = 2L;

    private static final String DEFAULT_LOAD_PARTER = "AAAAA";
    private static final String UPDATED_LOAD_PARTER = "BBBBB";

    private static final BigDecimal DEFAULT_REPLY = new BigDecimal(1);
    private static final BigDecimal UPDATED_REPLY = new BigDecimal(2);

    private static final String DEFAULT_OIL_CARD_NO = "AAAAA";
    private static final String UPDATED_OIL_CARD_NO = "BBBBB";

    private static final BigDecimal DEFAULT_OIL_CARD_BLANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_OIL_CARD_BLANCE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_FREIGHT_SUM = new BigDecimal(1);
    private static final BigDecimal UPDATED_FREIGHT_SUM = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ARRIVE_FREIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ARRIVE_FREIGHT = new BigDecimal(2);

    private static final String DEFAULT_ARRIVE_DRIVER = "AAAAA";
    private static final String UPDATED_ARRIVE_DRIVER = "BBBBB";

    private static final String DEFAULT_ORDER_HEAD_STAT = "AAAAA";
    private static final String UPDATED_ORDER_HEAD_STAT = "BBBBB";

    @Inject
    private SdOrderHeaderRepository sdOrderHeaderRepository;

    @Inject
    private SdOrderHeaderService sdOrderHeaderService;

    @Inject
    private SdOrderHeaderSearchRepository sdOrderHeaderSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSdOrderHeaderMockMvc;

    private SdOrderHeader sdOrderHeader;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SdOrderHeaderResource sdOrderHeaderResource = new SdOrderHeaderResource();
        ReflectionTestUtils.setField(sdOrderHeaderResource, "sdOrderHeaderService", sdOrderHeaderService);
        this.restSdOrderHeaderMockMvc = MockMvcBuilders.standaloneSetup(sdOrderHeaderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SdOrderHeader createEntity(EntityManager em) {
        SdOrderHeader sdOrderHeader = new SdOrderHeader()
                .orderHeaderNo(DEFAULT_ORDER_HEADER_NO)
                .carNo(DEFAULT_CAR_NO)
                .driverName(DEFAULT_DRIVER_NAME)
                .mobilePhone(DEFAULT_MOBILE_PHONE)
                .departBatch(DEFAULT_DEPART_BATCH)
                .fromStation(DEFAULT_FROM_STATION)
                .toStation(DEFAULT_TO_STATION)
                .unloadPlace(DEFAULT_UNLOAD_PLACE)
                .cashPay(DEFAULT_CASH_PAY)
                .driverCollection(DEFAULT_DRIVER_COLLECTION)
                .handlingCharges(DEFAULT_HANDLING_CHARGES)
                .receiveShipment(DEFAULT_RECEIVE_SHIPMENT)
                .otherPay(DEFAULT_OTHER_PAY)
                .departureTime(DEFAULT_DEPARTURE_TIME)
                .weight(DEFAULT_WEIGHT)
                .practical(DEFAULT_PRACTICAL)
                .loadParter(DEFAULT_LOAD_PARTER)
                .reply(DEFAULT_REPLY)
                .oilCardNo(DEFAULT_OIL_CARD_NO)
                .oilCardBlance(DEFAULT_OIL_CARD_BLANCE)
                .freightSum(DEFAULT_FREIGHT_SUM)
                .arriveFreight(DEFAULT_ARRIVE_FREIGHT)
                .arriveDriver(DEFAULT_ARRIVE_DRIVER)
                .orderHeadStat(DEFAULT_ORDER_HEAD_STAT);
        return sdOrderHeader;
    }

    @Before
    public void initTest() {
        sdOrderHeaderSearchRepository.deleteAll();
        sdOrderHeader = createEntity(em);
    }

    @Test
    @Transactional
    public void createSdOrderHeader() throws Exception {
        int databaseSizeBeforeCreate = sdOrderHeaderRepository.findAll().size();

        // Create the SdOrderHeader

        restSdOrderHeaderMockMvc.perform(post("/api/sd-order-headers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sdOrderHeader)))
                .andExpect(status().isCreated());

        // Validate the SdOrderHeader in the database
        List<SdOrderHeader> sdOrderHeaders = sdOrderHeaderRepository.findAll();
        assertThat(sdOrderHeaders).hasSize(databaseSizeBeforeCreate + 1);
        SdOrderHeader testSdOrderHeader = sdOrderHeaders.get(sdOrderHeaders.size() - 1);
        assertThat(testSdOrderHeader.getOrderHeaderNo()).isEqualTo(DEFAULT_ORDER_HEADER_NO);
        assertThat(testSdOrderHeader.getCarNo()).isEqualTo(DEFAULT_CAR_NO);
        assertThat(testSdOrderHeader.getDriverName()).isEqualTo(DEFAULT_DRIVER_NAME);
        assertThat(testSdOrderHeader.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testSdOrderHeader.getDepartBatch()).isEqualTo(DEFAULT_DEPART_BATCH);
        assertThat(testSdOrderHeader.getFromStation()).isEqualTo(DEFAULT_FROM_STATION);
        assertThat(testSdOrderHeader.getToStation()).isEqualTo(DEFAULT_TO_STATION);
        assertThat(testSdOrderHeader.getUnloadPlace()).isEqualTo(DEFAULT_UNLOAD_PLACE);
        assertThat(testSdOrderHeader.getCashPay()).isEqualTo(DEFAULT_CASH_PAY);
        assertThat(testSdOrderHeader.getDriverCollection()).isEqualTo(DEFAULT_DRIVER_COLLECTION);
        assertThat(testSdOrderHeader.getHandlingCharges()).isEqualTo(DEFAULT_HANDLING_CHARGES);
        assertThat(testSdOrderHeader.getReceiveShipment()).isEqualTo(DEFAULT_RECEIVE_SHIPMENT);
        assertThat(testSdOrderHeader.getOtherPay()).isEqualTo(DEFAULT_OTHER_PAY);
        assertThat(testSdOrderHeader.getDepartureTime()).isEqualTo(DEFAULT_DEPARTURE_TIME);
        assertThat(testSdOrderHeader.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testSdOrderHeader.getPractical()).isEqualTo(DEFAULT_PRACTICAL);
        assertThat(testSdOrderHeader.getLoadParter()).isEqualTo(DEFAULT_LOAD_PARTER);
        assertThat(testSdOrderHeader.getReply()).isEqualTo(DEFAULT_REPLY);
        assertThat(testSdOrderHeader.getOilCardNo()).isEqualTo(DEFAULT_OIL_CARD_NO);
        assertThat(testSdOrderHeader.getOilCardBlance()).isEqualTo(DEFAULT_OIL_CARD_BLANCE);
        assertThat(testSdOrderHeader.getFreightSum()).isEqualTo(DEFAULT_FREIGHT_SUM);
        assertThat(testSdOrderHeader.getArriveFreight()).isEqualTo(DEFAULT_ARRIVE_FREIGHT);
        assertThat(testSdOrderHeader.getArriveDriver()).isEqualTo(DEFAULT_ARRIVE_DRIVER);
        assertThat(testSdOrderHeader.getOrderHeadStat()).isEqualTo(DEFAULT_ORDER_HEAD_STAT);

        // Validate the SdOrderHeader in ElasticSearch
        SdOrderHeader sdOrderHeaderEs = sdOrderHeaderSearchRepository.findOne(testSdOrderHeader.getId());
        assertThat(sdOrderHeaderEs).isEqualToComparingFieldByField(testSdOrderHeader);
    }

    @Test
    @Transactional
    public void getAllSdOrderHeaders() throws Exception {
        // Initialize the database
        sdOrderHeaderRepository.saveAndFlush(sdOrderHeader);

        // Get all the sdOrderHeaders
        restSdOrderHeaderMockMvc.perform(get("/api/sd-order-headers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sdOrderHeader.getId().intValue())))
                .andExpect(jsonPath("$.[*].orderHeaderNo").value(hasItem(DEFAULT_ORDER_HEADER_NO.toString())))
                .andExpect(jsonPath("$.[*].carNo").value(hasItem(DEFAULT_CAR_NO.toString())))
                .andExpect(jsonPath("$.[*].driverName").value(hasItem(DEFAULT_DRIVER_NAME.toString())))
                .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.intValue())))
                .andExpect(jsonPath("$.[*].departBatch").value(hasItem(DEFAULT_DEPART_BATCH.toString())))
                .andExpect(jsonPath("$.[*].fromStation").value(hasItem(DEFAULT_FROM_STATION.toString())))
                .andExpect(jsonPath("$.[*].toStation").value(hasItem(DEFAULT_TO_STATION.toString())))
                .andExpect(jsonPath("$.[*].unloadPlace").value(hasItem(DEFAULT_UNLOAD_PLACE.toString())))
                .andExpect(jsonPath("$.[*].cashPay").value(hasItem(DEFAULT_CASH_PAY.intValue())))
                .andExpect(jsonPath("$.[*].driverCollection").value(hasItem(DEFAULT_DRIVER_COLLECTION.intValue())))
                .andExpect(jsonPath("$.[*].handlingCharges").value(hasItem(DEFAULT_HANDLING_CHARGES.intValue())))
                .andExpect(jsonPath("$.[*].receiveShipment").value(hasItem(DEFAULT_RECEIVE_SHIPMENT.intValue())))
                .andExpect(jsonPath("$.[*].otherPay").value(hasItem(DEFAULT_OTHER_PAY.intValue())))
                .andExpect(jsonPath("$.[*].departureTime").value(hasItem(DEFAULT_DEPARTURE_TIME_STR)))
                .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
                .andExpect(jsonPath("$.[*].practical").value(hasItem(DEFAULT_PRACTICAL.intValue())))
                .andExpect(jsonPath("$.[*].loadParter").value(hasItem(DEFAULT_LOAD_PARTER.toString())))
                .andExpect(jsonPath("$.[*].reply").value(hasItem(DEFAULT_REPLY.intValue())))
                .andExpect(jsonPath("$.[*].oilCardNo").value(hasItem(DEFAULT_OIL_CARD_NO.toString())))
                .andExpect(jsonPath("$.[*].oilCardBlance").value(hasItem(DEFAULT_OIL_CARD_BLANCE.intValue())))
                .andExpect(jsonPath("$.[*].freightSum").value(hasItem(DEFAULT_FREIGHT_SUM.intValue())))
                .andExpect(jsonPath("$.[*].arriveFreight").value(hasItem(DEFAULT_ARRIVE_FREIGHT.intValue())))
                .andExpect(jsonPath("$.[*].arriveDriver").value(hasItem(DEFAULT_ARRIVE_DRIVER.toString())))
                .andExpect(jsonPath("$.[*].orderHeadStat").value(hasItem(DEFAULT_ORDER_HEAD_STAT.toString())));
    }

    @Test
    @Transactional
    public void getSdOrderHeader() throws Exception {
        // Initialize the database
        sdOrderHeaderRepository.saveAndFlush(sdOrderHeader);

        // Get the sdOrderHeader
        restSdOrderHeaderMockMvc.perform(get("/api/sd-order-headers/{id}", sdOrderHeader.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sdOrderHeader.getId().intValue()))
            .andExpect(jsonPath("$.orderHeaderNo").value(DEFAULT_ORDER_HEADER_NO.toString()))
            .andExpect(jsonPath("$.carNo").value(DEFAULT_CAR_NO.toString()))
            .andExpect(jsonPath("$.driverName").value(DEFAULT_DRIVER_NAME.toString()))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE.intValue()))
            .andExpect(jsonPath("$.departBatch").value(DEFAULT_DEPART_BATCH.toString()))
            .andExpect(jsonPath("$.fromStation").value(DEFAULT_FROM_STATION.toString()))
            .andExpect(jsonPath("$.toStation").value(DEFAULT_TO_STATION.toString()))
            .andExpect(jsonPath("$.unloadPlace").value(DEFAULT_UNLOAD_PLACE.toString()))
            .andExpect(jsonPath("$.cashPay").value(DEFAULT_CASH_PAY.intValue()))
            .andExpect(jsonPath("$.driverCollection").value(DEFAULT_DRIVER_COLLECTION.intValue()))
            .andExpect(jsonPath("$.handlingCharges").value(DEFAULT_HANDLING_CHARGES.intValue()))
            .andExpect(jsonPath("$.receiveShipment").value(DEFAULT_RECEIVE_SHIPMENT.intValue()))
            .andExpect(jsonPath("$.otherPay").value(DEFAULT_OTHER_PAY.intValue()))
            .andExpect(jsonPath("$.departureTime").value(DEFAULT_DEPARTURE_TIME_STR))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.intValue()))
            .andExpect(jsonPath("$.practical").value(DEFAULT_PRACTICAL.intValue()))
            .andExpect(jsonPath("$.loadParter").value(DEFAULT_LOAD_PARTER.toString()))
            .andExpect(jsonPath("$.reply").value(DEFAULT_REPLY.intValue()))
            .andExpect(jsonPath("$.oilCardNo").value(DEFAULT_OIL_CARD_NO.toString()))
            .andExpect(jsonPath("$.oilCardBlance").value(DEFAULT_OIL_CARD_BLANCE.intValue()))
            .andExpect(jsonPath("$.freightSum").value(DEFAULT_FREIGHT_SUM.intValue()))
            .andExpect(jsonPath("$.arriveFreight").value(DEFAULT_ARRIVE_FREIGHT.intValue()))
            .andExpect(jsonPath("$.arriveDriver").value(DEFAULT_ARRIVE_DRIVER.toString()))
            .andExpect(jsonPath("$.orderHeadStat").value(DEFAULT_ORDER_HEAD_STAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSdOrderHeader() throws Exception {
        // Get the sdOrderHeader
        restSdOrderHeaderMockMvc.perform(get("/api/sd-order-headers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSdOrderHeader() throws Exception {
        // Initialize the database
        sdOrderHeaderService.save(sdOrderHeader);

        int databaseSizeBeforeUpdate = sdOrderHeaderRepository.findAll().size();

        // Update the sdOrderHeader
        SdOrderHeader updatedSdOrderHeader = sdOrderHeaderRepository.findOne(sdOrderHeader.getId());
        updatedSdOrderHeader
                .orderHeaderNo(UPDATED_ORDER_HEADER_NO)
                .carNo(UPDATED_CAR_NO)
                .driverName(UPDATED_DRIVER_NAME)
                .mobilePhone(UPDATED_MOBILE_PHONE)
                .departBatch(UPDATED_DEPART_BATCH)
                .fromStation(UPDATED_FROM_STATION)
                .toStation(UPDATED_TO_STATION)
                .unloadPlace(UPDATED_UNLOAD_PLACE)
                .cashPay(UPDATED_CASH_PAY)
                .driverCollection(UPDATED_DRIVER_COLLECTION)
                .handlingCharges(UPDATED_HANDLING_CHARGES)
                .receiveShipment(UPDATED_RECEIVE_SHIPMENT)
                .otherPay(UPDATED_OTHER_PAY)
                .departureTime(UPDATED_DEPARTURE_TIME)
                .weight(UPDATED_WEIGHT)
                .practical(UPDATED_PRACTICAL)
                .loadParter(UPDATED_LOAD_PARTER)
                .reply(UPDATED_REPLY)
                .oilCardNo(UPDATED_OIL_CARD_NO)
                .oilCardBlance(UPDATED_OIL_CARD_BLANCE)
                .freightSum(UPDATED_FREIGHT_SUM)
                .arriveFreight(UPDATED_ARRIVE_FREIGHT)
                .arriveDriver(UPDATED_ARRIVE_DRIVER)
                .orderHeadStat(UPDATED_ORDER_HEAD_STAT);

        restSdOrderHeaderMockMvc.perform(put("/api/sd-order-headers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSdOrderHeader)))
                .andExpect(status().isOk());

        // Validate the SdOrderHeader in the database
        List<SdOrderHeader> sdOrderHeaders = sdOrderHeaderRepository.findAll();
        assertThat(sdOrderHeaders).hasSize(databaseSizeBeforeUpdate);
        SdOrderHeader testSdOrderHeader = sdOrderHeaders.get(sdOrderHeaders.size() - 1);
        assertThat(testSdOrderHeader.getOrderHeaderNo()).isEqualTo(UPDATED_ORDER_HEADER_NO);
        assertThat(testSdOrderHeader.getCarNo()).isEqualTo(UPDATED_CAR_NO);
        assertThat(testSdOrderHeader.getDriverName()).isEqualTo(UPDATED_DRIVER_NAME);
        assertThat(testSdOrderHeader.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testSdOrderHeader.getDepartBatch()).isEqualTo(UPDATED_DEPART_BATCH);
        assertThat(testSdOrderHeader.getFromStation()).isEqualTo(UPDATED_FROM_STATION);
        assertThat(testSdOrderHeader.getToStation()).isEqualTo(UPDATED_TO_STATION);
        assertThat(testSdOrderHeader.getUnloadPlace()).isEqualTo(UPDATED_UNLOAD_PLACE);
        assertThat(testSdOrderHeader.getCashPay()).isEqualTo(UPDATED_CASH_PAY);
        assertThat(testSdOrderHeader.getDriverCollection()).isEqualTo(UPDATED_DRIVER_COLLECTION);
        assertThat(testSdOrderHeader.getHandlingCharges()).isEqualTo(UPDATED_HANDLING_CHARGES);
        assertThat(testSdOrderHeader.getReceiveShipment()).isEqualTo(UPDATED_RECEIVE_SHIPMENT);
        assertThat(testSdOrderHeader.getOtherPay()).isEqualTo(UPDATED_OTHER_PAY);
        assertThat(testSdOrderHeader.getDepartureTime()).isEqualTo(UPDATED_DEPARTURE_TIME);
        assertThat(testSdOrderHeader.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testSdOrderHeader.getPractical()).isEqualTo(UPDATED_PRACTICAL);
        assertThat(testSdOrderHeader.getLoadParter()).isEqualTo(UPDATED_LOAD_PARTER);
        assertThat(testSdOrderHeader.getReply()).isEqualTo(UPDATED_REPLY);
        assertThat(testSdOrderHeader.getOilCardNo()).isEqualTo(UPDATED_OIL_CARD_NO);
        assertThat(testSdOrderHeader.getOilCardBlance()).isEqualTo(UPDATED_OIL_CARD_BLANCE);
        assertThat(testSdOrderHeader.getFreightSum()).isEqualTo(UPDATED_FREIGHT_SUM);
        assertThat(testSdOrderHeader.getArriveFreight()).isEqualTo(UPDATED_ARRIVE_FREIGHT);
        assertThat(testSdOrderHeader.getArriveDriver()).isEqualTo(UPDATED_ARRIVE_DRIVER);
        assertThat(testSdOrderHeader.getOrderHeadStat()).isEqualTo(UPDATED_ORDER_HEAD_STAT);

        // Validate the SdOrderHeader in ElasticSearch
        SdOrderHeader sdOrderHeaderEs = sdOrderHeaderSearchRepository.findOne(testSdOrderHeader.getId());
        assertThat(sdOrderHeaderEs).isEqualToComparingFieldByField(testSdOrderHeader);
    }

    @Test
    @Transactional
    public void deleteSdOrderHeader() throws Exception {
        // Initialize the database
        sdOrderHeaderService.save(sdOrderHeader);

        int databaseSizeBeforeDelete = sdOrderHeaderRepository.findAll().size();

        // Get the sdOrderHeader
        restSdOrderHeaderMockMvc.perform(delete("/api/sd-order-headers/{id}", sdOrderHeader.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean sdOrderHeaderExistsInEs = sdOrderHeaderSearchRepository.exists(sdOrderHeader.getId());
        assertThat(sdOrderHeaderExistsInEs).isFalse();

        // Validate the database is empty
        List<SdOrderHeader> sdOrderHeaders = sdOrderHeaderRepository.findAll();
        assertThat(sdOrderHeaders).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSdOrderHeader() throws Exception {
        // Initialize the database
        sdOrderHeaderService.save(sdOrderHeader);

        // Search the sdOrderHeader
        restSdOrderHeaderMockMvc.perform(get("/api/_search/sd-order-headers?query=id:" + sdOrderHeader.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sdOrderHeader.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderHeaderNo").value(hasItem(DEFAULT_ORDER_HEADER_NO.toString())))
            .andExpect(jsonPath("$.[*].carNo").value(hasItem(DEFAULT_CAR_NO.toString())))
            .andExpect(jsonPath("$.[*].driverName").value(hasItem(DEFAULT_DRIVER_NAME.toString())))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].departBatch").value(hasItem(DEFAULT_DEPART_BATCH.toString())))
            .andExpect(jsonPath("$.[*].fromStation").value(hasItem(DEFAULT_FROM_STATION.toString())))
            .andExpect(jsonPath("$.[*].toStation").value(hasItem(DEFAULT_TO_STATION.toString())))
            .andExpect(jsonPath("$.[*].unloadPlace").value(hasItem(DEFAULT_UNLOAD_PLACE.toString())))
            .andExpect(jsonPath("$.[*].cashPay").value(hasItem(DEFAULT_CASH_PAY.intValue())))
            .andExpect(jsonPath("$.[*].driverCollection").value(hasItem(DEFAULT_DRIVER_COLLECTION.intValue())))
            .andExpect(jsonPath("$.[*].handlingCharges").value(hasItem(DEFAULT_HANDLING_CHARGES.intValue())))
            .andExpect(jsonPath("$.[*].receiveShipment").value(hasItem(DEFAULT_RECEIVE_SHIPMENT.intValue())))
            .andExpect(jsonPath("$.[*].otherPay").value(hasItem(DEFAULT_OTHER_PAY.intValue())))
            .andExpect(jsonPath("$.[*].departureTime").value(hasItem(DEFAULT_DEPARTURE_TIME_STR)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].practical").value(hasItem(DEFAULT_PRACTICAL.intValue())))
            .andExpect(jsonPath("$.[*].loadParter").value(hasItem(DEFAULT_LOAD_PARTER.toString())))
            .andExpect(jsonPath("$.[*].reply").value(hasItem(DEFAULT_REPLY.intValue())))
            .andExpect(jsonPath("$.[*].oilCardNo").value(hasItem(DEFAULT_OIL_CARD_NO.toString())))
            .andExpect(jsonPath("$.[*].oilCardBlance").value(hasItem(DEFAULT_OIL_CARD_BLANCE.intValue())))
            .andExpect(jsonPath("$.[*].freightSum").value(hasItem(DEFAULT_FREIGHT_SUM.intValue())))
            .andExpect(jsonPath("$.[*].arriveFreight").value(hasItem(DEFAULT_ARRIVE_FREIGHT.intValue())))
            .andExpect(jsonPath("$.[*].arriveDriver").value(hasItem(DEFAULT_ARRIVE_DRIVER.toString())))
            .andExpect(jsonPath("$.[*].orderHeadStat").value(hasItem(DEFAULT_ORDER_HEAD_STAT.toString())));
    }
}
