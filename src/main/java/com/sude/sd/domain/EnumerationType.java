package com.sude.sd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A 枚举类型.
 */
@Entity
@Table(name = "enumeration_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "enumerationtype")
public class EnumerationType extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "is_delete")
    private String isDelete;  // 

    @Column(name = "description")
    private String description;  // 

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public EnumerationType isDelete(String isDelete) {
        this.isDelete = isDelete;
        return this;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getDescription() {
        return description;
    }

    public EnumerationType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EnumerationType enumerationType = (EnumerationType) o;
        if(enumerationType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, enumerationType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EnumerationType{" +
            "id=" + id +
            ", isDelete='" + isDelete + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
