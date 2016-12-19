package com.sude.sd.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sude.sd.domain.SdBalance;
import com.sude.sd.service.SdBalanceService;
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
 * REST controller for managing SdBalance.
 */
@RestController
@RequestMapping("/api")
public class SdBalanceResource {

    private final Logger log = LoggerFactory.getLogger(SdBalanceResource.class);
        
    @Inject
    private SdBalanceService sdBalanceService;

    /**
     * POST  /sd-balances : Create a new sdBalance.
     *
     * @param sdBalance the sdBalance to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sdBalance, or with status 400 (Bad Request) if the sdBalance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sd-balances")
    @Timed
    public ResponseEntity<SdBalance> createSdBalance(@RequestBody SdBalance sdBalance) throws URISyntaxException {
        log.debug("REST request to save SdBalance : {}", sdBalance);
        if (sdBalance.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sdBalance", "idexists", "A new sdBalance cannot already have an ID")).body(null);
        }
        SdBalance result = sdBalanceService.save(sdBalance);
        return ResponseEntity.created(new URI("/api/sd-balances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sdBalance", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sd-balances : Updates an existing sdBalance.
     *
     * @param sdBalance the sdBalance to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sdBalance,
     * or with status 400 (Bad Request) if the sdBalance is not valid,
     * or with status 500 (Internal Server Error) if the sdBalance couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sd-balances")
    @Timed
    public ResponseEntity<SdBalance> updateSdBalance(@RequestBody SdBalance sdBalance) throws URISyntaxException {
        log.debug("REST request to update SdBalance : {}", sdBalance);
        if (sdBalance.getId() == null) {
            return createSdBalance(sdBalance);
        }
        SdBalance result = sdBalanceService.save(sdBalance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sdBalance", sdBalance.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sd-balances : get all the sdBalances.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sdBalances in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/sd-balances")
    @Timed
    public ResponseEntity<List<SdBalance>> getAllSdBalances(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SdBalances");
        Page<SdBalance> page = sdBalanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sd-balances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sd-balances/:id : get the "id" sdBalance.
     *
     * @param id the id of the sdBalance to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sdBalance, or with status 404 (Not Found)
     */
    @GetMapping("/sd-balances/{id}")
    @Timed
    public ResponseEntity<SdBalance> getSdBalance(@PathVariable Long id) {
        log.debug("REST request to get SdBalance : {}", id);
        SdBalance sdBalance = sdBalanceService.findOne(id);
        return Optional.ofNullable(sdBalance)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sd-balances/:id : delete the "id" sdBalance.
     *
     * @param id the id of the sdBalance to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sd-balances/{id}")
    @Timed
    public ResponseEntity<Void> deleteSdBalance(@PathVariable Long id) {
        log.debug("REST request to delete SdBalance : {}", id);
        sdBalanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sdBalance", id.toString())).build();
    }

    /**
     * SEARCH  /_search/sd-balances?query=:query : search for the sdBalance corresponding
     * to the query.
     *
     * @param query the query of the sdBalance search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/sd-balances")
    @Timed
    public ResponseEntity<List<SdBalance>> searchSdBalances(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of SdBalances for query {}", query);
        Page<SdBalance> page = sdBalanceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sd-balances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
