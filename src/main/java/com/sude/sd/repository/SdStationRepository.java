package com.sude.sd.repository;

import com.sude.sd.domain.SdStation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SdStation entity.
 */
@SuppressWarnings("unused")
public interface SdStationRepository extends JpaRepository<SdStation,Long> {

	List<SdStation> findByStationName(String stationName);
}
