package com.sude.sd.repository.search;

import com.sude.sd.domain.Enumeration;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Enumeration entity.
 */
public interface EnumerationSearchRepository extends ElasticsearchRepository<Enumeration, String> {
}
