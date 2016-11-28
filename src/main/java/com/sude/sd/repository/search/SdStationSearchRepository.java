package com.sude.sd.repository.search;

import com.sude.sd.domain.SdStation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SdStation entity.
 */
public interface SdStationSearchRepository extends ElasticsearchRepository<SdStation, Long> {
}
