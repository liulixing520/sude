package com.sude.sd.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sude.sd.domain.SdCarInfo;
import com.sude.sd.service.SdCarInfoService;
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
 * REST controller for managing SdCarInfo.
 */
@RestController
@RequestMapping("/api")
public class SdCarInfoResource {

    private final Logger log = LoggerFactory.getLogger(SdCarInfoResource.class);
        
    @Inject
    private SdCarInfoService sdCarInfoService;

    /**
     * POST  /sd-car-infos : Create a new sdCarInfo.
     *
     * @param sdCarInfo the sdCarInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sdCarInfo, or with status 400 (Bad Request) if the sdCarInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sd-car-infos")
    @Timed
    public ResponseEntity<SdCarInfo> createSdCarInfo(@RequestBody SdCarInfo sdCarInfo) throws URISyntaxException {
        log.debug("REST request to save SdCarInfo : {}", sdCarInfo);
        if (sdCarInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sdCarInfo", "idexists", "A new sdCarInfo cannot already have an ID")).body(null);
        }
        SdCarInfo result = sdCarInfoService.save(sdCarInfo);
        return ResponseEntity.created(new URI("/api/sd-car-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sdCarInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sd-car-infos : Updates an existing sdCarInfo.
     *
     * @param sdCarInfo the sdCarInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sdCarInfo,
     * or with status 400 (Bad Request) if the sdCarInfo is not valid,
     * or with status 500 (Internal Server Error) if the sdCarInfo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sd-car-infos")
    @Timed
    public ResponseEntity<SdCarInfo> updateSdCarInfo(@RequestBody SdCarInfo sdCarInfo) throws URISyntaxException {
        log.debug("REST request to update SdCarInfo : {}", sdCarInfo);
        if (sdCarInfo.getId() == null) {
            return createSdCarInfo(sdCarInfo);
        }
        SdCarInfo result = sdCarInfoService.save(sdCarInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sdCarInfo", sdCarInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sd-car-infos : get all the sdCarInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sdCarInfos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/sd-car-infos")
    @Timed
    public ResponseEntity<List<SdCarInfo>> getAllSdCarInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SdCarInfos");
        Page<SdCarInfo> page = sdCarInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sd-car-infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sd-car-infos/:id : get the "id" sdCarInfo.
     *
     * @param id the id of the sdCarInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sdCarInfo, or with status 404 (Not Found)
     */
    @GetMapping("/sd-car-infos/{id}")
    @Timed
    public ResponseEntity<SdCarInfo> getSdCarInfo(@PathVariable String id) {
        log.debug("REST request to get SdCarInfo : {}", id);
        SdCarInfo sdCarInfo = sdCarInfoService.findOne(id);
        return Optional.ofNullable(sdCarInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sd-car-infos/:id : delete the "id" sdCarInfo.
     *
     * @param id the id of the sdCarInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sd-car-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteSdCarInfo(@PathVariable String id) {
        log.debug("REST request to delete SdCarInfo : {}", id);
        sdCarInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sdCarInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/sd-car-infos?query=:query : search for the sdCarInfo corresponding
     * to the query.
     *
     * @param query the query of the sdCarInfo search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/sd-car-infos")
    @Timed
    public ResponseEntity<List<SdCarInfo>> searchSdCarInfos(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of SdCarInfos for query {}", query);
        Page<SdCarInfo> page = sdCarInfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sd-car-infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * SEARCH  /_search/searchById?id=:id : 根据车牌号查找车辆信息
     * to the query.
     *
     * @param query the query of the sdCarInfo search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/searchById")
    @Timed
    public List<SdCarInfo> searchById(@RequestParam String id)
    		throws URISyntaxException {
    	log.debug("REST request to search for a page of SdCarInfos for query {}", id);
    	List<SdCarInfo> result = sdCarInfoService.findByIdLike("%"+id+"%");
    	return result;
    }


}
