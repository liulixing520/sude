package com.sude.sd.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sude.sd.domain.SdOrderItem;
import com.sude.sd.domain.SequenceValueItem;
import com.sude.sd.service.SdOrderItemService;
import com.sude.sd.service.SequenceValueItemService;
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
 * REST controller for managing SdOrderItem.
 */
@RestController
@RequestMapping("/api")
public class SdOrderItemResource {

    private final Logger log = LoggerFactory.getLogger(SdOrderItemResource.class);
        
    @Inject
    private SdOrderItemService sdOrderItemService;
    @Inject
    private SequenceValueItemService sequenceValueItemService;
    /**
     * POST  /sd-order-items : Create a new sdOrderItem.
     *
     * @param sdOrderItem the sdOrderItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sdOrderItem, or with status 400 (Bad Request) if the sdOrderItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sd-order-items")
    @Timed
    public ResponseEntity<SdOrderItem> createSdOrderItem(@RequestBody SdOrderItem sdOrderItem) throws URISyntaxException {
        log.debug("REST request to save SdOrderItem : {}", sdOrderItem);
        if (sdOrderItem.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sdOrderItem", "idexists", "A new sdOrderItem cannot already have an ID")).body(null);
        }
        SdOrderItem result = sdOrderItemService.save(sdOrderItem);
        //更新seqId
        sequenceValueItemService.updateSeqId(this.getClass().getName(), Long.valueOf(result.getOrderNo()));
        return ResponseEntity.created(new URI("/api/sd-order-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sdOrderItem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sd-order-items : Updates an existing sdOrderItem.
     *
     * @param sdOrderItem the sdOrderItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sdOrderItem,
     * or with status 400 (Bad Request) if the sdOrderItem is not valid,
     * or with status 500 (Internal Server Error) if the sdOrderItem couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sd-order-items")
    @Timed
    public ResponseEntity<SdOrderItem> updateSdOrderItem(@RequestBody SdOrderItem sdOrderItem) throws URISyntaxException {
        log.debug("REST request to update SdOrderItem : {}", sdOrderItem);
        if (sdOrderItem.getId() == null) {
            return createSdOrderItem(sdOrderItem);
        }
        SdOrderItem result = sdOrderItemService.save(sdOrderItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sdOrderItem", sdOrderItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sd-order-items : get all the sdOrderItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sdOrderItems in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/sd-order-items")
    @Timed
    public ResponseEntity<List<SdOrderItem>> getAllSdOrderItems(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SdOrderItems");
        Page<SdOrderItem> page = sdOrderItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sd-order-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sd-order-items/:id : get the "id" sdOrderItem.
     *
     * @param id the id of the sdOrderItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sdOrderItem, or with status 404 (Not Found)
     */
    @GetMapping("/sd-order-items/{id}")
    @Timed
    public ResponseEntity<SdOrderItem> getSdOrderItem(@PathVariable Long id) {
        log.debug("REST request to get SdOrderItem : {}", id);
        SdOrderItem sdOrderItem = sdOrderItemService.findOne(id);
        return Optional.ofNullable(sdOrderItem)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sd-order-items/:id : delete the "id" sdOrderItem.
     *
     * @param id the id of the sdOrderItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sd-order-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteSdOrderItem(@PathVariable Long id) {
        log.debug("REST request to delete SdOrderItem : {}", id);
        sdOrderItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sdOrderItem", id.toString())).build();
    }

    /**
     * SEARCH  /_search/sd-order-items?query=:query : search for the sdOrderItem corresponding
     * to the query.
     *
     * @param query the query of the sdOrderItem search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/sd-order-items")
    @Timed
    public ResponseEntity<List<SdOrderItem>> searchSdOrderItems(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of SdOrderItems for query {}", query);
        Page<SdOrderItem> page = sdOrderItemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sd-order-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
