package com.sude.sd.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sude.sd.domain.SdCustomer;
import com.sude.sd.domain.SdOrderItem;
import com.sude.sd.repository.SdCustomerRepository;
import com.sude.sd.repository.search.SdCustomerSearchRepository;

/**
 * Service Implementation for managing SdCustomer.
 */
@Service
@Transactional
public class SdCustomerService {

	private final Logger log = LoggerFactory.getLogger(SdCustomerService.class);

	@Inject
	private SdCustomerRepository sdCustomerRepository;

	@Inject
	private SdCustomerSearchRepository sdCustomerSearchRepository;

	/**
	 * Save a sdCustomer.
	 *
	 * @param sdCustomer
	 *            the entity to save
	 * @return the persisted entity
	 */
	public SdCustomer save(SdCustomer sdCustomer) {
		log.debug("Request to save SdCustomer : {}", sdCustomer);
		SdCustomer result = sdCustomerRepository.save(sdCustomer);
		sdCustomerSearchRepository.save(result);
		return result;
	}

	/**
	 * Get all the sdCustomers.
	 * 
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<SdCustomer> findAll(Pageable pageable) {
		log.debug("Request to get all SdCustomers");
		Page<SdCustomer> result = sdCustomerRepository.findAll(pageable);
		return result;
	}

	/**
	 * Get all the sdCustomers.
	 * 
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public List<SdCustomer> findAll() {
		log.debug("Request to get all SdCustomers");
		List<SdCustomer> result = sdCustomerRepository.findAll();
		return result;
	}

	/**
	 * Get one sdCustomer by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public SdCustomer findOne(Long id) {
		log.debug("Request to get SdCustomer : {}", id);
		SdCustomer sdCustomer = sdCustomerRepository.findOne(id);
		return sdCustomer;
	}

	/**
	 * Delete the sdCustomer by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	public void delete(Long id) {
		log.debug("Request to delete SdCustomer : {}", id);
		sdCustomerRepository.delete(id);
		sdCustomerSearchRepository.delete(id);
	}

	/**
	 * Search for the sdCustomer corresponding to the query.
	 *
	 * @param query
	 *            the query of the search
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<SdCustomer> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of SdCustomers for query {}", query);
		Page<SdCustomer> result = sdCustomerSearchRepository.search(queryStringQuery(query), pageable);
		return result;
	}

	/**
	 * Search for the sdCustomer corresponding to the query.
	 *
	 * @param query
	 *            the query of the search
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public List<SdCustomer> search(String query) {
		log.debug("Request to search for a page of SdCustomers for query {}", query);
		Iterable<SdCustomer> iter = sdCustomerSearchRepository.search(queryStringQuery(query));
		Iterator<SdCustomer> iterator = iter.iterator();
		List result = new ArrayList();
		for (;iterator.hasNext();) {
			Object element = (Object) iterator.next();
			result.add(element);
		}
		return result;
	}
	
	/**
	 * 检测是否有已存在同名、同手机号客户/如果不存在则新增
	 * 
	 * @param SdOrderItem
	 * @return the list of entities
	 */
	public SdOrderItem checkHasCustomer(SdOrderItem sdOrderItem) {
		log.debug("Request to get findByCustomerNameAndMobilePhone");
		String consignerName = sdOrderItem.getConsignerName();
	    Long consignerMbPhone = sdOrderItem.getConsignerMbPhone();
	    String consignerAddress = sdOrderItem.getConsignerAddress();
	    String consignerPhone = sdOrderItem.getConsignerPhone();
		List<SdCustomer> result = sdCustomerRepository.findByCustomerNameAndMobilePhone(consignerName,consignerMbPhone);
		if(result == null || result.size() == 0){
			SdCustomer sdCustomer = new SdCustomer();
			sdCustomer.setCustomerName(consignerName);
			sdCustomer.setMobilePhone(consignerMbPhone);
			sdCustomer.setPhone(consignerPhone);
			sdCustomer.setAddress(consignerAddress);
			sdCustomer = save(sdCustomer);
			sdOrderItem.setConsignerId(sdCustomer.getId()+"");
		}
		String consigneeName = sdOrderItem.getConsigneeName();
		Long consigneeMbPhone = sdOrderItem.getConsigneeMbPhone();
		String consigneeAddress = sdOrderItem.getConsigneeAddress();
		String consigneePhone = sdOrderItem.getConsigneePhone();
		List<SdCustomer> result2 = sdCustomerRepository.findByCustomerNameAndMobilePhone(consigneeName,consigneeMbPhone);
		if(result2 == null || result2.size() == 0 ){
			SdCustomer sdCustomer = new SdCustomer();
			sdCustomer.setCustomerName(consigneeName);
			sdCustomer.setMobilePhone(consigneeMbPhone);
			sdCustomer.setPhone(consigneePhone);
			sdCustomer.setAddress(consigneeAddress);
			save(sdCustomer);
			sdOrderItem.setConsigneeId(sdCustomer.getId()+"");
		}
		return sdOrderItem;
	}
}
