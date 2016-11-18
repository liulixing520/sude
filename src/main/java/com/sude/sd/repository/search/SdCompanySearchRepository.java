package com.sude.sd.repository.search;

import com.sude.sd.domain.SdCompany;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SdCompany entity.
 */
public interface SdCompanySearchRepository extends ElasticsearchRepository<SdCompany, Long> {
}
