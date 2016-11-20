package com.sude.sd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A 车辆信息. 
 */
@Entity
@Table(name = "sd_car_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sdcarinfo")
public class SdCarInfo extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "car_no")
    private String carNo;  // 车牌号

    @Column(name = "car_type")
    private String carType;  // 车型号

    @Column(name = "engine_number")
    private String engineNumber;  // 发动机号码

    @Column(name = "buy_date")
    private LocalDate buyDate;  // 购买日期

    @Column(name = "check_load")
    private String checkLoad;  // 核定载重

    @Column(name = "check_volume")
    private String checkVolume;  // 核定体积

    @Column(name = "car_length")
    private Long carLength;  // 车长

    @Column(name = "car_width")
    private Long carWidth;  // 车宽

    @Column(name = "car_height")
    private Long carHeight;  // 车高

    @Column(name = "vehicle_no")
    private String vehicleNo;  // 车架号

    @Column(name = "policy_no")
    private String policyNo;  // 保险单号

    @Column(name = "carrier")
    private String carrier;  // 承保单位

    @Column(name = "run_number")
    private String runNumber;  // 运营证号

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarNo() {
        return carNo;
    }

    public SdCarInfo carNo(String carNo) {
        this.carNo = carNo;
        return this;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getCarType() {
        return carType;
    }

    public SdCarInfo carType(String carType) {
        this.carType = carType;
        return this;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public SdCarInfo engineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
        return this;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public LocalDate getBuyDate() {
        return buyDate;
    }

    public SdCarInfo buyDate(LocalDate buyDate) {
        this.buyDate = buyDate;
        return this;
    }

    public void setBuyDate(LocalDate buyDate) {
        this.buyDate = buyDate;
    }

    public String getCheckLoad() {
        return checkLoad;
    }

    public SdCarInfo checkLoad(String checkLoad) {
        this.checkLoad = checkLoad;
        return this;
    }

    public void setCheckLoad(String checkLoad) {
        this.checkLoad = checkLoad;
    }

    public String getCheckVolume() {
        return checkVolume;
    }

    public SdCarInfo checkVolume(String checkVolume) {
        this.checkVolume = checkVolume;
        return this;
    }

    public void setCheckVolume(String checkVolume) {
        this.checkVolume = checkVolume;
    }

    public Long getCarLength() {
        return carLength;
    }

    public SdCarInfo carLength(Long carLength) {
        this.carLength = carLength;
        return this;
    }

    public void setCarLength(Long carLength) {
        this.carLength = carLength;
    }

    public Long getCarWidth() {
        return carWidth;
    }

    public SdCarInfo carWidth(Long carWidth) {
        this.carWidth = carWidth;
        return this;
    }

    public void setCarWidth(Long carWidth) {
        this.carWidth = carWidth;
    }

    public Long getCarHeight() {
        return carHeight;
    }

    public SdCarInfo carHeight(Long carHeight) {
        this.carHeight = carHeight;
        return this;
    }

    public void setCarHeight(Long carHeight) {
        this.carHeight = carHeight;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public SdCarInfo vehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
        return this;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public SdCarInfo policyNo(String policyNo) {
        this.policyNo = policyNo;
        return this;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getCarrier() {
        return carrier;
    }

    public SdCarInfo carrier(String carrier) {
        this.carrier = carrier;
        return this;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getRunNumber() {
        return runNumber;
    }

    public SdCarInfo runNumber(String runNumber) {
        this.runNumber = runNumber;
        return this;
    }

    public void setRunNumber(String runNumber) {
        this.runNumber = runNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SdCarInfo sdCarInfo = (SdCarInfo) o;
        if(sdCarInfo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sdCarInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SdCarInfo{" +
            "id=" + id +
            ", carNo='" + carNo + "'" +
            ", carType='" + carType + "'" +
            ", engineNumber='" + engineNumber + "'" +
            ", buyDate='" + buyDate + "'" +
            ", checkLoad='" + checkLoad + "'" +
            ", checkVolume='" + checkVolume + "'" +
            ", carLength='" + carLength + "'" +
            ", carWidth='" + carWidth + "'" +
            ", carHeight='" + carHeight + "'" +
            ", vehicleNo='" + vehicleNo + "'" +
            ", policyNo='" + policyNo + "'" +
            ", carrier='" + carrier + "'" +
            ", runNumber='" + runNumber + "'" +
            '}';
    }
}
