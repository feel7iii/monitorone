package com.assoft.ssomonitor.web.rest;

import com.assoft.ssomonitor.domain.Party;
import com.assoft.ssomonitor.domain.User;
import com.assoft.ssomonitor.repository.PartyRepository;
import com.assoft.ssomonitor.service.UserService;
import com.codahale.metrics.annotation.Timed;
import com.assoft.ssomonitor.domain.Middleware;
import com.assoft.ssomonitor.repository.MiddlewareRepository;
import com.assoft.ssomonitor.repository.search.MiddlewareSearchRepository;
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
 * REST controller for managing Middleware.
 */
@RestController
@RequestMapping("/api")
public class MiddlewareResource {

    private final Logger log = LoggerFactory.getLogger(MiddlewareResource.class);

    @Inject
    private MiddlewareRepository middlewareRepository;

    @Inject
    private MiddlewareSearchRepository middlewareSearchRepository;

    @Inject
    private PartyRepository partyRepository;

    @Inject
    private UserService userService;

    /**
     * POST  /middlewares : Create a new middleware.
     *
     * @param middleware the middleware to create
     * @return the ResponseEntity with status 201 (Created) and with body the new middleware, or with status 400 (Bad Request) if the middleware has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/middlewares",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Middleware> createMiddleware(@Valid @RequestBody Middleware middleware) throws URISyntaxException {
        log.debug("REST request to save Middleware : {}", middleware);
        if (middleware.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("middleware", "idexists", "A new middleware cannot already have an ID")).body(null);
        }
        Middleware result = middlewareRepository.save(middleware);
        middlewareSearchRepository.save(result);
        Party party = new Party();
        party.setParentId(middleware.getComputer().getId().toString());
        party.setName(result.getMidname());
        party.setPath("NOTHING");
        party.setUniqueName(result.getMidname());
        party.setPosition(1);
        party.setManageBy(userService.getUserWithAuthorities().getLogin());
        partyRepository.save(party);
        return ResponseEntity.created(new URI("/api/middlewares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("middleware", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /middlewares : Updates an existing middleware.
     *
     * @param middleware the middleware to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated middleware,
     * or with status 400 (Bad Request) if the middleware is not valid,
     * or with status 500 (Internal Server Error) if the middleware couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/middlewares",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Middleware> updateMiddleware(@Valid @RequestBody Middleware middleware) throws URISyntaxException {
        log.debug("REST request to update Middleware : {}", middleware);
        if (middleware.getId() == null) {
            return createMiddleware(middleware);
        }
        Middleware result = middlewareRepository.save(middleware);
        middlewareSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("middleware", middleware.getId().toString()))
            .body(result);
    }

    /**
     * GET  /middlewares : get all the middlewares.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of middlewares in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/middlewares",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Middleware>> getAllMiddlewares(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Middlewares");
        Page<Middleware> page = middlewareRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/middlewares");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /middlewares/:id : get the "id" middleware.
     *
     * @param id the id of the middleware to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the middleware, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/middlewares/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Middleware> getMiddleware(@PathVariable Long id) {
        log.debug("REST request to get Middleware : {}", id);
        Middleware middleware = middlewareRepository.findOne(id);
        return Optional.ofNullable(middleware)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /middlewares/:id : delete the "id" middleware.
     *
     * @param id the id of the middleware to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/middlewares/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMiddleware(@PathVariable Long id) {
        log.debug("REST request to delete Middleware : {}", id);
        middlewareRepository.delete(id);
        middlewareSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("middleware", id.toString())).build();
    }

    /**
     * SEARCH  /_search/middlewares?query=:query : search for the middleware corresponding
     * to the query.
     *
     * @param query the query of the middleware search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/middlewares",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Middleware>> searchMiddlewares(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Middlewares for query {}", query);
        Page<Middleware> page = middlewareSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/middlewares");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "_countSearch/middlewares",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Middleware> searchMiddlewares(@RequestParam String query) {

        log.info("REST request to search Computer's Middleware {}", query);
        return StreamSupport.stream(middlewareRepository.findByComputerId(query).spliterator(), false)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/_countSearch/middnum",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Long countApplications() throws URISyntaxException {
        log.debug("查询的中间件数量为-> {}", middlewareRepository.count());
        return middlewareRepository.count();
    }

}
