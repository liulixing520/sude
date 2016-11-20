package com.sude.sd.service;

import com.sude.sd.domain.SdCompany;
import com.sude.sd.repository.SdCompanyRepository;
import com.sude.sd.repository.search.SdCompanySearchRepository;
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
 * Service Implementation for managing SdCompany.
 */
@Service
@Transactional
public class SdCompanyService {

    private final Logger log = LoggerFactory.getLogger(SdCompanyService.class);
    
    @Inject
    private SdCompanyRepository sdCompanyRepository;

    @Inject
    private SdCompanySearchRepository sdCompanySearchRepository;

    /**
     * Save a sdCompany.
     *
     * @param sdCompany the entity to save
     * @return the persisted entity
     */
    public SdCompany save(SdCompany sdCompany) {
        log.debug("Request to save SdCompany : {}", sdCompany);
        SdCompany result = sdCompanyRepository.save(sdCompany);
        sdCompanySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the sdCompanies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SdCompany> findAll(Pageable pageable) {
        log.debug("Request to get all SdCompanies");
        Page<SdCompany> result = sdCompanyRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one sdCompany by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SdCompany findOne(Long id) {
        log.debug("Request to get SdCompany : {}", id);
        SdCompany sdCompany = sdCompanyRepository.findOne(id);
        return sdCompany;
    }

    /**
     *  Delete the  sdCompany by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SdCompany : {}", id);
        sdCompanyRepository.delete(id);
        sdCompanySearchRepository.delete(id);
    }

    /**
     * Search for the sdCompany corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SdCompany> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SdCompanies for query {}", query);
        Page<SdCompany> result = sdCompanySearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
