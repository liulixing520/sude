package com.sude.sd.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sude.sd.domain.EnumerationType;
import com.sude.sd.service.EnumerationTypeService;
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
 * REST controller for managing EnumerationType.
 */
@RestController
@RequestMapping("/api")
public class EnumerationTypeResource {

    private final Logger log = LoggerFactory.getLogger(EnumerationTypeResource.class);
        
    @Inject
    private EnumerationTypeService enumerationTypeService;

    /**
     * POST  /enumeration-types : Create a new enumerationType.
     *
     * @param enumerationType the enumerationType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new enumerationType, or with status 400 (Bad Request) if the enumerationType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/enumeration-types")
    @Timed
    public ResponseEntity<EnumerationType> createEnumerationType(@RequestBody EnumerationType enumerationType) throws URISyntaxException {
        log.debug("REST request to save EnumerationType : {}", enumerationType);
        if (enumerationType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("enumerationType", "idexists", "A new enumerationType cannot already have an ID")).body(null);
        }
        EnumerationType result = enumerationTypeService.save(enumerationType);
        return ResponseEntity.created(new URI("/api/enumeration-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("enumerationType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /enumeration-types : Updates an existing enumerationType.
     *
     * @param enumerationType the enumerationType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated enumerationType,
     * or with status 400 (Bad Request) if the enumerationType is not valid,
     * or with status 500 (Internal Server Error) if the enumerationType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/enumeration-types")
    @Timed
    public ResponseEntity<EnumerationType> updateEnumerationType(@RequestBody EnumerationType enumerationType) throws URISyntaxException {
        log.debug("REST request to update EnumerationType : {}", enumerationType);
        if (enumerationType.getId() == null) {
            return createEnumerationType(enumerationType);
        }
        EnumerationType result = enumerationTypeService.save(enumerationType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("enumerationType", enumerationType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /enumeration-types : get all the enumerationTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of enumerationTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/enumeration-types")
    @Timed
    public ResponseEntity<List<EnumerationType>> getAllEnumerationTypes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of EnumerationTypes");
        Page<EnumerationType> page = enumerationTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/enumeration-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /enumeration-types/:id : get the "id" enumerationType.
     *
     * @param id the id of the enumerationType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the enumerationType, or with status 404 (Not Found)
     */
    @GetMapping("/enumeration-types/{id}")
    @Timed
    public ResponseEntity<EnumerationType> getEnumerationType(@PathVariable String id) {
        log.debug("REST request to get EnumerationType : {}", id);
        EnumerationType enumerationType = enumerationTypeService.findOne(id);
        return Optional.ofNullable(enumerationType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /enumeration-types/:id : delete the "id" enumerationType.
     *
     * @param id the id of the enumerationType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/enumeration-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteEnumerationType(@PathVariable String id) {
        log.debug("REST request to delete EnumerationType : {}", id);
        enumerationTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("enumerationType", id.toString())).build();
    }

    /**
     * SEARCH  /_search/enumeration-types?query=:query : search for the enumerationType corresponding
     * to the query.
     *
     * @param query the query of the enumerationType search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/enumeration-types")
    @Timed
    public ResponseEntity<List<EnumerationType>> searchEnumerationTypes(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of EnumerationTypes for query {}", query);
        Page<EnumerationType> page = enumerationTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/enumeration-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
