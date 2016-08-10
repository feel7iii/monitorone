package com.assoft.ssomonitor.repository.search;

import com.assoft.ssomonitor.domain.Computer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Computer entity.
 */
public interface ComputerSearchRepository extends ElasticsearchRepository<Computer, Long> {
}
