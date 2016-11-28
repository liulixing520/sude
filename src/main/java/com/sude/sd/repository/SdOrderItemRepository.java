package com.sude.sd.repository;

import com.sude.sd.domain.SdOrderItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SdOrderItem entity.
 */
@SuppressWarnings("unused")
public interface SdOrderItemRepository extends JpaRepository<SdOrderItem,String> {

	Page<SdOrderItem> findByOrderStat(String orderStat,Pageable pageable);
}
