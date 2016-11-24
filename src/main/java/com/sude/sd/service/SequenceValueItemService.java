package com.sude.sd.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sude.sd.domain.SequenceValueItem;
import com.sude.sd.repository.SequenceValueItemRepository;
import com.sude.sd.repository.search.SequenceValueItemSearchRepository;

/**
 * Service Implementation for managing SequenceValueItem.
 */
@Service
@Transactional
public class SequenceValueItemService {

    private final Logger log = LoggerFactory.getLogger(SequenceValueItemService.class);
    
    @Inject
    private SequenceValueItemRepository sequenceValueItemRepository;

    @Inject
    private SequenceValueItemSearchRepository sequenceValueItemSearchRepository;

    /**
     * Save a sequenceValueItem.
     *
     * @param sequenceValueItem the entity to save
     * @return the persisted entity
     */
    public SequenceValueItem save(SequenceValueItem sequenceValueItem) {
        log.debug("Request to save SequenceValueItem : {}", sequenceValueItem);
        SequenceValueItem result = sequenceValueItemRepository.save(sequenceValueItem);
        sequenceValueItemSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the sequenceValueItems.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SequenceValueItem> findAll(Pageable pageable) {
        log.debug("Request to get all SequenceValueItems");
        Page<SequenceValueItem> result = sequenceValueItemRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one sequenceValueItem by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SequenceValueItem findOne(String id) {
        log.debug("Request to get SequenceValueItem : {}", id);
        SequenceValueItem sequenceValueItem = sequenceValueItemRepository.findOne(id);
        return sequenceValueItem;
    }

    /**
     *  Delete the  sequenceValueItem by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete SequenceValueItem : {}", id);
        sequenceValueItemRepository.delete(id);
        sequenceValueItemSearchRepository.delete(id);
    }

    /**
     * Search for the sequenceValueItem corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SequenceValueItem> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SequenceValueItems for query {}", query);
        Page<SequenceValueItem> result = sequenceValueItemSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
    
    /**
     *  获取该实体的下一个序列号.
     *
     *  @param id  the entityName
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Long getNextSeqIdLong(String id) {
        log.debug("Request to get SequenceValueItem : {}", id);
        SequenceValueItem sequenceValueItem = sequenceValueItemRepository.findOne(id);
        long retSeqId = 1;
        if(sequenceValueItem !=null && !"".equals(sequenceValueItem)){
        	if(sequenceValueItem.getSeqId()>0){
        		retSeqId++;
        	}else{
        		return retSeqId;
        	}
        }
        return retSeqId;
    }
    
    /**
     *  更新实体的序列号
     *
     *  @param id  the entityName
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public void updateSeqId(String id,Long seqId) {
        log.debug("Request to get SequenceValueItem : {}", id);
        SequenceValueItem sequenceValueItem = sequenceValueItemRepository.findOne(id);
        if(sequenceValueItem !=null && !"".equals(sequenceValueItem)){
        	sequenceValueItem.setSeqId(seqId);
        }else{
        	sequenceValueItem.setId(id);
        	sequenceValueItem.setSeqId(seqId);
        }
        sequenceValueItemRepository.save(sequenceValueItem);
    }
}
