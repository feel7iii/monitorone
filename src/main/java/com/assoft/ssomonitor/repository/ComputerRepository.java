package com.assoft.ssomonitor.repository;

import com.assoft.ssomonitor.domain.Computer;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Computer entity.
 */
@SuppressWarnings("unused")
public interface ComputerRepository extends JpaRepository<Computer,Long> {

}
