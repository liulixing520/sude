package com.sude.sd.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sude.sd.domain.SdCompany;
import com.sude.sd.service.SdCompanyService;
import com.sude.sd.web.rest.util.HeaderUtil;
import com.sude.sd.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing SdCompany.
 */
@RestController
@RequestMapping("/api")
public class SdCompanyResource {

    private final Logger log = LoggerFactory.getLogger(SdCompanyResource.class);
        
    @Inject
    private SdCompanyService sdCompanyService;

    /**
     * POST  /sd-companies : Create a new sdCompany.
     *
     * @param sdCompany the sdCompany to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sdCompany, or with status 400 (Bad Request) if the sdCompany has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sd-companies")
    @Timed
    public ResponseEntity<SdCompany> createSdCompany(@RequestBody SdCompany sdCompany) throws URISyntaxException {
        log.debug("REST request to save SdCompany : {}", sdCompany);
        if (sdCompany.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sdCompany", "idexists", "A new sdCompany cannot already have an ID")).body(null);
        }
        SdCompany result = sdCompanyService.save(sdCompany);
        return ResponseEntity.created(new URI("/api/sd-companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sdCompany", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sd-companies : Updates an existing sdCompany.
     *
     * @param sdCompany the sdCompany to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sdCompany,
     * or with status 400 (Bad Request) if the sdCompany is not valid,
     * or with status 500 (Internal Server Error) if the sdCompany couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sd-companies")
    @Timed
    public ResponseEntity<SdCompany> updateSdCompany(@RequestBody SdCompany sdCompany) throws URISyntaxException {
        log.debug("REST request to update SdCompany : {}", sdCompany);
        if (sdCompany.getId() == null) {
            return createSdCompany(sdCompany);
        }
        SdCompany result = sdCompanyService.save(sdCompany);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sdCompany", sdCompany.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sd-companies : get all the sdCompanies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sdCompanies in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/sd-companies")
    @Timed
    public ResponseEntity<List<SdCompany>> getAllSdCompanies(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SdCompanies");
        Page<SdCompany> page = sdCompanyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sd-companies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sd-companies/:id : get the "id" sdCompany.
     *
     * @param id the id of the sdCompany to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sdCompany, or with status 404 (Not Found)
     */
    @GetMapping("/sd-companies/{id}")
    @Timed
    public ResponseEntity<SdCompany> getSdCompany(@PathVariable Long id) {
        log.debug("REST request to get SdCompany : {}", id);
        SdCompany sdCompany = sdCompanyService.findOne(id);
        return Optional.ofNullable(sdCompany)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sd-companies/:id : delete the "id" sdCompany.
     *
     * @param id the id of the sdCompany to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sd-companies/{id}")
    @Timed
    public ResponseEntity<Void> deleteSdCompany(@PathVariable Long id) {
        log.debug("REST request to delete SdCompany : {}", id);
        sdCompanyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sdCompany", id.toString())).build();
    }

    /**
     * SEARCH  /_search/sd-companies?query=:query : search for the sdCompany corresponding
     * to the query.
     *
     * @param query the query of the sdCompany search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/sd-companies")
    @Timed
    public ResponseEntity<List<SdCompany>> searchSdCompanies(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of SdCompanies for query {}", query);
        Page<SdCompany> page = sdCompanyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sd-companies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
