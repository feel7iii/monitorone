package com.assoft.ssomonitor.repository.search;

import com.assoft.ssomonitor.domain.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Application entity.
 */
public interface ApplicationSearchRepository extends ElasticsearchRepository<Application, Long> {

}
