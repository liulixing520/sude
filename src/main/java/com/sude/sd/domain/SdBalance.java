package com.sude.sd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A 收支明细. 
 */
@Entity
@Table(name = "sd_balance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sdbalance")
public class SdBalance extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_no")
    private String orderNo;  // 托运单号

    @Column(name = "summary")
    private String summary;  // 摘要

    @Column(name = "money", precision=10, scale=2)
    private BigDecimal money;  // 实收金额
    
    @Column(name = "should_money", precision=10, scale=2)
    private BigDecimal shouldMoney;  // 应收金额

    @Column(name = "remark")
    private String remark;  // 备注

    @ManyToOne
    private Enumeration inOutType; //收支类型

    @ManyToOne
    private Enumeration payMent; //结算方式

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public SdBalance orderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSummary() {
        return summary;
    }

    public SdBalance summary(String summary) {
        this.summary = summary;
        return this;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public SdBalance money(BigDecimal money) {
        this.money = money;
        return this;
    }
    
    public SdBalance shouldMoney(BigDecimal shouldMoney) {
    	this.shouldMoney = shouldMoney;
    	return this;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    
    public BigDecimal getShouldMoney() {
		return shouldMoney;
	}

	public void setShouldMoney(BigDecimal shouldMoney) {
		this.shouldMoney = shouldMoney;
	}

	public String getRemark() {
        return remark;
    }

    public SdBalance remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Enumeration getInOutType() {
        return inOutType;
    }

    public SdBalance inOutType(Enumeration enumeration) {
        this.inOutType = enumeration;
        return this;
    }

    public void setInOutType(Enumeration enumeration) {
        this.inOutType = enumeration;
    }

    public Enumeration getPayMent() {
        return payMent;
    }

    public SdBalance payMent(Enumeration enumeration) {
        this.payMent = enumeration;
        return this;
    }

    public void setPayMent(Enumeration enumeration) {
        this.payMent = enumeration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SdBalance sdBalance = (SdBalance) o;
        if(sdBalance.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sdBalance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SdBalance{" +
            "id=" + id +
            ", orderNo='" + orderNo + "'" +
            ", summary='" + summary + "'" +
            ", money='" + money + "'" +
            ", shouldMoney='" + shouldMoney + "'" +
            ", remark='" + remark + "'" +
            '}';
    }
}
