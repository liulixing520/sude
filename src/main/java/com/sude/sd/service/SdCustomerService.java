package com.sude.sd.service;

import com.sude.sd.domain.SdCustomer;
import com.sude.sd.repository.SdCustomerRepository;
import com.sude.sd.repository.search.SdCustomerSearchRepository;
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
 * Service Implementation for managing SdCustomer.
 */
@Service
@Transactional
public class SdCustomerService {

    private final Logger log = LoggerFactory.getLogger(SdCustomerService.class);
    
    @Inject
    private SdCustomerRepository sdCustomerRepository;

    @Inject
    private SdCustomerSearchRepository sdCustomerSearchRepository;

    /**
     * Save a sdCustomer.
     *
     * @param sdCustomer the entity to save
     * @return the persisted entity
     */
    public SdCustomer save(SdCustomer sdCustomer) {
        log.debug("Request to save SdCustomer : {}", sdCustomer);
        SdCustomer result = sdCustomerRepository.save(sdCustomer);
        sdCustomerSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the sdCustomers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SdCustomer> findAll(Pageable pageable) {
        log.debug("Request to get all SdCustomers");
        Page<SdCustomer> result = sdCustomerRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one sdCustomer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SdCustomer findOne(Long id) {
        log.debug("Request to get SdCustomer : {}", id);
        SdCustomer sdCustomer = sdCustomerRepository.findOne(id);
        return sdCustomer;
    }

    /**
     *  Delete the  sdCustomer by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SdCustomer : {}", id);
        sdCustomerRepository.delete(id);
        sdCustomerSearchRepository.delete(id);
    }

    /**
     * Search for the sdCustomer corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SdCustomer> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SdCustomers for query {}", query);
        Page<SdCustomer> result = sdCustomerSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
