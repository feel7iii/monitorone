package com.assoft.ssomonitor.repository;

import com.assoft.ssomonitor.domain.Cserver;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Cserver entity.
 */
@SuppressWarnings("unused")
public interface CserverRepository extends JpaRepository<Cserver,Long> {

    @Query(value = "select * from cserver where computer_id = :query", nativeQuery = true)
    List<Cserver> findByComputerId(@Param("query") String query);

}
