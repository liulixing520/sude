package com.sude.sd.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sude.sd.domain.SdOrderHeader;
import com.sude.sd.repository.SdOrderHeaderRepository;
import com.sude.sd.repository.search.SdOrderHeaderSearchRepository;
import com.sude.sd.security.SecurityUtils;

/**
 * Service Implementation for managing SdOrderHeader.
 */
@Service
@Transactional
public class SdOrderHeaderService {

    private final Logger log = LoggerFactory.getLogger(SdOrderHeaderService.class);
    
    @Inject
    private SdOrderHeaderRepository sdOrderHeaderRepository;

    @Inject
    private SdOrderHeaderSearchRepository sdOrderHeaderSearchRepository;
    
    @Inject
    private SequenceValueItemService sequenceValueItemService;

    /**
     * Save a sdOrderHeader.
     *
     * @param sdOrderHeader the entity to save
     * @return the persisted entity
     */
    public SdOrderHeader save(SdOrderHeader sdOrderHeader) {
        log.debug("Request to save SdOrderHeader : {}", sdOrderHeader);
        SdOrderHeader result = sdOrderHeaderRepository.save(sdOrderHeader);
        sdOrderHeaderSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the sdOrderHeaders.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SdOrderHeader> findAll(Pageable pageable) {
        log.debug("Request to get all SdOrderHeaders");
        String currentLogin = SecurityUtils.getCurrentUserLogin();
        Page<SdOrderHeader> result = sdOrderHeaderRepository.findByCreatedBy(currentLogin,pageable);
        return result;
    }

    /**
     *  Get one sdOrderHeader by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SdOrderHeader findOne(Long id) {
        log.debug("Request to get SdOrderHeader : {}", id);
        SdOrderHeader sdOrderHeader = sdOrderHeaderRepository.findOne(id);
        return sdOrderHeader;
    }

    /**
     *  Delete the  sdOrderHeader by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SdOrderHeader : {}", id);
        sdOrderHeaderRepository.delete(id);
        sdOrderHeaderSearchRepository.delete(id);
    }

    /**
     * Search for the sdOrderHeader corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SdOrderHeader> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SdOrderHeaders for query {}", query);
        Page<SdOrderHeader> result = sdOrderHeaderSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
    
    /**
     *  获取下个合同号
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Long getNextHeaderNo() {
        log.debug("Request to get SdOrderHeader : {}");
        return sequenceValueItemService.getNextSeqIdLong("SdOrderHeader");
    }
    
}
