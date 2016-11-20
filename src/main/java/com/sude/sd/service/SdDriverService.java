package com.sude.sd.service;

import com.sude.sd.domain.SdDriver;
import com.sude.sd.repository.SdDriverRepository;
import com.sude.sd.repository.search.SdDriverSearchRepository;
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
 * Service Implementation for managing SdDriver.
 */
@Service
@Transactional
public class SdDriverService {

    private final Logger log = LoggerFactory.getLogger(SdDriverService.class);
    
    @Inject
    private SdDriverRepository sdDriverRepository;

    @Inject
    private SdDriverSearchRepository sdDriverSearchRepository;

    /**
     * Save a sdDriver.
     *
     * @param sdDriver the entity to save
     * @return the persisted entity
     */
    public SdDriver save(SdDriver sdDriver) {
        log.debug("Request to save SdDriver : {}", sdDriver);
        SdDriver result = sdDriverRepository.save(sdDriver);
        sdDriverSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the sdDrivers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SdDriver> findAll(Pageable pageable) {
        log.debug("Request to get all SdDrivers");
        Page<SdDriver> result = sdDriverRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one sdDriver by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SdDriver findOne(Long id) {
        log.debug("Request to get SdDriver : {}", id);
        SdDriver sdDriver = sdDriverRepository.findOne(id);
        return sdDriver;
    }

    /**
     *  Delete the  sdDriver by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SdDriver : {}", id);
        sdDriverRepository.delete(id);
        sdDriverSearchRepository.delete(id);
    }

    /**
     * Search for the sdDriver corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SdDriver> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SdDrivers for query {}", query);
        Page<SdDriver> result = sdDriverSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
