package com.sude.sd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A 司机信息. 
 */
@Entity
@Table(name = "sd_driver")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sddriver")
public class SdDriver extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "driver_name")
    private String driverName;  // 司机名称

    @Column(name = "sex")
    private String sex;  // 性别

    @Column(name = "mobile_phone")
    private Long mobilePhone;  // 手机号

    @Column(name = "remark")
    private String remark;  // 备注

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDriverName() {
        return driverName;
    }

    public SdDriver driverName(String driverName) {
        this.driverName = driverName;
        return this;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getSex() {
        return sex;
    }

    public SdDriver sex(String sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getMobilePhone() {
        return mobilePhone;
    }

    public SdDriver mobilePhone(Long mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public void setMobilePhone(Long mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getRemark() {
        return remark;
    }

    public SdDriver remark(String remark) {
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
        SdDriver sdDriver = (SdDriver) o;
        if(sdDriver.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sdDriver.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SdDriver{" +
            "id=" + id +
            ", driverName='" + driverName + "'" +
            ", sex='" + sex + "'" +
            ", mobilePhone='" + mobilePhone + "'" +
            ", remark='" + remark + "'" +
            '}';
    }
}
