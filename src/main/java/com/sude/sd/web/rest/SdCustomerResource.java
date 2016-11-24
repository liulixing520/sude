package com.sude.sd.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sude.sd.domain.SdCustomer;
import com.sude.sd.service.SdCustomerService;
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
 * REST controller for managing SdCustomer.
 */
@RestController
@RequestMapping("/api")
public class SdCustomerResource {

    private final Logger log = LoggerFactory.getLogger(SdCustomerResource.class);
        
    @Inject
    private SdCustomerService sdCustomerService;

    /**
     * POST  /sd-customers : Create a new sdCustomer.
     *
     * @param sdCustomer the sdCustomer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sdCustomer, or with status 400 (Bad Request) if the sdCustomer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sd-customers")
    @Timed
    public ResponseEntity<SdCustomer> createSdCustomer(@RequestBody SdCustomer sdCustomer) throws URISyntaxException {
        log.debug("REST request to save SdCustomer : {}", sdCustomer);
        if (sdCustomer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sdCustomer", "idexists", "A new sdCustomer cannot already have an ID")).body(null);
        }
        SdCustomer result = sdCustomerService.save(sdCustomer);
        return ResponseEntity.created(new URI("/api/sd-customers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sdCustomer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sd-customers : Updates an existing sdCustomer.
     *
     * @param sdCustomer the sdCustomer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sdCustomer,
     * or with status 400 (Bad Request) if the sdCustomer is not valid,
     * or with status 500 (Internal Server Error) if the sdCustomer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sd-customers")
    @Timed
    public ResponseEntity<SdCustomer> updateSdCustomer(@RequestBody SdCustomer sdCustomer) throws URISyntaxException {
        log.debug("REST request to update SdCustomer : {}", sdCustomer);
        if (sdCustomer.getId() == null) {
            return createSdCustomer(sdCustomer);
        }
        SdCustomer result = sdCustomerService.save(sdCustomer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sdCustomer", sdCustomer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sd-customers : get all the sdCustomers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sdCustomers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/sd-customers")
    @Timed
    public ResponseEntity<List<SdCustomer>> getAllSdCustomers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SdCustomers");
        Page<SdCustomer> page = sdCustomerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sd-customers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    

    /**
     * GET  /sd-customers/:id : get the "id" sdCustomer.
     *
     * @param id the id of the sdCustomer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sdCustomer, or with status 404 (Not Found)
     */
    @GetMapping("/sd-customers/{id}")
    @Timed
    public ResponseEntity<SdCustomer> getSdCustomer(@PathVariable Long id) {
        log.debug("REST request to get SdCustomer : {}", id);
        SdCustomer sdCustomer = sdCustomerService.findOne(id);
        return Optional.ofNullable(sdCustomer)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sd-customers/:id : delete the "id" sdCustomer.
     *
     * @param id the id of the sdCustomer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sd-customers/{id}")
    @Timed
    public ResponseEntity<Void> deleteSdCustomer(@PathVariable Long id) {
        log.debug("REST request to delete SdCustomer : {}", id);
        sdCustomerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sdCustomer", id.toString())).build();
    }

    /**
     * SEARCH  /_search/sd-customers?query=:query : search for the sdCustomer corresponding
     * to the query.
     *
     * @param query the query of the sdCustomer search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/sd-customers")
    @Timed
    public ResponseEntity<List<SdCustomer>> searchSdCustomers(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of SdCustomers for query {}", query);
        Page<SdCustomer> page = sdCustomerService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sd-customers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * 搜索客户、托运单录入时调用
     *
     * @param query the query of the sdCustomer search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/searchSdCustomers")
    @Timed
    public List<SdCustomer> searchSdCustomers(@RequestParam String query)
        throws URISyntaxException {
        log.debug("REST request to search for a page of SdCustomers for query {}", query);
        List<SdCustomer> list = sdCustomerService.search(query);
        return list;
    }
    

}
