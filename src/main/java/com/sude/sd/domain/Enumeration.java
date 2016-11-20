package com.sude.sd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A 枚举.
 */
@Entity
@Table(name = "enumeration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "enumeration")
public class Enumeration extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "is_delete")
    private String isDelete;  // 是否删除

    @Column(name = "description")
    private String description;  // 描述

    @Column(name = "enum_type_id")
    private String enumTypeId;  // 枚举类型

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public Enumeration isDelete(String isDelete) {
        this.isDelete = isDelete;
        return this;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getDescription() {
        return description;
    }

    public Enumeration description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnumTypeId() {
        return enumTypeId;
    }

    public Enumeration enumTypeId(String enumTypeId) {
        this.enumTypeId = enumTypeId;
        return this;
    }

    public void setEnumTypeId(String enumTypeId) {
        this.enumTypeId = enumTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Enumeration enumeration = (Enumeration) o;
        if(enumeration.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, enumeration.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Enumeration{" +
            "id=" + id +
            ", isDelete='" + isDelete + "'" +
            ", description='" + description + "'" +
            ", enumTypeId='" + enumTypeId + "'" +
            '}';
    }
}
