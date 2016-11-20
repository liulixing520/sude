package com.sude.sd.service;

import com.sude.sd.domain.SdItemInfo;
import com.sude.sd.repository.SdItemInfoRepository;
import com.sude.sd.repository.search.SdItemInfoSearchRepository;
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
 * Service Implementation for managing SdItemInfo.
 */
@Service
@Transactional
public class SdItemInfoService {

    private final Logger log = LoggerFactory.getLogger(SdItemInfoService.class);
    
    @Inject
    private SdItemInfoRepository sdItemInfoRepository;

    @Inject
    private SdItemInfoSearchRepository sdItemInfoSearchRepository;

    /**
     * Save a sdItemInfo.
     *
     * @param sdItemInfo the entity to save
     * @return the persisted entity
     */
    public SdItemInfo save(SdItemInfo sdItemInfo) {
        log.debug("Request to save SdItemInfo : {}", sdItemInfo);
        SdItemInfo result = sdItemInfoRepository.save(sdItemInfo);
        sdItemInfoSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the sdItemInfos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SdItemInfo> findAll(Pageable pageable) {
        log.debug("Request to get all SdItemInfos");
        Page<SdItemInfo> result = sdItemInfoRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one sdItemInfo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SdItemInfo findOne(Long id) {
        log.debug("Request to get SdItemInfo : {}", id);
        SdItemInfo sdItemInfo = sdItemInfoRepository.findOne(id);
        return sdItemInfo;
    }

    /**
     *  Delete the  sdItemInfo by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SdItemInfo : {}", id);
        sdItemInfoRepository.delete(id);
        sdItemInfoSearchRepository.delete(id);
    }

    /**
     * Search for the sdItemInfo corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SdItemInfo> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SdItemInfos for query {}", query);
        Page<SdItemInfo> result = sdItemInfoSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
