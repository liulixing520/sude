package com.sude.sd.repository;

import com.sude.sd.domain.SdOrderItem;

import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Spring Data JPA repository for the SdOrderItem entity.
 */
@SuppressWarnings("unused")
public interface SdOrderItemRepository extends JpaRepository<SdOrderItem,String> {

	Page<SdOrderItem> findByOrderStat(String orderStat,Pageable pageable);
	
	@Transactional
	@Modifying
	@Query("update SdOrderItem set orderStat =?1 where id in (?2)")
	void updateStat(String orderStat,String ids);
}
