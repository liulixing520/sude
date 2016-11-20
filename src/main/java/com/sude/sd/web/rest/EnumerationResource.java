package com.sude.sd.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sude.sd.domain.Enumeration;
import com.sude.sd.service.EnumerationService;
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
 * REST controller for managing Enumeration.
 */
@RestController
@RequestMapping("/api")
public class EnumerationResource {

    private final Logger log = LoggerFactory.getLogger(EnumerationResource.class);
        
    @Inject
    private EnumerationService enumerationService;

    /**
     * POST  /enumerations : Create a new enumeration.
     *
     * @param enumeration the enumeration to create
     * @return the ResponseEntity with status 201 (Created) and with body the new enumeration, or with status 400 (Bad Request) if the enumeration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/enumerations")
    @Timed
    public ResponseEntity<Enumeration> createEnumeration(@RequestBody Enumeration enumeration) throws URISyntaxException {
        log.debug("REST request to save Enumeration : {}", enumeration);
//        if (enumeration.getId() != null) {
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("enumeration", "idexists", "A new enumeration cannot already have an ID")).body(null);
//        }
        Enumeration result = enumerationService.save(enumeration);
        return ResponseEntity.created(new URI("/api/enumerations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("enumeration", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /enumerations : Updates an existing enumeration.
     *
     * @param enumeration the enumeration to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated enumeration,
     * or with status 400 (Bad Request) if the enumeration is not valid,
     * or with status 500 (Internal Server Error) if the enumeration couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/enumerations")
    @Timed
    public ResponseEntity<Enumeration> updateEnumeration(@RequestBody Enumeration enumeration) throws URISyntaxException {
        log.debug("REST request to update Enumeration : {}", enumeration);
        if (enumeration.getId() == null) {
            return createEnumeration(enumeration);
        }
        Enumeration result = enumerationService.save(enumeration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("enumeration", enumeration.getId().toString()))
            .body(result);
    }

    /**
     * GET  /enumerations : get all the enumerations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of enumerations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/enumerations")
    @Timed
    public ResponseEntity<List<Enumeration>> getAllEnumerations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Enumerations");
        Page<Enumeration> page = enumerationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/enumerations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /enumerations/:id : get the "id" enumeration.
     *
     * @param id the id of the enumeration to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the enumeration, or with status 404 (Not Found)
     */
    @GetMapping("/enumerations/{id}")
    @Timed
    public ResponseEntity<Enumeration> getEnumeration(@PathVariable String id) {
        log.debug("REST request to get Enumeration : {}", id);
        Enumeration enumeration = enumerationService.findOne(id);
        return Optional.ofNullable(enumeration)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /enumerations/:id : delete the "id" enumeration.
     *
     * @param id the id of the enumeration to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/enumerations/{id}")
    @Timed
    public ResponseEntity<Void> deleteEnumeration(@PathVariable String id) {
        log.debug("REST request to delete Enumeration : {}", id);
        enumerationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("enumeration", id.toString())).build();
    }

    /**
     * SEARCH  /_search/enumerations?query=:query : search for the enumeration corresponding
     * to the query.
     *
     * @param query the query of the enumeration search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/enumerations")
    @Timed
    public ResponseEntity<List<Enumeration>> searchEnumerations(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Enumerations for query {}", query);
        Page<Enumeration> page = enumerationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/enumerations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
