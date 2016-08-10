package com.assoft.ssomonitor.repository.search;

import com.assoft.ssomonitor.domain.Mmetadate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Mmetadate entity.
 */
public interface MmetadateSearchRepository extends ElasticsearchRepository<Mmetadate, Long> {
}
