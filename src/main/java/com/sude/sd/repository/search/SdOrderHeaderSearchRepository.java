package com.sude.sd.repository.search;

import com.sude.sd.domain.SdOrderHeader;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SdOrderHeader entity.
 */
public interface SdOrderHeaderSearchRepository extends ElasticsearchRepository<SdOrderHeader, Long> {
}
