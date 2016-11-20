package com.sude.sd.service;

import com.sude.sd.domain.EnumerationType;
import com.sude.sd.repository.EnumerationTypeRepository;
import com.sude.sd.repository.search.EnumerationTypeSearchRepository;
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
 * Service Implementation for managing EnumerationType.
 */
@Service
@Transactional
public class EnumerationTypeService {

    private final Logger log = LoggerFactory.getLogger(EnumerationTypeService.class);
    
    @Inject
    private EnumerationTypeRepository enumerationTypeRepository;

    @Inject
    private EnumerationTypeSearchRepository enumerationTypeSearchRepository;

    /**
     * Save a enumerationType.
     *
     * @param enumerationType the entity to save
     * @return the persisted entity
     */
    public EnumerationType save(EnumerationType enumerationType) {
        log.debug("Request to save EnumerationType : {}", enumerationType);
        EnumerationType result = enumerationTypeRepository.save(enumerationType);
        enumerationTypeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the enumerationTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<EnumerationType> findAll(Pageable pageable) {
        log.debug("Request to get all EnumerationTypes");
        Page<EnumerationType> result = enumerationTypeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one enumerationType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public EnumerationType findOne(String id) {
        log.debug("Request to get EnumerationType : {}", id);
        EnumerationType enumerationType = enumerationTypeRepository.findOne(id);
        return enumerationType;
    }

    /**
     *  Delete the  enumerationType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete EnumerationType : {}", id);
        enumerationTypeRepository.delete(id);
        enumerationTypeSearchRepository.delete(id);
    }

    /**
     * Search for the enumerationType corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EnumerationType> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EnumerationTypes for query {}", query);
        Page<EnumerationType> result = enumerationTypeSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
