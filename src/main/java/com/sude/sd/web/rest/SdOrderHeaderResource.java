package com.sude.sd.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sude.sd.domain.SdOrderHeader;
import com.sude.sd.service.SdOrderHeaderService;
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
 * REST controller for managing SdOrderHeader.
 */
@RestController
@RequestMapping("/api")
public class SdOrderHeaderResource {

    private final Logger log = LoggerFactory.getLogger(SdOrderHeaderResource.class);
        
    @Inject
    private SdOrderHeaderService sdOrderHeaderService;

    /**
     * POST  /sd-order-headers : Create a new sdOrderHeader.
     *
     * @param sdOrderHeader the sdOrderHeader to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sdOrderHeader, or with status 400 (Bad Request) if the sdOrderHeader has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sd-order-headers")
    @Timed
    public ResponseEntity<SdOrderHeader> createSdOrderHeader(@RequestBody SdOrderHeader sdOrderHeader) throws URISyntaxException {
        log.debug("REST request to save SdOrderHeader : {}", sdOrderHeader);
        if (sdOrderHeader.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sdOrderHeader", "idexists", "A new sdOrderHeader cannot already have an ID")).body(null);
        }
        SdOrderHeader result = sdOrderHeaderService.save(sdOrderHeader);
        return ResponseEntity.created(new URI("/api/sd-order-headers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sdOrderHeader", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sd-order-headers : Updates an existing sdOrderHeader.
     *
     * @param sdOrderHeader the sdOrderHeader to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sdOrderHeader,
     * or with status 400 (Bad Request) if the sdOrderHeader is not valid,
     * or with status 500 (Internal Server Error) if the sdOrderHeader couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sd-order-headers")
    @Timed
    public ResponseEntity<SdOrderHeader> updateSdOrderHeader(@RequestBody SdOrderHeader sdOrderHeader) throws URISyntaxException {
        log.debug("REST request to update SdOrderHeader : {}", sdOrderHeader);
        if (sdOrderHeader.getId() == null) {
            return createSdOrderHeader(sdOrderHeader);
        }
        SdOrderHeader result = sdOrderHeaderService.save(sdOrderHeader);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sdOrderHeader", sdOrderHeader.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sd-order-headers : get all the sdOrderHeaders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sdOrderHeaders in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/sd-order-headers")
    @Timed
    public ResponseEntity<List<SdOrderHeader>> getAllSdOrderHeaders(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SdOrderHeaders");
        Page<SdOrderHeader> page = sdOrderHeaderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sd-order-headers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sd-order-headers/:id : get the "id" sdOrderHeader.
     *
     * @param id the id of the sdOrderHeader to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sdOrderHeader, or with status 404 (Not Found)
     */
    @GetMapping("/sd-order-headers/{id}")
    @Timed
    public ResponseEntity<SdOrderHeader> getSdOrderHeader(@PathVariable Long id) {
        log.debug("REST request to get SdOrderHeader : {}", id);
        SdOrderHeader sdOrderHeader = sdOrderHeaderService.findOne(id);
        return Optional.ofNullable(sdOrderHeader)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sd-order-headers/:id : delete the "id" sdOrderHeader.
     *
     * @param id the id of the sdOrderHeader to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sd-order-headers/{id}")
    @Timed
    public ResponseEntity<Void> deleteSdOrderHeader(@PathVariable Long id) {
        log.debug("REST request to delete SdOrderHeader : {}", id);
        sdOrderHeaderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sdOrderHeader", id.toString())).build();
    }

    /**
     * SEARCH  /_search/sd-order-headers?query=:query : search for the sdOrderHeader corresponding
     * to the query.
     *
     * @param query the query of the sdOrderHeader search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/sd-order-headers")
    @Timed
    public ResponseEntity<List<SdOrderHeader>> searchSdOrderHeaders(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of SdOrderHeaders for query {}", query);
        Page<SdOrderHeader> page = sdOrderHeaderService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sd-order-headers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
