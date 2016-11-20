package com.sude.sd.repository.search;

import com.sude.sd.domain.SdCarInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SdCarInfo entity.
 */
public interface SdCarInfoSearchRepository extends ElasticsearchRepository<SdCarInfo, Long> {
}
