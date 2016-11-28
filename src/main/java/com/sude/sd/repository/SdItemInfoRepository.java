package com.sude.sd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sude.sd.domain.SdItemInfo;

/**
 * Spring Data JPA repository for the SdItemInfo entity.
 */
@SuppressWarnings("unused")
public interface SdItemInfoRepository extends JpaRepository<SdItemInfo,Long> {

	List<SdItemInfo> findByOrderNo(String orderNo);
}
