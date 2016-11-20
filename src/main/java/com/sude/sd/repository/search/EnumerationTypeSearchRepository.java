package com.sude.sd.repository.search;

import com.sude.sd.domain.EnumerationType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the EnumerationType entity.
 */
public interface EnumerationTypeSearchRepository extends ElasticsearchRepository<EnumerationType, String> {
}
