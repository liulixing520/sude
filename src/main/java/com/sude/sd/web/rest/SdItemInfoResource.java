package com.sude.sd.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sude.sd.domain.SdItemInfo;
import com.sude.sd.service.SdItemInfoService;
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
 * REST controller for managing SdItemInfo.
 */
@RestController
@RequestMapping("/api")
public class SdItemInfoResource {

    private final Logger log = LoggerFactory.getLogger(SdItemInfoResource.class);
        
    @Inject
    private SdItemInfoService sdItemInfoService;

    /**
     * POST  /sd-item-infos : Create a new sdItemInfo.
     *
     * @param sdItemInfo the sdItemInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sdItemInfo, or with status 400 (Bad Request) if the sdItemInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sd-item-infos")
    @Timed
    public ResponseEntity<SdItemInfo> createSdItemInfo(@RequestBody SdItemInfo sdItemInfo) throws URISyntaxException {
        log.debug("REST request to save SdItemInfo : {}", sdItemInfo);
        if (sdItemInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sdItemInfo", "idexists", "A new sdItemInfo cannot already have an ID")).body(null);
        }
        SdItemInfo result = sdItemInfoService.save(sdItemInfo);
        return ResponseEntity.created(new URI("/api/sd-item-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sdItemInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sd-item-infos : Updates an existing sdItemInfo.
     *
     * @param sdItemInfo the sdItemInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sdItemInfo,
     * or with status 400 (Bad Request) if the sdItemInfo is not valid,
     * or with status 500 (Internal Server Error) if the sdItemInfo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sd-item-infos")
    @Timed
    public ResponseEntity<SdItemInfo> updateSdItemInfo(@RequestBody SdItemInfo sdItemInfo) throws URISyntaxException {
        log.debug("REST request to update SdItemInfo : {}", sdItemInfo);
        if (sdItemInfo.getId() == null) {
            return createSdItemInfo(sdItemInfo);
        }
        SdItemInfo result = sdItemInfoService.save(sdItemInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sdItemInfo", sdItemInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sd-item-infos : get all the sdItemInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sdItemInfos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/sd-item-infos")
    @Timed
    public ResponseEntity<List<SdItemInfo>> getAllSdItemInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SdItemInfos");
        Page<SdItemInfo> page = sdItemInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sd-item-infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sd-item-infos/:id : get the "id" sdItemInfo.
     *
     * @param id the id of the sdItemInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sdItemInfo, or with status 404 (Not Found)
     */
    @GetMapping("/sd-item-infos/{id}")
    @Timed
    public ResponseEntity<SdItemInfo> getSdItemInfo(@PathVariable Long id) {
        log.debug("REST request to get SdItemInfo : {}", id);
        SdItemInfo sdItemInfo = sdItemInfoService.findOne(id);
        return Optional.ofNullable(sdItemInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sd-item-infos/:id : delete the "id" sdItemInfo.
     *
     * @param id the id of the sdItemInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sd-item-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteSdItemInfo(@PathVariable Long id) {
        log.debug("REST request to delete SdItemInfo : {}", id);
        sdItemInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sdItemInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/sd-item-infos?query=:query : search for the sdItemInfo corresponding
     * to the query.
     *
     * @param query the query of the sdItemInfo search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/sd-item-infos")
    @Timed
    public ResponseEntity<List<SdItemInfo>> searchSdItemInfos(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of SdItemInfos for query {}", query);
        Page<SdItemInfo> page = sdItemInfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sd-item-infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
