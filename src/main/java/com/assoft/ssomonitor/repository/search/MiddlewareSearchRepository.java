package com.assoft.ssomonitor.repository.search;

import com.assoft.ssomonitor.domain.Middleware;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Middleware entity.
 */
public interface MiddlewareSearchRepository extends ElasticsearchRepository<Middleware, Long> {
}
