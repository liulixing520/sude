package com.sude.sd.repository.search;

import com.sude.sd.domain.SdOrderItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SdOrderItem entity.
 */
public interface SdOrderItemSearchRepository extends ElasticsearchRepository<SdOrderItem, String> {
}
