package com.sude.sd.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sude.sd.domain.SdCustomer;
import com.sude.sd.domain.SdStation;
import com.sude.sd.service.SdStationService;
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
 * REST controller for managing SdStation.
 */
@RestController
@RequestMapping("/api")
public class SdStationResource {

    private final Logger log = LoggerFactory.getLogger(SdStationResource.class);
        
    @Inject
    private SdStationService sdStationService;

    /**
     * POST  /sd-stations : Create a new sdStation.
     *
     * @param sdStation the sdStation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sdStation, or with status 400 (Bad Request) if the sdStation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sd-stations")
    @Timed
    public ResponseEntity<SdStation> createSdStation(@RequestBody SdStation sdStation) throws URISyntaxException {
        log.debug("REST request to save SdStation : {}", sdStation);
        if (sdStation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sdStation", "idexists", "A new sdStation cannot already have an ID")).body(null);
        }
        SdStation result = sdStationService.save(sdStation);
        return ResponseEntity.created(new URI("/api/sd-stations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sdStation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sd-stations : Updates an existing sdStation.
     *
     * @param sdStation the sdStation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sdStation,
     * or with status 400 (Bad Request) if the sdStation is not valid,
     * or with status 500 (Internal Server Error) if the sdStation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sd-stations")
    @Timed
    public ResponseEntity<SdStation> updateSdStation(@RequestBody SdStation sdStation) throws URISyntaxException {
        log.debug("REST request to update SdStation : {}", sdStation);
        if (sdStation.getId() == null) {
            return createSdStation(sdStation);
        }
        SdStation result = sdStationService.save(sdStation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sdStation", sdStation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sd-stations : get all the sdStations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sdStations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/sd-stations")
    @Timed
    public ResponseEntity<List<SdStation>> getAllSdStations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SdStations");
        Page<SdStation> page = sdStationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sd-stations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sd-stations/:id : get the "id" sdStation.
     *
     * @param id the id of the sdStation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sdStation, or with status 404 (Not Found)
     */
    @GetMapping("/sd-stations/{id}")
    @Timed
    public ResponseEntity<SdStation> getSdStation(@PathVariable Long id) {
        log.debug("REST request to get SdStation : {}", id);
        SdStation sdStation = sdStationService.findOne(id);
        return Optional.ofNullable(sdStation)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    /**
     * GET  /sd-stations/:id : get the "id" sdStation.
     *
     * @param id the id of the sdStation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sdStation, or with status 404 (Not Found)
     */
    @GetMapping("/get-one-station/{id}")
    @Timed
    public SdStation getOneStation(@PathVariable Long id) {
    	log.debug("REST request to get SdStation : {}", id);
    	SdStation sdStation = sdStationService.findOne(id);
    	return sdStation;
    }

    /**
     * DELETE  /sd-stations/:id : delete the "id" sdStation.
     *
     * @param id the id of the sdStation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sd-stations/{id}")
    @Timed
    public ResponseEntity<Void> deleteSdStation(@PathVariable Long id) {
        log.debug("REST request to delete SdStation : {}", id);
        sdStationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sdStation", id.toString())).build();
    }

    /**
     * SEARCH  /_search/sd-stations?query=:query : search for the sdStation corresponding
     * to the query.
     *
     * @param query the query of the sdStation search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/sd-stations")
    @Timed
    public ResponseEntity<List<SdStation>> searchSdStations(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of SdStations for query {}", query);
        Page<SdStation> page = sdStationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sd-stations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * 搜索站点、托运单录入时调用
     *
     * @param query the query of the sdCustomer search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/searchSdStation")
    @Timed
    public List<SdStation> searchSdStation(@RequestParam String query)
        throws URISyntaxException {
        log.debug("REST request to search for a page of SdCustomers for query {}", query);
        List<SdStation> list = sdStationService.search(query);
        return list;
    }
}
