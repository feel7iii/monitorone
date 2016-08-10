package com.assoft.ssomonitor.web.rest;

import com.assoft.ssomonitor.MonitorApp;
import com.assoft.ssomonitor.domain.Party;
import com.assoft.ssomonitor.repository.PartyRepository;
import com.assoft.ssomonitor.repository.search.PartySearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PartyResource REST controller.
 *
 * @see PartyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MonitorApp.class)
@WebAppConfiguration
@IntegrationTest
public class PartyResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_PARENT_ID = "AAAAA";
    private static final String UPDATED_PARENT_ID = "BBBBB";
    private static final String DEFAULT_PATH = "AAAAA";
    private static final String UPDATED_PATH = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_UNIQUE_NAME = "AAAAA";
    private static final String UPDATED_UNIQUE_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final String DEFAULT_SECURITY_LEVEL = "AAAAA";
    private static final String UPDATED_SECURITY_LEVEL = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_TIME_STR = dateTimeFormatter.format(DEFAULT_CREATE_TIME);

    private static final ZonedDateTime DEFAULT_MODIFY_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_MODIFY_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_MODIFY_TIME_STR = dateTimeFormatter.format(DEFAULT_MODIFY_TIME);
    private static final String DEFAULT_MANAGE_BY = "AAAAA";
    private static final String UPDATED_MANAGE_BY = "BBBBB";
    private static final String DEFAULT_PARTY_ONE = "AAAAA";
    private static final String UPDATED_PARTY_ONE = "BBBBB";

    @Inject
    private PartyRepository partyRepository;

    @Inject
    private PartySearchRepository partySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPartyMockMvc;

    private Party party;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PartyResource partyResource = new PartyResource();
        ReflectionTestUtils.setField(partyResource, "partySearchRepository", partySearchRepository);
        ReflectionTestUtils.setField(partyResource, "partyRepository", partyRepository);
        this.restPartyMockMvc = MockMvcBuilders.standaloneSetup(partyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        partySearchRepository.deleteAll();
        party = new Party();
        party.setParentId(DEFAULT_PARENT_ID);
        party.setPath(DEFAULT_PATH);
        party.setName(DEFAULT_NAME);
        party.setUniqueName(DEFAULT_UNIQUE_NAME);
        party.setDescription(DEFAULT_DESCRIPTION);
        party.setPosition(DEFAULT_POSITION);
        party.setType(DEFAULT_TYPE);
        party.setStatus(DEFAULT_STATUS);
        party.setSecurityLevel(DEFAULT_SECURITY_LEVEL);
        party.setCreateTime(DEFAULT_CREATE_TIME);
        party.setModifyTime(DEFAULT_MODIFY_TIME);
        party.setManageBy(DEFAULT_MANAGE_BY);
        party.setPartyOne(DEFAULT_PARTY_ONE);
    }

    @Test
    @Transactional
    public void createParty() throws Exception {
        int databaseSizeBeforeCreate = partyRepository.findAll().size();

        // Create the Party

        restPartyMockMvc.perform(post("/api/parties")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(party)))
                .andExpect(status().isCreated());

        // Validate the Party in the database
        List<Party> parties = partyRepository.findAll();
        assertThat(parties).hasSize(databaseSizeBeforeCreate + 1);
        Party testParty = parties.get(parties.size() - 1);
        assertThat(testParty.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testParty.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testParty.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testParty.getUniqueName()).isEqualTo(DEFAULT_UNIQUE_NAME);
        assertThat(testParty.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testParty.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testParty.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testParty.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testParty.getSecurityLevel()).isEqualTo(DEFAULT_SECURITY_LEVEL);
        assertThat(testParty.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testParty.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
        assertThat(testParty.getManageBy()).isEqualTo(DEFAULT_MANAGE_BY);
        assertThat(testParty.getPartyOne()).isEqualTo(DEFAULT_PARTY_ONE);

        // Validate the Party in ElasticSearch
        Party partyEs = partySearchRepository.findOne(testParty.getId());
        assertThat(partyEs).isEqualToComparingFieldByField(testParty);
    }

    @Test
    @Transactional
    public void checkParentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = partyRepository.findAll().size();
        // set the field null
        party.setParentId(null);

        // Create the Party, which fails.

        restPartyMockMvc.perform(post("/api/parties")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(party)))
                .andExpect(status().isBadRequest());

        List<Party> parties = partyRepository.findAll();
        assertThat(parties).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = partyRepository.findAll().size();
        // set the field null
        party.setPath(null);

        // Create the Party, which fails.

        restPartyMockMvc.perform(post("/api/parties")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(party)))
                .andExpect(status().isBadRequest());

        List<Party> parties = partyRepository.findAll();
        assertThat(parties).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = partyRepository.findAll().size();
        // set the field null
        party.setName(null);

        // Create the Party, which fails.

        restPartyMockMvc.perform(post("/api/parties")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(party)))
                .andExpect(status().isBadRequest());

        List<Party> parties = partyRepository.findAll();
        assertThat(parties).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUniqueNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = partyRepository.findAll().size();
        // set the field null
        party.setUniqueName(null);

        // Create the Party, which fails.

        restPartyMockMvc.perform(post("/api/parties")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(party)))
                .andExpect(status().isBadRequest());

        List<Party> parties = partyRepository.findAll();
        assertThat(parties).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = partyRepository.findAll().size();
        // set the field null
        party.setPosition(null);

        // Create the Party, which fails.

        restPartyMockMvc.perform(post("/api/parties")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(party)))
                .andExpect(status().isBadRequest());

        List<Party> parties = partyRepository.findAll();
        assertThat(parties).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParties() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the parties
        restPartyMockMvc.perform(get("/api/parties?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(party.getId().intValue())))
                .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.toString())))
                .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].uniqueName").value(hasItem(DEFAULT_UNIQUE_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].securityLevel").value(hasItem(DEFAULT_SECURITY_LEVEL.toString())))
                .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME_STR)))
                .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(DEFAULT_MODIFY_TIME_STR)))
                .andExpect(jsonPath("$.[*].manageBy").value(hasItem(DEFAULT_MANAGE_BY.toString())))
                .andExpect(jsonPath("$.[*].partyOne").value(hasItem(DEFAULT_PARTY_ONE.toString())));
    }

    @Test
    @Transactional
    public void getParty() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get the party
        restPartyMockMvc.perform(get("/api/parties/{id}", party.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(party.getId().intValue()))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.toString()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.uniqueName").value(DEFAULT_UNIQUE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.securityLevel").value(DEFAULT_SECURITY_LEVEL.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME_STR))
            .andExpect(jsonPath("$.modifyTime").value(DEFAULT_MODIFY_TIME_STR))
            .andExpect(jsonPath("$.manageBy").value(DEFAULT_MANAGE_BY.toString()))
            .andExpect(jsonPath("$.partyOne").value(DEFAULT_PARTY_ONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParty() throws Exception {
        // Get the party
        restPartyMockMvc.perform(get("/api/parties/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParty() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);
        partySearchRepository.save(party);
        int databaseSizeBeforeUpdate = partyRepository.findAll().size();

        // Update the party
        Party updatedParty = new Party();
        updatedParty.setId(party.getId());
        updatedParty.setParentId(UPDATED_PARENT_ID);
        updatedParty.setPath(UPDATED_PATH);
        updatedParty.setName(UPDATED_NAME);
        updatedParty.setUniqueName(UPDATED_UNIQUE_NAME);
        updatedParty.setDescription(UPDATED_DESCRIPTION);
        updatedParty.setPosition(UPDATED_POSITION);
        updatedParty.setType(UPDATED_TYPE);
        updatedParty.setStatus(UPDATED_STATUS);
        updatedParty.setSecurityLevel(UPDATED_SECURITY_LEVEL);
        updatedParty.setCreateTime(UPDATED_CREATE_TIME);
        updatedParty.setModifyTime(UPDATED_MODIFY_TIME);
        updatedParty.setManageBy(UPDATED_MANAGE_BY);
        updatedParty.setPartyOne(UPDATED_PARTY_ONE);

        restPartyMockMvc.perform(put("/api/parties")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedParty)))
                .andExpect(status().isOk());

        // Validate the Party in the database
        List<Party> parties = partyRepository.findAll();
        assertThat(parties).hasSize(databaseSizeBeforeUpdate);
        Party testParty = parties.get(parties.size() - 1);
        assertThat(testParty.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testParty.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testParty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testParty.getUniqueName()).isEqualTo(UPDATED_UNIQUE_NAME);
        assertThat(testParty.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testParty.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testParty.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testParty.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testParty.getSecurityLevel()).isEqualTo(UPDATED_SECURITY_LEVEL);
        assertThat(testParty.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testParty.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
        assertThat(testParty.getManageBy()).isEqualTo(UPDATED_MANAGE_BY);
        assertThat(testParty.getPartyOne()).isEqualTo(UPDATED_PARTY_ONE);

        // Validate the Party in ElasticSearch
        Party partyEs = partySearchRepository.findOne(testParty.getId());
        assertThat(partyEs).isEqualToComparingFieldByField(testParty);
    }

    @Test
    @Transactional
    public void deleteParty() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);
        partySearchRepository.save(party);
        int databaseSizeBeforeDelete = partyRepository.findAll().size();

        // Get the party
        restPartyMockMvc.perform(delete("/api/parties/{id}", party.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean partyExistsInEs = partySearchRepository.exists(party.getId());
        assertThat(partyExistsInEs).isFalse();

        // Validate the database is empty
        List<Party> parties = partyRepository.findAll();
        assertThat(parties).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchParty() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);
        partySearchRepository.save(party);

        // Search the party
        restPartyMockMvc.perform(get("/api/_search/parties?query=id:" + party.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(party.getId().intValue())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.toString())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].uniqueName").value(hasItem(DEFAULT_UNIQUE_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].securityLevel").value(hasItem(DEFAULT_SECURITY_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME_STR)))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(DEFAULT_MODIFY_TIME_STR)))
            .andExpect(jsonPath("$.[*].manageBy").value(hasItem(DEFAULT_MANAGE_BY.toString())))
            .andExpect(jsonPath("$.[*].partyOne").value(hasItem(DEFAULT_PARTY_ONE.toString())));
    }
}
