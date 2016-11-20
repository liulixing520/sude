package com.sude.sd.repository;

import com.sude.sd.domain.EnumerationType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EnumerationType entity.
 */
@SuppressWarnings("unused")
public interface EnumerationTypeRepository extends JpaRepository<EnumerationType,String> {

}
