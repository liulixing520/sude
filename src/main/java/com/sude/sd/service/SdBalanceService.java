package com.sude.sd.service;

import com.sude.sd.domain.SdBalance;
import com.sude.sd.repository.SdBalanceRepository;
import com.sude.sd.repository.search.SdBalanceSearchRepository;
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
 * Service Implementation for managing SdBalance.
 */
@Service
@Transactional
public class SdBalanceService {

    private final Logger log = LoggerFactory.getLogger(SdBalanceService.class);
    
    @Inject
    private SdBalanceRepository sdBalanceRepository;

    @Inject
    private SdBalanceSearchRepository sdBalanceSearchRepository;

    /**
     * Save a sdBalance.
     *
     * @param sdBalance the entity to save
     * @return the persisted entity
     */
    public SdBalance save(SdBalance sdBalance) {
        log.debug("Request to save SdBalance : {}", sdBalance);
        SdBalance result = sdBalanceRepository.save(sdBalance);
        sdBalanceSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the sdBalances.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SdBalance> findAll(Pageable pageable) {
        log.debug("Request to get all SdBalances");
        Page<SdBalance> result = sdBalanceRepository.findAll(pageable);
        return result;
    }
    
    /**
     *  Get all the sdBalances.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<SdBalance> findByOrderNo(String orderNo) {
    	log.debug("Request to get all SdBalances");
    	List<SdBalance> result = sdBalanceRepository.findByOrderNo(orderNo);
    	return result;
    }

    /**
     *  Get one sdBalance by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SdBalance findOne(Long id) {
        log.debug("Request to get SdBalance : {}", id);
        SdBalance sdBalance = sdBalanceRepository.findOne(id);
        return sdBalance;
    }

    /**
     *  Delete the  sdBalance by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SdBalance : {}", id);
        sdBalanceRepository.delete(id);
        sdBalanceSearchRepository.delete(id);
    }

    /**
     * Search for the sdBalance corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SdBalance> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SdBalances for query {}", query);
        Page<SdBalance> result = sdBalanceSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
