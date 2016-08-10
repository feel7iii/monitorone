package com.assoft.ssomonitor.repository.search;

import com.assoft.ssomonitor.domain.Cserver;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Cserver entity.
 */
public interface CserverSearchRepository extends ElasticsearchRepository<Cserver, Long> {
}
