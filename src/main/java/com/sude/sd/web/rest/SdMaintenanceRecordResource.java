package com.sude.sd.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sude.sd.domain.SdMaintenanceRecord;
import com.sude.sd.service.SdMaintenanceRecordService;
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
 * REST controller for managing SdMaintenanceRecord.
 */
@RestController
@RequestMapping("/api")
public class SdMaintenanceRecordResource {

    private final Logger log = LoggerFactory.getLogger(SdMaintenanceRecordResource.class);
        
    @Inject
    private SdMaintenanceRecordService sdMaintenanceRecordService;

    /**
     * POST  /sd-maintenance-records : Create a new sdMaintenanceRecord.
     *
     * @param sdMaintenanceRecord the sdMaintenanceRecord to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sdMaintenanceRecord, or with status 400 (Bad Request) if the sdMaintenanceRecord has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sd-maintenance-records")
    @Timed
    public ResponseEntity<SdMaintenanceRecord> createSdMaintenanceRecord(@RequestBody SdMaintenanceRecord sdMaintenanceRecord) throws URISyntaxException {
        log.debug("REST request to save SdMaintenanceRecord : {}", sdMaintenanceRecord);
        if (sdMaintenanceRecord.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sdMaintenanceRecord", "idexists", "A new sdMaintenanceRecord cannot already have an ID")).body(null);
        }
        SdMaintenanceRecord result = sdMaintenanceRecordService.save(sdMaintenanceRecord);
        return ResponseEntity.created(new URI("/api/sd-maintenance-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sdMaintenanceRecord", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sd-maintenance-records : Updates an existing sdMaintenanceRecord.
     *
     * @param sdMaintenanceRecord the sdMaintenanceRecord to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sdMaintenanceRecord,
     * or with status 400 (Bad Request) if the sdMaintenanceRecord is not valid,
     * or with status 500 (Internal Server Error) if the sdMaintenanceRecord couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sd-maintenance-records")
    @Timed
    public ResponseEntity<SdMaintenanceRecord> updateSdMaintenanceRecord(@RequestBody SdMaintenanceRecord sdMaintenanceRecord) throws URISyntaxException {
        log.debug("REST request to update SdMaintenanceRecord : {}", sdMaintenanceRecord);
        if (sdMaintenanceRecord.getId() == null) {
            return createSdMaintenanceRecord(sdMaintenanceRecord);
        }
        SdMaintenanceRecord result = sdMaintenanceRecordService.save(sdMaintenanceRecord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sdMaintenanceRecord", sdMaintenanceRecord.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sd-maintenance-records : get all the sdMaintenanceRecords.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sdMaintenanceRecords in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/sd-maintenance-records")
    @Timed
    public ResponseEntity<List<SdMaintenanceRecord>> getAllSdMaintenanceRecords(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SdMaintenanceRecords");
        Page<SdMaintenanceRecord> page = sdMaintenanceRecordService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sd-maintenance-records");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sd-maintenance-records/:id : get the "id" sdMaintenanceRecord.
     *
     * @param id the id of the sdMaintenanceRecord to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sdMaintenanceRecord, or with status 404 (Not Found)
     */
    @GetMapping("/sd-maintenance-records/{id}")
    @Timed
    public ResponseEntity<SdMaintenanceRecord> getSdMaintenanceRecord(@PathVariable Long id) {
        log.debug("REST request to get SdMaintenanceRecord : {}", id);
        SdMaintenanceRecord sdMaintenanceRecord = sdMaintenanceRecordService.findOne(id);
        return Optional.ofNullable(sdMaintenanceRecord)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sd-maintenance-records/:id : delete the "id" sdMaintenanceRecord.
     *
     * @param id the id of the sdMaintenanceRecord to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sd-maintenance-records/{id}")
    @Timed
    public ResponseEntity<Void> deleteSdMaintenanceRecord(@PathVariable Long id) {
        log.debug("REST request to delete SdMaintenanceRecord : {}", id);
        sdMaintenanceRecordService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sdMaintenanceRecord", id.toString())).build();
    }

    /**
     * SEARCH  /_search/sd-maintenance-records?query=:query : search for the sdMaintenanceRecord corresponding
     * to the query.
     *
     * @param query the query of the sdMaintenanceRecord search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/sd-maintenance-records")
    @Timed
    public ResponseEntity<List<SdMaintenanceRecord>> searchSdMaintenanceRecords(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of SdMaintenanceRecords for query {}", query);
        Page<SdMaintenanceRecord> page = sdMaintenanceRecordService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sd-maintenance-records");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
