package com.sude.sd.repository;

import com.sude.sd.domain.SdDriver;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SdDriver entity.
 */
@SuppressWarnings("unused")
public interface SdDriverRepository extends JpaRepository<SdDriver,Long> {

}
