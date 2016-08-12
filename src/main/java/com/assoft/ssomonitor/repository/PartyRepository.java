package com.assoft.ssomonitor.repository;

import com.assoft.ssomonitor.domain.Party;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Party entity.
 */
@SuppressWarnings("unused")
public interface PartyRepository extends JpaRepository<Party,Long> {

    Party getByPartyOne(String partyOneId);

    @Query(value = "select p.id, p.parent_id, p.name from Party p", nativeQuery = true)
    List<Party> getAllParty();

}
