package com.sude.sd.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sude.sd.domain.SdOrderItem;
import com.sude.sd.repository.SdOrderItemRepository;
import com.sude.sd.repository.search.SdOrderItemSearchRepository;

/**
 * Service Implementation for managing SdOrderItem.
 */
@Service
@Transactional
public class SdOrderItemService {

    private final Logger log = LoggerFactory.getLogger(SdOrderItemService.class);
    
    @Inject
    private SdOrderItemRepository sdOrderItemRepository;

    @Inject
    private SdOrderItemSearchRepository sdOrderItemSearchRepository;
    

    /**
     * Save a sdOrderItem.
     *
     * @param sdOrderItem the entity to save
     * @return the persisted entity
     */
    public SdOrderItem save(SdOrderItem sdOrderItem) {
        log.debug("Request to save SdOrderItem : {}", sdOrderItem);
        SdOrderItem result = sdOrderItemRepository.save(sdOrderItem);
        sdOrderItemSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the sdOrderItems.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SdOrderItem> findAll(Pageable pageable) {
        log.debug("Request to get all SdOrderItems");
        Page<SdOrderItem> result = sdOrderItemRepository.findAll(pageable);
        return result;
    }
    
    /**
     *  Get all the sdOrderItems.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SdOrderItem> findByOrderStat(String orderStat,Pageable pageable) {
    	log.debug("Request to get all SdOrderItems");
    	Page<SdOrderItem> result = sdOrderItemRepository.findByOrderStat(orderStat,pageable);
    	return result;
    }

    /**
     *  Get one sdOrderItem by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SdOrderItem findOne(String id) {
        log.debug("Request to get SdOrderItem : {}", id);
        SdOrderItem sdOrderItem = sdOrderItemRepository.findOne(id);
        return sdOrderItem;
    }

    /**
     *  Delete the  sdOrderItem by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete SdOrderItem : {}", id);
        sdOrderItemRepository.delete(id);
        sdOrderItemSearchRepository.delete(id);
    }

    /**
     * Search for the sdOrderItem corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SdOrderItem> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SdOrderItems for query {}", query);
        Page<SdOrderItem> result = sdOrderItemSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
