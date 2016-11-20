package com.sude.sd.repository;

import com.sude.sd.domain.SdItemInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SdItemInfo entity.
 */
@SuppressWarnings("unused")
public interface SdItemInfoRepository extends JpaRepository<SdItemInfo,Long> {

}
