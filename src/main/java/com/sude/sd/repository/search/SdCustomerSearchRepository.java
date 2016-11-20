package com.sude.sd.repository.search;

import com.sude.sd.domain.SdCustomer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SdCustomer entity.
 */
public interface SdCustomerSearchRepository extends ElasticsearchRepository<SdCustomer, Long> {
}
