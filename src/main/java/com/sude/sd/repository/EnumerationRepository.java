package com.sude.sd.repository;

import com.sude.sd.domain.Enumeration;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Enumeration entity.
 */
@SuppressWarnings("unused")
public interface EnumerationRepository extends JpaRepository<Enumeration,String> {

}
