package com.assoft.ssomonitor.web.rest;

import com.assoft.ssomonitor.domain.Application;
import com.assoft.ssomonitor.domain.Party;
import com.assoft.ssomonitor.repository.PartyRepository;
import com.assoft.ssomonitor.security.SecurityUtils;
import com.assoft.ssomonitor.service.UserService;
import com.codahale.metrics.annotation.Timed;
import com.assoft.ssomonitor.domain.Computer;
import com.assoft.ssomonitor.repository.ComputerRepository;
import com.assoft.ssomonitor.repository.search.ComputerSearchRepository;
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
import org.springframework.security.core.userdetails.UserDetails;
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
 * REST controller for managing Computer.
 */
@RestController
@RequestMapping("/api")
public class ComputerResource {

    private final Logger log = LoggerFactory.getLogger(ComputerResource.class);

    @Inject
    private ComputerRepository computerRepository;

    @Inject
    private ComputerSearchRepository computerSearchRepository;

    @Inject
    private PartyRepository partyRepository;

    @Inject
    private UserService userService;

    /**
     * POST  /computers : Create a new computer.
     *
     * @param computer the computer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new computer, or with status 400 (Bad Request) if the computer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/computers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Computer> createComputer(@Valid @RequestBody Computer computer) throws URISyntaxException {
        log.debug("REST request to save Computer : {}", computer);
        if (computer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("computer", "idexists", "A new computer cannot already have an ID")).body(null);
        }
        Computer result = computerRepository.save(computer);
        computerSearchRepository.save(result);
        Party party = new Party();
        party.setParentId("1");
        party.setName(result.getCpname());
        party.setPath("NOTHING");
        party.setUniqueName(result.getCpname());
        party.setPosition(1);
        party.setManageBy(userService.getUserWithAuthorities().getLogin());
        party.setPartyOne("cp" + result.getId().toString());
        partyRepository.save(party);
        return ResponseEntity.created(new URI("/api/computers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("computer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /computers : Updates an existing computer.
     *
     * @param computer the computer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated computer,
     * or with status 400 (Bad Request) if the computer is not valid,
     * or with status 500 (Internal Server Error) if the computer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/computers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Computer> updateComputer(@Valid @RequestBody Computer computer) throws URISyntaxException {
        log.debug("REST request to update Computer : {}", computer);
        if (computer.getId() == null) {
            return createComputer(computer);
        }
        Computer result = computerRepository.save(computer);
        computerSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("computer", computer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /computers : get all the computers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of computers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/computers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Computer>> getAllComputers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Computers");
        Page<Computer> page = computerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/computers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /computers/:id : get the "id" computer.
     *
     * @param id the id of the computer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the computer, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/computers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Computer> getComputer(@PathVariable Long id) {
        log.debug("REST request to get Computer : {}", id);
        Computer computer = computerRepository.findOne(id);
        return Optional.ofNullable(computer)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /computers/:id : delete the "id" computer.
     *
     * @param id the id of the computer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/computers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteComputer(@PathVariable Long id) {
        log.debug("REST request to delete Computer : {}", id);
        computerRepository.delete(id);
        computerSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("computer", id.toString())).build();
    }

    /**
     * SEARCH  /_search/computers?query=:query : search for the computer corresponding
     * to the query.
     *
     * @param query the query of the computer search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/computers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Computer>> searchComputers(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Computers for query {}", query);
        Page<Computer> page = computerSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/computers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/_countSearch/computers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Long countApplications() throws URISyntaxException {
        log.debug("查询的服务器数量为-> {}", computerRepository.count());
        return computerRepository.count();
    }

}
