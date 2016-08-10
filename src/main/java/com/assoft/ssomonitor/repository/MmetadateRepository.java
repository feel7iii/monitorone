package com.assoft.ssomonitor.repository;

import com.assoft.ssomonitor.domain.Mmetadate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Mmetadate entity.
 */
@SuppressWarnings("unused")
public interface MmetadateRepository extends JpaRepository<Mmetadate,Long> {

}
