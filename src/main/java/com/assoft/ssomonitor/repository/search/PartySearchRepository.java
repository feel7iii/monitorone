package com.assoft.ssomonitor.repository.search;

import com.assoft.ssomonitor.domain.Party;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Party entity.
 */
public interface PartySearchRepository extends ElasticsearchRepository<Party, Long> {
}
