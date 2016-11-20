package com.sude.sd.repository;

import com.sude.sd.domain.SdCustomer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SdCustomer entity.
 */
@SuppressWarnings("unused")
public interface SdCustomerRepository extends JpaRepository<SdCustomer,Long> {

}
