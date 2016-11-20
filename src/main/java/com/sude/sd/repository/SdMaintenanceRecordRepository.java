package com.sude.sd.repository;

import com.sude.sd.domain.SdMaintenanceRecord;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SdMaintenanceRecord entity.
 */
@SuppressWarnings("unused")
public interface SdMaintenanceRecordRepository extends JpaRepository<SdMaintenanceRecord,Long> {

}
