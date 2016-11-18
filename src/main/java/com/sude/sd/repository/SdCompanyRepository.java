package com.sude.sd.repository;

import com.sude.sd.domain.SdCompany;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SdCompany entity.
 */
@SuppressWarnings("unused")
public interface SdCompanyRepository extends JpaRepository<SdCompany,Long> {

}
