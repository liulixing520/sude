package com.sude.sd.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.sude.sd.domain.SdCarInfo;

/**
 * Spring Data ElasticSearch repository for the SdCarInfo entity.
 */
public interface SdCarInfoSearchRepository extends ElasticsearchRepository<SdCarInfo, String> {
}
