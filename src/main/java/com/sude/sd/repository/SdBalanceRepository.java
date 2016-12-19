package com.sude.sd.repository;

import com.sude.sd.domain.SdBalance;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SdBalance entity.
 */
@SuppressWarnings("unused")
public interface SdBalanceRepository extends JpaRepository<SdBalance,Long> {

}
