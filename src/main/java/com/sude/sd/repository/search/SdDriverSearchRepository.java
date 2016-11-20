package com.sude.sd.repository.search;

import com.sude.sd.domain.SdDriver;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SdDriver entity.
 */
public interface SdDriverSearchRepository extends ElasticsearchRepository<SdDriver, Long> {
}
