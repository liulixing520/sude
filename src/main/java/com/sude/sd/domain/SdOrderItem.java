package com.sude.sd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A 托运单. 
 */
@Entity
@Table(name = "sd_order_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sdorderitem")
public class SdOrderItem extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;// 托运单号

    @Column(name = "order_header_no")
    private String orderHeaderNo;  // 货运单号

    @Column(name = "consign_date")
    private LocalDate consignDate;  // 受理日期

    @Column(name = "from_station")
    private String fromStation;  // 启运站
    
    @Column(name = "from_station_name")
    private String fromStationName;  // 启运站名称

    @Column(name = "to_station")
    private String toStation;  // 到达站
    
    @Column(name = "to_station_name")
    private String toStationName;  // 到达站名称

    @Column(name = "middle_station")
    private String middleStation;  // 经由
    
    @Column(name = "goods_no")
    private String goodsNo;  // 货号

    @Column(name = "consigner_id")
    private String consignerId;  // 发货人id

    @Column(name = "consigner_name")
    private String consignerName;  // 发货人

    @Column(name = "consigner_address")
    private String consignerAddress;  // 发货地址

    @Column(name = "consigner_phone")
    private String consignerPhone;  // 联系电话

    @Column(name = "consigner_mb_phone")
    private Long consignerMbPhone;  // 手机

    @Column(name = "consignee_id")
    private String consigneeId;  // 收货人id

    @Column(name = "consignee_name")
    private String consigneeName;  // 收货人

    @Column(name = "consignee_phone")
    private String consigneePhone;  // 联系电话

    @Column(name = "consignee_mb_phone")
    private Long consigneeMbPhone;  // 手机

    @Column(name = "consignee_address")
    private String consigneeAddress;  // 收货地址

    @Column(name = "bank_no")
    private Long bankNo;  // 银行卡号

    @Column(name = "bank_name")
    private String bankName;  // 银行

    @Column(name = "open_name")
    private String openName;  // 开户名

    @Column(name = "id_card")
    private String idCard;  // 身份证号
    
    @Column(name = "recive_car")
    private String reciveCar;  // 接货车号
    
    @Column(name = "recive_driver")
    private String rreciveDriver;  // 接货司机
    
    @Column(name = "total_weight")
    private BigDecimal totalWeight;  // 总重量
    
    @Column(name = "total_freight")
    private BigDecimal totalFreight;  // 总运费

    @Column(name = "pay_type")
    private String payType;  // 付款方式

    @Column(name = "cash_pay", precision=10, scale=2)
    private BigDecimal cashPay;  // 现付

    @Column(name = "fetch_pay", precision=10, scale=2)
    private BigDecimal fetchPay;  // 提付

    @Column(name = "receipt_pay", precision=10, scale=2)
    private BigDecimal receiptPay;  // 回单付

    @Column(name = "month_pay", precision=10, scale=2)
    private BigDecimal monthPay;  // 月结

    @Column(name = "charge_pay", precision=10, scale=2)
    private BigDecimal chargePay;  // 货款扣

    @Column(name = "transport_type")
    private String transportType;  // 运输方式

    @Column(name = "back_require")
    private String backRequire;  // 回单要求

    @Column(name = "hand_over_type")
    private String handOverType;  // 交接方式

    @Column(name = "other_pay")
    private String otherPay;  // 其他支出

    @Column(name = "pay_explain")
    private String payExplain;  // 费用说明

    @Column(name = "remark")
    private String remark;  // 备注
    
    @Column(name = "others")
    private String others;  // 其他

    @Column(name = "kick_back")
    private String kickBack;  // 回扣已返

    @Column(name = "cash_owe")
    private String cashOwe;  // 现付尚欠

    @Column(name = "require_item")
    private String requireItem;  // 要求控货

    @Column(name = "tagged")
    private String tagged;  // 打标签

    @Column(name = "envelopes")
    private String envelopes;  // 打信封

    @Column(name = "sales_man")
    private String salesMan;  // 业务员

    @Column(name = "operator")
    private String operator;  // 制单人

    @Column(name = "order_stat")
    private String orderStat;  // 运单状态

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	public String getReciveCar() {
		return reciveCar;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public void setReciveCar(String reciveCar) {
		this.reciveCar = reciveCar;
	}

	public String getRreciveDriver() {
		return rreciveDriver;
	}

	public void setRreciveDriver(String rreciveDriver) {
		this.rreciveDriver = rreciveDriver;
	}


    public String getOrderHeaderNo() {
        return orderHeaderNo;
    }

    public SdOrderItem id(String id) {
    	this.id = id;
    	return this;
    }
    public SdOrderItem orderHeaderNo(String orderHeaderNo) {
        this.orderHeaderNo = orderHeaderNo;
        return this;
    }

    public void setOrderHeaderNo(String orderHeaderNo) {
        this.orderHeaderNo = orderHeaderNo;
    }

    public LocalDate getConsignDate() {
        return consignDate;
    }

    public SdOrderItem consignDate(LocalDate consignDate) {
        this.consignDate = consignDate;
        return this;
    }

    public void setConsignDate(LocalDate consignDate) {
        this.consignDate = consignDate;
    }

    public String getFromStation() {
        return fromStation;
    }

    public SdOrderItem fromStation(String fromStation) {
        this.fromStation = fromStation;
        return this;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public String getToStation() {
        return toStation;
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

	public SdOrderItem toStation(String toStation) {
        this.toStation = toStation;
        return this;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }

    public String getMiddleStation() {
        return middleStation;
    }

    public SdOrderItem middleStation(String middleStation) {
        this.middleStation = middleStation;
        return this;
    }

    public void setMiddleStation(String middleStation) {
        this.middleStation = middleStation;
    }

    public String getConsignerId() {
        return consignerId;
    }

    public SdOrderItem consignerId(String consignerId) {
        this.consignerId = consignerId;
        return this;
    }

    public void setConsignerId(String consignerId) {
        this.consignerId = consignerId;
    }

    public String getConsignerName() {
        return consignerName;
    }

    public SdOrderItem consignerName(String consignerName) {
        this.consignerName = consignerName;
        return this;
    }

    public void setConsignerName(String consignerName) {
        this.consignerName = consignerName;
    }

    public String getConsignerAddress() {
        return consignerAddress;
    }

    public SdOrderItem consignerAddress(String consignerAddress) {
        this.consignerAddress = consignerAddress;
        return this;
    }

    public void setConsignerAddress(String consignerAddress) {
        this.consignerAddress = consignerAddress;
    }

    public String getConsignerPhone() {
        return consignerPhone;
    }

    public SdOrderItem consignerPhone(String consignerPhone) {
        this.consignerPhone = consignerPhone;
        return this;
    }

    public void setConsignerPhone(String consignerPhone) {
        this.consignerPhone = consignerPhone;
    }

    public Long getConsignerMbPhone() {
        return consignerMbPhone;
    }

    public SdOrderItem consignerMbPhone(Long consignerMbPhone) {
        this.consignerMbPhone = consignerMbPhone;
        return this;
    }

    public void setConsignerMbPhone(Long consignerMbPhone) {
        this.consignerMbPhone = consignerMbPhone;
    }

    public String getConsigneeId() {
        return consigneeId;
    }

    public SdOrderItem consigneeId(String consigneeId) {
        this.consigneeId = consigneeId;
        return this;
    }

    public void setConsigneeId(String consigneeId) {
        this.consigneeId = consigneeId;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public SdOrderItem consigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
        return this;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public SdOrderItem consigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
        return this;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public Long getConsigneeMbPhone() {
        return consigneeMbPhone;
    }

    public SdOrderItem consigneeMbPhone(Long consigneeMbPhone) {
        this.consigneeMbPhone = consigneeMbPhone;
        return this;
    }

    public void setConsigneeMbPhone(Long consigneeMbPhone) {
        this.consigneeMbPhone = consigneeMbPhone;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public SdOrderItem consigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
        return this;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public Long getBankNo() {
        return bankNo;
    }

    public SdOrderItem bankNo(Long bankNo) {
        this.bankNo = bankNo;
        return this;
    }

    public void setBankNo(Long bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }

    public SdOrderItem bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getOpenName() {
        return openName;
    }

    public SdOrderItem openName(String openName) {
        this.openName = openName;
        return this;
    }

    public void setOpenName(String openName) {
        this.openName = openName;
    }

    public String getIdCard() {
        return idCard;
    }

    public SdOrderItem idCard(String idCard) {
        this.idCard = idCard;
        return this;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPayType() {
        return payType;
    }

    public SdOrderItem payType(String payType) {
        this.payType = payType;
        return this;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public BigDecimal getCashPay() {
        return cashPay;
    }

    public SdOrderItem cashPay(BigDecimal cashPay) {
        this.cashPay = cashPay;
        return this;
    }

    public void setCashPay(BigDecimal cashPay) {
        this.cashPay = cashPay;
    }

    public BigDecimal getFetchPay() {
        return fetchPay;
    }

    public SdOrderItem fetchPay(BigDecimal fetchPay) {
        this.fetchPay = fetchPay;
        return this;
    }

    public void setFetchPay(BigDecimal fetchPay) {
        this.fetchPay = fetchPay;
    }

    public BigDecimal getReceiptPay() {
        return receiptPay;
    }

    public SdOrderItem receiptPay(BigDecimal receiptPay) {
        this.receiptPay = receiptPay;
        return this;
    }

    public void setReceiptPay(BigDecimal receiptPay) {
        this.receiptPay = receiptPay;
    }

    public BigDecimal getMonthPay() {
        return monthPay;
    }

    public SdOrderItem monthPay(BigDecimal monthPay) {
        this.monthPay = monthPay;
        return this;
    }

    public void setMonthPay(BigDecimal monthPay) {
        this.monthPay = monthPay;
    }

    public BigDecimal getChargePay() {
        return chargePay;
    }

    public SdOrderItem chargePay(BigDecimal chargePay) {
        this.chargePay = chargePay;
        return this;
    }

    public void setChargePay(BigDecimal chargePay) {
        this.chargePay = chargePay;
    }

    public String getTransportType() {
        return transportType;
    }

    public SdOrderItem transportType(String transportType) {
        this.transportType = transportType;
        return this;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getBackRequire() {
        return backRequire;
    }

    public SdOrderItem backRequire(String backRequire) {
        this.backRequire = backRequire;
        return this;
    }

    public void setBackRequire(String backRequire) {
        this.backRequire = backRequire;
    }

    public String getHandOverType() {
        return handOverType;
    }

    public SdOrderItem handOverType(String handOverType) {
        this.handOverType = handOverType;
        return this;
    }

    public void setHandOverType(String handOverType) {
        this.handOverType = handOverType;
    }

    public String getOtherPay() {
        return otherPay;
    }

    public SdOrderItem otherPay(String otherPay) {
        this.otherPay = otherPay;
        return this;
    }

    public void setOtherPay(String otherPay) {
        this.otherPay = otherPay;
    }

    public String getPayExplain() {
        return payExplain;
    }

    public SdOrderItem payExplain(String payExplain) {
        this.payExplain = payExplain;
        return this;
    }

    public void setPayExplain(String payExplain) {
        this.payExplain = payExplain;
    }

    public String getRemark() {
        return remark;
    }

    public SdOrderItem remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getKickBack() {
        return kickBack;
    }

    public SdOrderItem kickBack(String kickBack) {
        this.kickBack = kickBack;
        return this;
    }

    public void setKickBack(String kickBack) {
        this.kickBack = kickBack;
    }

    public String getCashOwe() {
        return cashOwe;
    }

    public SdOrderItem cashOwe(String cashOwe) {
        this.cashOwe = cashOwe;
        return this;
    }

    public void setCashOwe(String cashOwe) {
        this.cashOwe = cashOwe;
    }

    public String getRequireItem() {
        return requireItem;
    }

    public SdOrderItem requireItem(String requireItem) {
        this.requireItem = requireItem;
        return this;
    }

    public void setRequireItem(String requireItem) {
        this.requireItem = requireItem;
    }

    public String getTagged() {
        return tagged;
    }

    public SdOrderItem tagged(String tagged) {
        this.tagged = tagged;
        return this;
    }

    public void setTagged(String tagged) {
        this.tagged = tagged;
    }

    public String getEnvelopes() {
        return envelopes;
    }

    public SdOrderItem envelopes(String envelopes) {
        this.envelopes = envelopes;
        return this;
    }

    public void setEnvelopes(String envelopes) {
        this.envelopes = envelopes;
    }

    public String getSalesMan() {
        return salesMan;
    }

    public SdOrderItem salesMan(String salesMan) {
        this.salesMan = salesMan;
        return this;
    }

    public void setSalesMan(String salesMan) {
        this.salesMan = salesMan;
    }

    public String getOperator() {
        return operator;
    }

    public SdOrderItem operator(String operator) {
        this.operator = operator;
        return this;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOrderStat() {
        return orderStat;
    }

    public SdOrderItem orderStat(String orderStat) {
        this.orderStat = orderStat;
        return this;
    }

    public BigDecimal getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}

	public BigDecimal getTotalFreight() {
		return totalFreight;
	}

	public void setTotalFreight(BigDecimal totalFreight) {
		this.totalFreight = totalFreight;
	}

	public void setOrderStat(String orderStat) {
        this.orderStat = orderStat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SdOrderItem sdOrderItem = (SdOrderItem) o;
        if(sdOrderItem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sdOrderItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SdOrderItem{" +
            "id=" + id +
            ", orderHeaderNo='" + orderHeaderNo + "'" +
            ", consignDate='" + consignDate + "'" +
            ", fromStation='" + fromStation + "'" +
            ", toStation='" + toStation + "'" +
            ", middleStation='" + middleStation + "'" +
            ", consignerId='" + consignerId + "'" +
            ", consignerName='" + consignerName + "'" +
            ", consignerAddress='" + consignerAddress + "'" +
            ", consignerPhone='" + consignerPhone + "'" +
            ", consignerMbPhone='" + consignerMbPhone + "'" +
            ", consigneeId='" + consigneeId + "'" +
            ", consigneeName='" + consigneeName + "'" +
            ", consigneePhone='" + consigneePhone + "'" +
            ", consigneeMbPhone='" + consigneeMbPhone + "'" +
            ", consigneeAddress='" + consigneeAddress + "'" +
            ", bankNo='" + bankNo + "'" +
            ", bankName='" + bankName + "'" +
            ", openName='" + openName + "'" +
            ", idCard='" + idCard + "'" +
            ", totalWeight='" + totalWeight + "'" +
            ", totalFreight='" + totalFreight + "'" +
            ", payType='" + payType + "'" +
            ", cashPay='" + cashPay + "'" +
            ", fetchPay='" + fetchPay + "'" +
            ", receiptPay='" + receiptPay + "'" +
            ", monthPay='" + monthPay + "'" +
            ", chargePay='" + chargePay + "'" +
            ", transportType='" + transportType + "'" +
            ", backRequire='" + backRequire + "'" +
            ", handOverType='" + handOverType + "'" +
            ", otherPay='" + otherPay + "'" +
            ", payExplain='" + payExplain + "'" +
            ", remark='" + remark + "'" +
            ", kickBack='" + kickBack + "'" +
            ", cashOwe='" + cashOwe + "'" +
            ", requireItem='" + requireItem + "'" +
            ", tagged='" + tagged + "'" +
            ", envelopes='" + envelopes + "'" +
            ", salesMan='" + salesMan + "'" +
            ", operator='" + operator + "'" +
            ", orderStat='" + orderStat + "'" +
            '}';
    }
}
