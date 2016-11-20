package com.sude.sd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A 运单明细. 
 */
@Entity
@Table(name = "sd_item_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sditeminfo")
public class SdItemInfo extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_no")
    private String orderNo;  // 托运单号

    @Column(name = "trad_name")
    private String tradName;  // 品名

    @Column(name = "item_num")
    private Long itemNum;  // 件数

    @Column(name = "weight")
    private Long weight;  // 重量

    @Column(name = "volume")
    private Long volume;  // 体积

    @Column(name = "item_unit")
    private String itemUnit;  // 包装

    @Column(name = "freight", precision=10, scale=2)
    private BigDecimal freight;  // 运费

    @Column(name = "kick_back", precision=10, scale=2)
    private BigDecimal kickBack;  // 回扣

    @Column(name = "cod", precision=10, scale=2)
    private BigDecimal cod;  // 代收货款

    @Column(name = "delivery_expense", precision=10, scale=2)
    private BigDecimal deliveryExpense;  // 送货费

    @Column(name = "claiming_value", precision=10, scale=2)
    private BigDecimal claimingValue;  // 申明价值

    @Column(name = "premium", precision=10, scale=2)
    private BigDecimal premium;  // 保险费

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public SdItemInfo orderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTradName() {
        return tradName;
    }

    public SdItemInfo tradName(String tradName) {
        this.tradName = tradName;
        return this;
    }

    public void setTradName(String tradName) {
        this.tradName = tradName;
    }

    public Long getItemNum() {
        return itemNum;
    }

    public SdItemInfo itemNum(Long itemNum) {
        this.itemNum = itemNum;
        return this;
    }

    public void setItemNum(Long itemNum) {
        this.itemNum = itemNum;
    }

    public Long getWeight() {
        return weight;
    }

    public SdItemInfo weight(Long weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getVolume() {
        return volume;
    }

    public SdItemInfo volume(Long volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public SdItemInfo itemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
        return this;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public SdItemInfo freight(BigDecimal freight) {
        this.freight = freight;
        return this;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public BigDecimal getKickBack() {
        return kickBack;
    }

    public SdItemInfo kickBack(BigDecimal kickBack) {
        this.kickBack = kickBack;
        return this;
    }

    public void setKickBack(BigDecimal kickBack) {
        this.kickBack = kickBack;
    }

    public BigDecimal getCod() {
        return cod;
    }

    public SdItemInfo cod(BigDecimal cod) {
        this.cod = cod;
        return this;
    }

    public void setCod(BigDecimal cod) {
        this.cod = cod;
    }

    public BigDecimal getDeliveryExpense() {
        return deliveryExpense;
    }

    public SdItemInfo deliveryExpense(BigDecimal deliveryExpense) {
        this.deliveryExpense = deliveryExpense;
        return this;
    }

    public void setDeliveryExpense(BigDecimal deliveryExpense) {
        this.deliveryExpense = deliveryExpense;
    }

    public BigDecimal getClaimingValue() {
        return claimingValue;
    }

    public SdItemInfo claimingValue(BigDecimal claimingValue) {
        this.claimingValue = claimingValue;
        return this;
    }

    public void setClaimingValue(BigDecimal claimingValue) {
        this.claimingValue = claimingValue;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public SdItemInfo premium(BigDecimal premium) {
        this.premium = premium;
        return this;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SdItemInfo sdItemInfo = (SdItemInfo) o;
        if(sdItemInfo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sdItemInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SdItemInfo{" +
            "id=" + id +
            ", orderNo='" + orderNo + "'" +
            ", tradName='" + tradName + "'" +
            ", itemNum='" + itemNum + "'" +
            ", weight='" + weight + "'" +
            ", volume='" + volume + "'" +
            ", itemUnit='" + itemUnit + "'" +
            ", freight='" + freight + "'" +
            ", kickBack='" + kickBack + "'" +
            ", cod='" + cod + "'" +
            ", deliveryExpense='" + deliveryExpense + "'" +
            ", claimingValue='" + claimingValue + "'" +
            ", premium='" + premium + "'" +
            '}';
    }
}
