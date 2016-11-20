package com.sude.sd.repository;

import com.sude.sd.domain.SdCarInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SdCarInfo entity.
 */
@SuppressWarnings("unused")
public interface SdCarInfoRepository extends JpaRepository<SdCarInfo,Long> {

}
