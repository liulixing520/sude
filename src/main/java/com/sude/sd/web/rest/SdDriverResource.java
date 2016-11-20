package com.sude.sd.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sude.sd.domain.SdDriver;
import com.sude.sd.service.SdDriverService;
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
 * REST controller for managing SdDriver.
 */
@RestController
@RequestMapping("/api")
public class SdDriverResource {

    private final Logger log = LoggerFactory.getLogger(SdDriverResource.class);
        
    @Inject
    private SdDriverService sdDriverService;

    /**
     * POST  /sd-drivers : Create a new sdDriver.
     *
     * @param sdDriver the sdDriver to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sdDriver, or with status 400 (Bad Request) if the sdDriver has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sd-drivers")
    @Timed
    public ResponseEntity<SdDriver> createSdDriver(@RequestBody SdDriver sdDriver) throws URISyntaxException {
        log.debug("REST request to save SdDriver : {}", sdDriver);
        if (sdDriver.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sdDriver", "idexists", "A new sdDriver cannot already have an ID")).body(null);
        }
        SdDriver result = sdDriverService.save(sdDriver);
        return ResponseEntity.created(new URI("/api/sd-drivers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sdDriver", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sd-drivers : Updates an existing sdDriver.
     *
     * @param sdDriver the sdDriver to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sdDriver,
     * or with status 400 (Bad Request) if the sdDriver is not valid,
     * or with status 500 (Internal Server Error) if the sdDriver couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sd-drivers")
    @Timed
    public ResponseEntity<SdDriver> updateSdDriver(@RequestBody SdDriver sdDriver) throws URISyntaxException {
        log.debug("REST request to update SdDriver : {}", sdDriver);
        if (sdDriver.getId() == null) {
            return createSdDriver(sdDriver);
        }
        SdDriver result = sdDriverService.save(sdDriver);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sdDriver", sdDriver.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sd-drivers : get all the sdDrivers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sdDrivers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/sd-drivers")
    @Timed
    public ResponseEntity<List<SdDriver>> getAllSdDrivers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SdDrivers");
        Page<SdDriver> page = sdDriverService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sd-drivers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sd-drivers/:id : get the "id" sdDriver.
     *
     * @param id the id of the sdDriver to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sdDriver, or with status 404 (Not Found)
     */
    @GetMapping("/sd-drivers/{id}")
    @Timed
    public ResponseEntity<SdDriver> getSdDriver(@PathVariable Long id) {
        log.debug("REST request to get SdDriver : {}", id);
        SdDriver sdDriver = sdDriverService.findOne(id);
        return Optional.ofNullable(sdDriver)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sd-drivers/:id : delete the "id" sdDriver.
     *
     * @param id the id of the sdDriver to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sd-drivers/{id}")
    @Timed
    public ResponseEntity<Void> deleteSdDriver(@PathVariable Long id) {
        log.debug("REST request to delete SdDriver : {}", id);
        sdDriverService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sdDriver", id.toString())).build();
    }

    /**
     * SEARCH  /_search/sd-drivers?query=:query : search for the sdDriver corresponding
     * to the query.
     *
     * @param query the query of the sdDriver search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/sd-drivers")
    @Timed
    public ResponseEntity<List<SdDriver>> searchSdDrivers(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of SdDrivers for query {}", query);
        Page<SdDriver> page = sdDriverService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sd-drivers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
