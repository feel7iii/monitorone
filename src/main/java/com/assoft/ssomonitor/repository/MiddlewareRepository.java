package com.assoft.ssomonitor.repository;

import com.assoft.ssomonitor.domain.Middleware;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Middleware entity.
 */
@SuppressWarnings("unused")
public interface MiddlewareRepository extends JpaRepository<Middleware,Long> {

    @Query(value = "select * from middleware where computer_id = :query", nativeQuery = true)
    List<Middleware> findByComputerId(@Param("query") String query);
}
