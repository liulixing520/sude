package com.sude.sd.service;

import com.sude.sd.domain.Enumeration;
import com.sude.sd.repository.EnumerationRepository;
import com.sude.sd.repository.search.EnumerationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Enumeration.
 */
@Service
@Transactional
public class EnumerationService {

    private final Logger log = LoggerFactory.getLogger(EnumerationService.class);
    
    @Inject
    private EnumerationRepository enumerationRepository;

    @Inject
    private EnumerationSearchRepository enumerationSearchRepository;

    /**
     * Save a enumeration.
     *
     * @param enumeration the entity to save
     * @return the persisted entity
     */
    public Enumeration save(Enumeration enumeration) {
        log.debug("Request to save Enumeration : {}", enumeration);
        Enumeration result = enumerationRepository.save(enumeration);
        enumerationSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the enumerations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Enumeration> findAll(Pageable pageable) {
        log.debug("Request to get all Enumerations");
        Page<Enumeration> result = enumerationRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one enumeration by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Enumeration findOne(String id) {
        log.debug("Request to get Enumeration : {}", id);
        Enumeration enumeration = enumerationRepository.findOne(id);
        return enumeration;
    }

    /**
     *  Delete the  enumeration by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Enumeration : {}", id);
        enumerationRepository.delete(id);
        enumerationSearchRepository.delete(id);
    }

    /**
     * Search for the enumeration corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Enumeration> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Enumerations for query {}", query);
        Page<Enumeration> result = enumerationSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
