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
 * A 车辆维修记录. 
 */
@Entity
@Table(name = "sd_maintenance_record")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sdmaintenancerecord")
public class SdMaintenanceRecord extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "car_no")
    private String carNo;  // 车牌号

    @Column(name = "maintain_date")
    private LocalDate maintainDate;  // 维修日期

    @Column(name = "sender")
    private String sender;  // 送修人

    @Column(name = "send_reason")
    private String sendReason;  // 送修原因

    @Column(name = "repairer")
    private String repairer;  // 维修单位

    @Column(name = "repai_result")
    private String repaiResult;  // 修理结果

    @Column(name = "repai_costs", precision=10, scale=2)
    private BigDecimal repaiCosts;  // 维修费用

    @Column(name = "remark")
    private String remark;  // 备注

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarNo() {
        return carNo;
    }

    public SdMaintenanceRecord carNo(String carNo) {
        this.carNo = carNo;
        return this;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public LocalDate getMaintainDate() {
        return maintainDate;
    }

    public SdMaintenanceRecord maintainDate(LocalDate maintainDate) {
        this.maintainDate = maintainDate;
        return this;
    }

    public void setMaintainDate(LocalDate maintainDate) {
        this.maintainDate = maintainDate;
    }

    public String getSender() {
        return sender;
    }

    public SdMaintenanceRecord sender(String sender) {
        this.sender = sender;
        return this;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSendReason() {
        return sendReason;
    }

    public SdMaintenanceRecord sendReason(String sendReason) {
        this.sendReason = sendReason;
        return this;
    }

    public void setSendReason(String sendReason) {
        this.sendReason = sendReason;
    }

    public String getRepairer() {
        return repairer;
    }

    public SdMaintenanceRecord repairer(String repairer) {
        this.repairer = repairer;
        return this;
    }

    public void setRepairer(String repairer) {
        this.repairer = repairer;
    }

    public String getRepaiResult() {
        return repaiResult;
    }

    public SdMaintenanceRecord repaiResult(String repaiResult) {
        this.repaiResult = repaiResult;
        return this;
    }

    public void setRepaiResult(String repaiResult) {
        this.repaiResult = repaiResult;
    }

    public BigDecimal getRepaiCosts() {
        return repaiCosts;
    }

    public SdMaintenanceRecord repaiCosts(BigDecimal repaiCosts) {
        this.repaiCosts = repaiCosts;
        return this;
    }

    public void setRepaiCosts(BigDecimal repaiCosts) {
        this.repaiCosts = repaiCosts;
    }

    public String getRemark() {
        return remark;
    }

    public SdMaintenanceRecord remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SdMaintenanceRecord sdMaintenanceRecord = (SdMaintenanceRecord) o;
        if(sdMaintenanceRecord.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sdMaintenanceRecord.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SdMaintenanceRecord{" +
            "id=" + id +
            ", carNo='" + carNo + "'" +
            ", maintainDate='" + maintainDate + "'" +
            ", sender='" + sender + "'" +
            ", sendReason='" + sendReason + "'" +
            ", repairer='" + repairer + "'" +
            ", repaiResult='" + repaiResult + "'" +
            ", repaiCosts='" + repaiCosts + "'" +
            ", remark='" + remark + "'" +
            '}';
    }
}
