package com.sude.sd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A 客户信息. 
 */
@Entity
@Table(name = "sd_customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sdcustomer")
public class SdCustomer extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "customer_name")
    private String customerName;  // 客户名称

    @Column(name = "customer_nm")
    private String customerNM;  // 名称简写

    @Column(name = "sex")
    private String sex;  // 性别

    @Column(name = "mobile_phone")
    private Long mobilePhone;  // 手机号

    @Column(name = "id_card")
    private String idCard;  // 身份证

    @Column(name = "address")
    private String address;  // 地址

    @Column(name = "bank")
    private String bank;  // 银行

    @Column(name = "bank_no")
    private Long bankNo;  // 银行账号

    @Column(name = "bank_open_name")
    private String bankOpenName;  // 开户行

    @Column(name = "company")
    private String company;  // 公司名

    @Column(name = "cust_type")
    private String custType;  // 客户类型

    @Column(name = "remark")
    private String remark;  // 备注

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public SdCustomer customerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNM() {
        return customerNM;
    }

    public SdCustomer customerNM(String customerNM) {
        this.customerNM = customerNM;
        return this;
    }

    public void setCustomerNM(String customerNM) {
        this.customerNM = customerNM;
    }

    public String getSex() {
        return sex;
    }

    public SdCustomer sex(String sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getMobilePhone() {
        return mobilePhone;
    }

    public SdCustomer mobilePhone(Long mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public void setMobilePhone(Long mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getIdCard() {
        return idCard;
    }

    public SdCustomer idCard(String idCard) {
        this.idCard = idCard;
        return this;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAddress() {
        return address;
    }

    public SdCustomer address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBank() {
        return bank;
    }

    public SdCustomer bank(String bank) {
        this.bank = bank;
        return this;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Long getBankNo() {
        return bankNo;
    }

    public SdCustomer bankNo(Long bankNo) {
        this.bankNo = bankNo;
        return this;
    }

    public void setBankNo(Long bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankOpenName() {
        return bankOpenName;
    }

    public SdCustomer bankOpenName(String bankOpenName) {
        this.bankOpenName = bankOpenName;
        return this;
    }

    public void setBankOpenName(String bankOpenName) {
        this.bankOpenName = bankOpenName;
    }

    public String getCompany() {
        return company;
    }

    public SdCustomer company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCustType() {
        return custType;
    }

    public SdCustomer custType(String custType) {
        this.custType = custType;
        return this;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getRemark() {
        return remark;
    }

    public SdCustomer remark(String remark) {
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
        SdCustomer sdCustomer = (SdCustomer) o;
        if(sdCustomer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sdCustomer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SdCustomer{" +
            "id=" + id +
            ", customerName='" + customerName + "'" +
            ", customerNM='" + customerNM + "'" +
            ", sex='" + sex + "'" +
            ", mobilePhone='" + mobilePhone + "'" +
            ", idCard='" + idCard + "'" +
            ", address='" + address + "'" +
            ", bank='" + bank + "'" +
            ", bankNo='" + bankNo + "'" +
            ", bankOpenName='" + bankOpenName + "'" +
            ", company='" + company + "'" +
            ", custType='" + custType + "'" +
            ", remark='" + remark + "'" +
            '}';
    }
}
