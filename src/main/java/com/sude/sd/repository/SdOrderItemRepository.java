package com.sude.sd.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.sude.sd.domain.SdOrderItem;

/**
 * Spring Data JPA repository for the SdOrderItem entity.
 */
@SuppressWarnings("unused")
public interface SdOrderItemRepository extends JpaRepository<SdOrderItem,String> {

	Page<SdOrderItem> findByOrderStat(String orderStat,Pageable pageable);
	
	@Transactional
	@Modifying
	@Query("update SdOrderItem set orderStat =?1,orderHeaderNo =?3 where id =?2 ")
	Integer updateStat(String orderStat,String ids,String orderHeaderNo);
	
	List<SdOrderItem> findByIdIn(String[] ids);
	
	List<SdOrderItem> findByOrderHeaderNoAndCreatedBy(String orderHeaderNo,String currentLogin);

	Page<SdOrderItem>  findByCreatedBy(String currentLogin,Pageable pageable);
}
