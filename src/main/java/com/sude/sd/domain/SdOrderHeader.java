package com.sude.sd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A 发货单. 
 */
@Entity
@Table(name = "sd_order_header")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sdorderheader")
public class SdOrderHeader extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_header_no")
    private String orderHeaderNo;  // 合同编号

    @Column(name = "car_no")
    private String carNo;  // 车牌号

    @Column(name = "driver_name")
    private String driverName;  // 司机
    
    @Column(name = "driver_id")
    private String driverId;  // 司机

    @Column(name = "mobile_phone")
    private Long mobilePhone;  // 手机号

    @Column(name = "depart_batch")
    private String departBatch;  // 发车批次

    @Column(name = "from_station")
    private String fromStation;  // 启运地
    
    @Column(name = "from_station_name")
    private String fromStationName;  // 启运地名称

    @Column(name = "to_station")
    private String toStation;  // 目的地
    
    @Column(name = "to_station_name")
    private String toStationName;  // 目的地名称

    @Column(name = "unload_place")
    private String unloadPlace;  // 卸货地

    @Column(name = "cash_pay", precision=10, scale=2)
    private BigDecimal cashPay;  // 现付运费

    @Column(name = "driver_collection", precision=10, scale=2)
    private BigDecimal driverCollection;  // 司机代收款

    @Column(name = "handling_charges", precision=10, scale=2)
    private BigDecimal handlingCharges;  // 装卸费

    @Column(name = "receive_shipment", precision=10, scale=2)
    private BigDecimal receiveShipment;  // 接货费

    @Column(name = "other_pay", precision=10, scale=2)
    private BigDecimal otherPay;  // 其他费用

    @Column(name = "departure_time")
    private ZonedDateTime departureTime;  // 发车时间

    @Column(name = "weight")
    private Long weight;  // 本车载重

    @Column(name = "practical")
    private Long practical;  // 实际重量

    @Column(name = "load_parter")
    private String loadParter;  // 配载员

    @Column(name = "reply", precision=10, scale=2)
    private BigDecimal reply;  // 回付运费

    @Column(name = "oil_card_no")
    private String oilCardNo;  // 油卡卡号

    @Column(name = "oil_card_blance", precision=10, scale=2)
    private BigDecimal oilCardBlance;  // 油卡金额

    @Column(name = "freight_sum", precision=10, scale=2)
    private BigDecimal freightSum;  // 运费合计

    @Column(name = "arrive_freight", precision=10, scale=2)
    private BigDecimal arriveFreight;  // 到付运费

    @Column(name = "arrive_driver")
    private String arriveDriver;  // 到付驾驶员

    @Column(name = "order_head_stat")
    private String orderHeadStat;  // 合同状态
    
    @Column(name = "order_head_stat_name")
    private String orderHeadStatName;  // 合同状态

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromStationName() {
		return fromStationName;
	}

	public void setFromStationName(String fromStationName) {
		this.fromStationName = fromStationName;
	}

	public String getToStationName() {
		return toStationName;
	}

	public void setToStationName(String toStationName) {
		this.toStationName = toStationName;
	}

	public String getOrderHeaderNo() {
        return orderHeaderNo;
    }

    public SdOrderHeader orderHeaderNo(String orderHeaderNo) {
        this.orderHeaderNo = orderHeaderNo;
        return this;
    }

    public void setOrderHeaderNo(String orderHeaderNo) {
        this.orderHeaderNo = orderHeaderNo;
    }

    public String getCarNo() {
        return carNo;
    }

    public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public SdOrderHeader carNo(String carNo) {
        this.carNo = carNo;
        return this;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getDriverName() {
        return driverName;
    }

    public SdOrderHeader driverName(String driverName) {
        this.driverName = driverName;
        return this;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Long getMobilePhone() {
        return mobilePhone;
    }

    public SdOrderHeader mobilePhone(Long mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public void setMobilePhone(Long mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getDepartBatch() {
        return departBatch;
    }

    public SdOrderHeader departBatch(String departBatch) {
        this.departBatch = departBatch;
        return this;
    }

    public void setDepartBatch(String departBatch) {
        this.departBatch = departBatch;
    }

    public String getFromStation() {
        return fromStation;
    }

    public SdOrderHeader fromStation(String fromStation) {
        this.fromStation = fromStation;
        return this;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public String getToStation() {
        return toStation;
    }

    public SdOrderHeader toStation(String toStation) {
        this.toStation = toStation;
        return this;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }

    public String getUnloadPlace() {
        return unloadPlace;
    }

    public SdOrderHeader unloadPlace(String unloadPlace) {
        this.unloadPlace = unloadPlace;
        return this;
    }

    public void setUnloadPlace(String unloadPlace) {
        this.unloadPlace = unloadPlace;
    }

    public BigDecimal getCashPay() {
        return cashPay;
    }

    public SdOrderHeader cashPay(BigDecimal cashPay) {
        this.cashPay = cashPay;
        return this;
    }

    public void setCashPay(BigDecimal cashPay) {
        this.cashPay = cashPay;
    }

    public BigDecimal getDriverCollection() {
        return driverCollection;
    }

    public SdOrderHeader driverCollection(BigDecimal driverCollection) {
        this.driverCollection = driverCollection;
        return this;
    }

    public void setDriverCollection(BigDecimal driverCollection) {
        this.driverCollection = driverCollection;
    }

    public BigDecimal getHandlingCharges() {
        return handlingCharges;
    }

    public SdOrderHeader handlingCharges(BigDecimal handlingCharges) {
        this.handlingCharges = handlingCharges;
        return this;
    }

    public void setHandlingCharges(BigDecimal handlingCharges) {
        this.handlingCharges = handlingCharges;
    }

    public BigDecimal getReceiveShipment() {
        return receiveShipment;
    }

    public SdOrderHeader receiveShipment(BigDecimal receiveShipment) {
        this.receiveShipment = receiveShipment;
        return this;
    }

    public void setReceiveShipment(BigDecimal receiveShipment) {
        this.receiveShipment = receiveShipment;
    }

    public BigDecimal getOtherPay() {
        return otherPay;
    }

    public SdOrderHeader otherPay(BigDecimal otherPay) {
        this.otherPay = otherPay;
        return this;
    }

    public void setOtherPay(BigDecimal otherPay) {
        this.otherPay = otherPay;
    }

    public ZonedDateTime getDepartureTime() {
        return departureTime;
    }

    public SdOrderHeader departureTime(ZonedDateTime departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    public void setDepartureTime(ZonedDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public Long getWeight() {
        return weight;
    }

    public SdOrderHeader weight(Long weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getPractical() {
        return practical;
    }

    public SdOrderHeader practical(Long practical) {
        this.practical = practical;
        return this;
    }

    public void setPractical(Long practical) {
        this.practical = practical;
    }

    public String getLoadParter() {
        return loadParter;
    }

    public SdOrderHeader loadParter(String loadParter) {
        this.loadParter = loadParter;
        return this;
    }

    public void setLoadParter(String loadParter) {
        this.loadParter = loadParter;
    }

    public BigDecimal getReply() {
        return reply;
    }

    public SdOrderHeader reply(BigDecimal reply) {
        this.reply = reply;
        return this;
    }

    public void setReply(BigDecimal reply) {
        this.reply = reply;
    }

    public String getOilCardNo() {
        return oilCardNo;
    }

    public SdOrderHeader oilCardNo(String oilCardNo) {
        this.oilCardNo = oilCardNo;
        return this;
    }

    public void setOilCardNo(String oilCardNo) {
        this.oilCardNo = oilCardNo;
    }

    public BigDecimal getOilCardBlance() {
        return oilCardBlance;
    }

    public SdOrderHeader oilCardBlance(BigDecimal oilCardBlance) {
        this.oilCardBlance = oilCardBlance;
        return this;
    }

    public void setOilCardBlance(BigDecimal oilCardBlance) {
        this.oilCardBlance = oilCardBlance;
    }

    public BigDecimal getFreightSum() {
        return freightSum;
    }

    public SdOrderHeader freightSum(BigDecimal freightSum) {
        this.freightSum = freightSum;
        return this;
    }

    public void setFreightSum(BigDecimal freightSum) {
        this.freightSum = freightSum;
    }

    public BigDecimal getArriveFreight() {
        return arriveFreight;
    }

    public SdOrderHeader arriveFreight(BigDecimal arriveFreight) {
        this.arriveFreight = arriveFreight;
        return this;
    }

    public void setArriveFreight(BigDecimal arriveFreight) {
        this.arriveFreight = arriveFreight;
    }

    public String getArriveDriver() {
        return arriveDriver;
    }

    public SdOrderHeader arriveDriver(String arriveDriver) {
        this.arriveDriver = arriveDriver;
        return this;
    }

    public void setArriveDriver(String arriveDriver) {
        this.arriveDriver = arriveDriver;
    }

    public String getOrderHeadStat() {
        return orderHeadStat;
    }

    public SdOrderHeader orderHeadStat(String orderHeadStat) {
        this.orderHeadStat = orderHeadStat;
        return this;
    }

    public void setOrderHeadStat(String orderHeadStat) {
        this.orderHeadStat = orderHeadStat;
    }

    public String getOrderHeadStatName() {
		return orderHeadStatName;
	}

	public void setOrderHeadStatName(String orderHeadStatName) {
		this.orderHeadStatName = orderHeadStatName;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SdOrderHeader sdOrderHeader = (SdOrderHeader) o;
        if(sdOrderHeader.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sdOrderHeader.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SdOrderHeader{" +
            "id=" + id +
            ", orderHeaderNo='" + orderHeaderNo + "'" +
            ", carNo='" + carNo + "'" +
            ", driverName='" + driverName + "'" +
            ", mobilePhone='" + mobilePhone + "'" +
            ", departBatch='" + departBatch + "'" +
            ", fromStation='" + fromStation + "'" +
            ", toStation='" + toStation + "'" +
            ", unloadPlace='" + unloadPlace + "'" +
            ", cashPay='" + cashPay + "'" +
            ", driverCollection='" + driverCollection + "'" +
            ", handlingCharges='" + handlingCharges + "'" +
            ", receiveShipment='" + receiveShipment + "'" +
            ", otherPay='" + otherPay + "'" +
            ", departureTime='" + departureTime + "'" +
            ", weight='" + weight + "'" +
            ", practical='" + practical + "'" +
            ", loadParter='" + loadParter + "'" +
            ", reply='" + reply + "'" +
            ", oilCardNo='" + oilCardNo + "'" +
            ", oilCardBlance='" + oilCardBlance + "'" +
            ", freightSum='" + freightSum + "'" +
            ", arriveFreight='" + arriveFreight + "'" +
            ", arriveDriver='" + arriveDriver + "'" +
            ", orderHeadStat='" + orderHeadStat + "'" +
            ", orderHeadStatName='" + orderHeadStatName + "'" +
            '}';
    }
}
