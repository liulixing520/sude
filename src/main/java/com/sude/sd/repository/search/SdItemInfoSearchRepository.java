package com.sude.sd.repository.search;

import com.sude.sd.domain.SdItemInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SdItemInfo entity.
 */
public interface SdItemInfoSearchRepository extends ElasticsearchRepository<SdItemInfo, Long> {
}
