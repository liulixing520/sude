package com.sude.sd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A 序列单号. 
 */
@Entity
@Table(name = "sequence_value_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sequencevalueitem")
public class SequenceValueItem extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "seq_id")
    private Long seqId;  // 序列号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSeqId() {
        return seqId;
    }

    public SequenceValueItem seqId(Long seqId) {
        this.seqId = seqId;
        return this;
    }

    public void setSeqId(Long seqId) {
        this.seqId = seqId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SequenceValueItem sequenceValueItem = (SequenceValueItem) o;
        if(sequenceValueItem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sequenceValueItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SequenceValueItem{" +
            "id=" + id +
            ", seqId='" + seqId + "'" +
            '}';
    }
}
