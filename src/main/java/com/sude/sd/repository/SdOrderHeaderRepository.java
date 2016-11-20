package com.sude.sd.repository;

import com.sude.sd.domain.SdOrderHeader;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SdOrderHeader entity.
 */
@SuppressWarnings("unused")
public interface SdOrderHeaderRepository extends JpaRepository<SdOrderHeader,Long> {

}
