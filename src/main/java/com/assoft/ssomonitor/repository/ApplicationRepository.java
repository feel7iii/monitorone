package com.assoft.ssomonitor.repository;

import com.assoft.ssomonitor.domain.Application;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;

import java.util.List;

/**
 * Spring Data JPA repository for the Application entity.
 */
@SuppressWarnings("unused")
public interface ApplicationRepository extends PagingAndSortingRepository<Application,Long>, JpaSpecificationExecutor<Application> {

    @Query("select distinct application from Application application left join fetch application.mmetadates")
    List<Application> findAllWithEagerRelationships();

    @Query("select application from Application application left join fetch application.mmetadates where application.id =:id")
    Application findOneWithEagerRelationships(@Param("id") Long id);

    @Query(value = "select * from application where computer_id = :query", nativeQuery = true)
    List<Application> findByComputerId(@Param("query") String query);
}
