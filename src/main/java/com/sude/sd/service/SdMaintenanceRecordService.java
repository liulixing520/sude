package com.sude.sd.service;

import com.sude.sd.domain.SdMaintenanceRecord;
import com.sude.sd.repository.SdMaintenanceRecordRepository;
import com.sude.sd.repository.search.SdMaintenanceRecordSearchRepository;
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
 * Service Implementation for managing SdMaintenanceRecord.
 */
@Service
@Transactional
public class SdMaintenanceRecordService {

    private final Logger log = LoggerFactory.getLogger(SdMaintenanceRecordService.class);
    
    @Inject
    private SdMaintenanceRecordRepository sdMaintenanceRecordRepository;

    @Inject
    private SdMaintenanceRecordSearchRepository sdMaintenanceRecordSearchRepository;

    /**
     * Save a sdMaintenanceRecord.
     *
     * @param sdMaintenanceRecord the entity to save
     * @return the persisted entity
     */
    public SdMaintenanceRecord save(SdMaintenanceRecord sdMaintenanceRecord) {
        log.debug("Request to save SdMaintenanceRecord : {}", sdMaintenanceRecord);
        SdMaintenanceRecord result = sdMaintenanceRecordRepository.save(sdMaintenanceRecord);
        sdMaintenanceRecordSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the sdMaintenanceRecords.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SdMaintenanceRecord> findAll(Pageable pageable) {
        log.debug("Request to get all SdMaintenanceRecords");
        Page<SdMaintenanceRecord> result = sdMaintenanceRecordRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one sdMaintenanceRecord by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SdMaintenanceRecord findOne(Long id) {
        log.debug("Request to get SdMaintenanceRecord : {}", id);
        SdMaintenanceRecord sdMaintenanceRecord = sdMaintenanceRecordRepository.findOne(id);
        return sdMaintenanceRecord;
    }

    /**
     *  Delete the  sdMaintenanceRecord by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SdMaintenanceRecord : {}", id);
        sdMaintenanceRecordRepository.delete(id);
        sdMaintenanceRecordSearchRepository.delete(id);
    }

    /**
     * Search for the sdMaintenanceRecord corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SdMaintenanceRecord> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SdMaintenanceRecords for query {}", query);
        Page<SdMaintenanceRecord> result = sdMaintenanceRecordSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
