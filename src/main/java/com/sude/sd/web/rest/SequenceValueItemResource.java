package com.sude.sd.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.sude.sd.domain.SequenceValueItem;
import com.sude.sd.service.SequenceValueItemService;
import com.sude.sd.web.rest.util.HeaderUtil;
import com.sude.sd.web.rest.util.PaginationUtil;

/**
 * REST controller for managing SequenceValueItem.
 */
@RestController
@RequestMapping("/api")
public class SequenceValueItemResource {

    private final Logger log = LoggerFactory.getLogger(SequenceValueItemResource.class);
        
    @Inject
    private SequenceValueItemService sequenceValueItemService;

    /**
     * POST  /sequence-value-items : Create a new sequenceValueItem.
     *
     * @param sequenceValueItem the sequenceValueItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sequenceValueItem, or with status 400 (Bad Request) if the sequenceValueItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sequence-value-items")
    @Timed
    public ResponseEntity<SequenceValueItem> createSequenceValueItem(@RequestBody SequenceValueItem sequenceValueItem) throws URISyntaxException {
        log.debug("REST request to save SequenceValueItem : {}", sequenceValueItem);
        if (sequenceValueItem.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sequenceValueItem", "idexists", "A new sequenceValueItem cannot already have an ID")).body(null);
        }
        SequenceValueItem result = sequenceValueItemService.save(sequenceValueItem);
        return ResponseEntity.created(new URI("/api/sequence-value-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sequenceValueItem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sequence-value-items : Updates an existing sequenceValueItem.
     *
     * @param sequenceValueItem the sequenceValueItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sequenceValueItem,
     * or with status 400 (Bad Request) if the sequenceValueItem is not valid,
     * or with status 500 (Internal Server Error) if the sequenceValueItem couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sequence-value-items")
    @Timed
    public ResponseEntity<SequenceValueItem> updateSequenceValueItem(@RequestBody SequenceValueItem sequenceValueItem) throws URISyntaxException {
        log.debug("REST request to update SequenceValueItem : {}", sequenceValueItem);
        if (sequenceValueItem.getId() == null) {
            return createSequenceValueItem(sequenceValueItem);
        }
        SequenceValueItem result = sequenceValueItemService.save(sequenceValueItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sequenceValueItem", sequenceValueItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sequence-value-items : get all the sequenceValueItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sequenceValueItems in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/sequence-value-items")
    @Timed
    public ResponseEntity<List<SequenceValueItem>> getAllSequenceValueItems(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SequenceValueItems");
        Page<SequenceValueItem> page = sequenceValueItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sequence-value-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sequence-value-items/:id : get the "id" sequenceValueItem.
     *
     * @param id the id of the sequenceValueItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sequenceValueItem, or with status 404 (Not Found)
     */
    @GetMapping("/sequence-value-items/{id}")
    @Timed
    public ResponseEntity<SequenceValueItem> getSequenceValueItem(@PathVariable String id) {
        log.debug("REST request to get SequenceValueItem : {}", id);
        SequenceValueItem sequenceValueItem = sequenceValueItemService.findOne(id);
        return Optional.ofNullable(sequenceValueItem)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    /**
     * 获取实体下一个序列号/getNextSeqIdString
     * 
     *GET
     *
     * @param id the id of the sequenceValueItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sequenceValueItem, or with status 404 (Not Found)
     */
    @GetMapping("/getNextSeqIdLong/{id}")
    @Timed
    public SequenceValueItem getNextSeqIdLong(@PathVariable String id) {
    	log.debug("REST request to getNextSeqIdLong : {}", id);
    	Long seqId = sequenceValueItemService.getNextSeqIdLong(id);
    	SequenceValueItem seq = new SequenceValueItem();
    	seq.setSeqId(seqId);
    	return seq;
    }

    /**
     * DELETE  /sequence-value-items/:id : delete the "id" sequenceValueItem.
     *
     * @param id the id of the sequenceValueItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sequence-value-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteSequenceValueItem(@PathVariable String id) {
        log.debug("REST request to delete SequenceValueItem : {}", id);
        sequenceValueItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sequenceValueItem", id.toString())).build();
    }

    /**
     * SEARCH  /_search/sequence-value-items?query=:query : search for the sequenceValueItem corresponding
     * to the query.
     *
     * @param query the query of the sequenceValueItem search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/sequence-value-items")
    @Timed
    public ResponseEntity<List<SequenceValueItem>> searchSequenceValueItems(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of SequenceValueItems for query {}", query);
        Page<SequenceValueItem> page = sequenceValueItemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sequence-value-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
