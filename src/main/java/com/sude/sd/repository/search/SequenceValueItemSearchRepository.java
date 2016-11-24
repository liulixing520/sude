package com.sude.sd.repository.search;

import com.sude.sd.domain.SequenceValueItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SequenceValueItem entity.
 */
public interface SequenceValueItemSearchRepository extends ElasticsearchRepository<SequenceValueItem, String> {
}
