package com.sude.sd.repository;

import com.sude.sd.domain.SequenceValueItem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SequenceValueItem entity.
 */
@SuppressWarnings("unused")
public interface SequenceValueItemRepository extends JpaRepository<SequenceValueItem,String> {

}
