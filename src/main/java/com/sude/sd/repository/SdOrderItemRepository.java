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

	//分单配载页面查找
	Page<SdOrderItem> findByOrderStatAndCreatedBy(String currentLogin,String orderStat,Pageable pageable);
	
	//修改运单状态
	@Transactional
	@Modifying
	@Query("update SdOrderItem set orderStat =?1,orderHeaderNo =?3 where id =?2 ")
	Integer updateStat(String orderStat,String ids,String orderHeaderNo);
	
	//根据ids查找运单
	List<SdOrderItem> findByIdIn(String[] ids);
	
	//根据ids查找运单
	List<SdOrderItem> findByOrderHeaderNoAndCreatedBy(String orderHeaderNo,String currentLogin);

	//查找当前登陆人创建的运单
	Page<SdOrderItem> findByCreatedBy(String currentLogin,Pageable pageable);
}
