package com.sude.sd.repository.search;

import com.sude.sd.domain.SdMaintenanceRecord;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SdMaintenanceRecord entity.
 */
public interface SdMaintenanceRecordSearchRepository extends ElasticsearchRepository<SdMaintenanceRecord, Long> {
}
