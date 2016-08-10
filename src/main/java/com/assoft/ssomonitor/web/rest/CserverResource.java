package com.assoft.ssomonitor.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.assoft.ssomonitor.domain.Cserver;
import com.assoft.ssomonitor.repository.CserverRepository;
import com.assoft.ssomonitor.repository.search.CserverSearchRepository;
import com.assoft.ssomonitor.web.rest.util.HeaderUtil;
import com.assoft.ssomonitor.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Cserver.
 */
@RestController
@RequestMapping("/api")
public class CserverResource {

    private final Logger log = LoggerFactory.getLogger(CserverResource.class);

    @Inject
    private CserverRepository cserverRepository;

    @Inject
    private CserverSearchRepository cserverSearchRepository;

    /**
     * POST  /cservers : Create a new cserver.
     *
     * @param cserver the cserver to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cserver, or with status 400 (Bad Request) if the cserver has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cservers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cserver> createCserver(@Valid @RequestBody Cserver cserver) throws URISyntaxException {
        log.debug("REST request to save Cserver : {}", cserver);
        if (cserver.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cserver", "idexists", "A new cserver cannot already have an ID")).body(null);
        }
        Cserver result = cserverRepository.save(cserver);
        cserverSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/cservers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cserver", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cservers : Updates an existing cserver.
     *
     * @param cserver the cserver to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cserver,
     * or with status 400 (Bad Request) if the cserver is not valid,
     * or with status 500 (Internal Server Error) if the cserver couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cservers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cserver> updateCserver(@Valid @RequestBody Cserver cserver) throws URISyntaxException {
        log.debug("REST request to update Cserver : {}", cserver);
        if (cserver.getId() == null) {
            return createCserver(cserver);
        }
        Cserver result = cserverRepository.save(cserver);
        cserverSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cserver", cserver.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cservers : get all the cservers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cservers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/cservers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Cserver>> getAllCservers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Cservers");
        Page<Cserver> page = cserverRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cservers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cservers/:id : get the "id" cserver.
     *
     * @param id the id of the cserver to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cserver, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/cservers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cserver> getCserver(@PathVariable Long id) {
        log.debug("REST request to get Cserver : {}", id);
        Cserver cserver = cserverRepository.findOne(id);
        return Optional.ofNullable(cserver)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cservers/:id : delete the "id" cserver.
     *
     * @param id the id of the cserver to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/cservers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCserver(@PathVariable Long id) {
        log.debug("REST request to delete Cserver : {}", id);
        cserverRepository.delete(id);
        cserverSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cserver", id.toString())).build();
    }

    /**
     * SEARCH  /_search/cservers?query=:query : search for the cserver corresponding
     * to the query.
     *
     * @param query the query of the cserver search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/cservers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Cserver>> searchCservers(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Cservers for query {}", query);
        Page<Cserver> page = cserverSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/cservers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/_countSearch/cservers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Cserver> countCservers(@RequestParam String query) {
        log.debug("REST request to search for Computer's Apps {}", query);
        return StreamSupport.stream(cserverRepository.findByComputerId(query).spliterator(), false)
                .collect(Collectors.toList());
    }

}
