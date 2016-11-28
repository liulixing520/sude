package com.sude.sd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A 站点. 
 */
@Entity
@Table(name = "sd_station")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sdstation")
public class SdStation extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "station_name")
    private String stationName;  // 站点

    @Column(name = "station_nm")
    private String stationNM;  // 缩写名字

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public SdStation stationName(String stationName) {
        this.stationName = stationName;
        return this;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationNM() {
        return stationNM;
    }

    public SdStation stationNM(String stationNM) {
        this.stationNM = stationNM;
        return this;
    }

    public void setStationNM(String stationNM) {
        this.stationNM = stationNM;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SdStation sdStation = (SdStation) o;
        if(sdStation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sdStation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SdStation{" +
            "id=" + id +
            ", stationName='" + stationName + "'" +
            ", stationNM='" + stationNM + "'" +
            '}';
    }
}
