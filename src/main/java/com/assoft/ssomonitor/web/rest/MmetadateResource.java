package com.assoft.ssomonitor.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.assoft.ssomonitor.domain.Mmetadate;
import com.assoft.ssomonitor.repository.MmetadateRepository;
import com.assoft.ssomonitor.repository.search.MmetadateSearchRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Mmetadate.
 */
@RestController
@RequestMapping("/api")
public class MmetadateResource {

    private final Logger log = LoggerFactory.getLogger(MmetadateResource.class);
        
    @Inject
    private MmetadateRepository mmetadateRepository;
    
    @Inject
    private MmetadateSearchRepository mmetadateSearchRepository;
    
    /**
     * POST  /mmetadates : Create a new mmetadate.
     *
     * @param mmetadate the mmetadate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mmetadate, or with status 400 (Bad Request) if the mmetadate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/mmetadates",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Mmetadate> createMmetadate(@RequestBody Mmetadate mmetadate) throws URISyntaxException {
        log.debug("REST request to save Mmetadate : {}", mmetadate);
        if (mmetadate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("mmetadate", "idexists", "A new mmetadate cannot already have an ID")).body(null);
        }
        Mmetadate result = mmetadateRepository.save(mmetadate);
        mmetadateSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/mmetadates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("mmetadate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mmetadates : Updates an existing mmetadate.
     *
     * @param mmetadate the mmetadate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mmetadate,
     * or with status 400 (Bad Request) if the mmetadate is not valid,
     * or with status 500 (Internal Server Error) if the mmetadate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/mmetadates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Mmetadate> updateMmetadate(@RequestBody Mmetadate mmetadate) throws URISyntaxException {
        log.debug("REST request to update Mmetadate : {}", mmetadate);
        if (mmetadate.getId() == null) {
            return createMmetadate(mmetadate);
        }
        Mmetadate result = mmetadateRepository.save(mmetadate);
        mmetadateSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("mmetadate", mmetadate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mmetadates : get all the mmetadates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mmetadates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/mmetadates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Mmetadate>> getAllMmetadates(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Mmetadates");
        Page<Mmetadate> page = mmetadateRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mmetadates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mmetadates/:id : get the "id" mmetadate.
     *
     * @param id the id of the mmetadate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mmetadate, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/mmetadates/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Mmetadate> getMmetadate(@PathVariable Long id) {
        log.debug("REST request to get Mmetadate : {}", id);
        Mmetadate mmetadate = mmetadateRepository.findOne(id);
        return Optional.ofNullable(mmetadate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /mmetadates/:id : delete the "id" mmetadate.
     *
     * @param id the id of the mmetadate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/mmetadates/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMmetadate(@PathVariable Long id) {
        log.debug("REST request to delete Mmetadate : {}", id);
        mmetadateRepository.delete(id);
        mmetadateSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("mmetadate", id.toString())).build();
    }

    /**
     * SEARCH  /_search/mmetadates?query=:query : search for the mmetadate corresponding
     * to the query.
     *
     * @param query the query of the mmetadate search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/mmetadates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Mmetadate>> searchMmetadates(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Mmetadates for query {}", query);
        Page<Mmetadate> page = mmetadateSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/mmetadates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
