package com.sude.sd.service;

import com.sude.sd.domain.SdCarInfo;
import com.sude.sd.repository.SdCarInfoRepository;
import com.sude.sd.repository.search.SdCarInfoSearchRepository;
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
 * Service Implementation for managing SdCarInfo.
 */
@Service
@Transactional
public class SdCarInfoService {

    private final Logger log = LoggerFactory.getLogger(SdCarInfoService.class);
    
    @Inject
    private SdCarInfoRepository sdCarInfoRepository;

    @Inject
    private SdCarInfoSearchRepository sdCarInfoSearchRepository;

    /**
     * Save a sdCarInfo.
     *
     * @param sdCarInfo the entity to save
     * @return the persisted entity
     */
    public SdCarInfo save(SdCarInfo sdCarInfo) {
        log.debug("Request to save SdCarInfo : {}", sdCarInfo);
        SdCarInfo result = sdCarInfoRepository.save(sdCarInfo);
        sdCarInfoSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the sdCarInfos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SdCarInfo> findAll(Pageable pageable) {
        log.debug("Request to get all SdCarInfos");
        Page<SdCarInfo> result = sdCarInfoRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one sdCarInfo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SdCarInfo findOne(Long id) {
        log.debug("Request to get SdCarInfo : {}", id);
        SdCarInfo sdCarInfo = sdCarInfoRepository.findOne(id);
        return sdCarInfo;
    }

    /**
     *  Delete the  sdCarInfo by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SdCarInfo : {}", id);
        sdCarInfoRepository.delete(id);
        sdCarInfoSearchRepository.delete(id);
    }

    /**
     * Search for the sdCarInfo corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SdCarInfo> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SdCarInfos for query {}", query);
        Page<SdCarInfo> result = sdCarInfoSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
