package com.assoft.ssomonitor.repository;

import com.assoft.ssomonitor.domain.Party;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Party entity.
 */
@SuppressWarnings("unused")
public interface PartyRepository extends JpaRepository<Party,Long> {

}
