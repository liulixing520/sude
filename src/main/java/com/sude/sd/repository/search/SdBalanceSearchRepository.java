package com.sude.sd.repository.search;

import com.sude.sd.domain.SdBalance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SdBalance entity.
 */
public interface SdBalanceSearchRepository extends ElasticsearchRepository<SdBalance, Long> {
}
