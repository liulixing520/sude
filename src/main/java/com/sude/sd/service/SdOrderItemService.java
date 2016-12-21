package com.sude.sd.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sude.sd.domain.Enumeration;
import com.sude.sd.domain.SdBalance;
import com.sude.sd.domain.SdOrderItem;
import com.sude.sd.repository.SdOrderItemRepository;
import com.sude.sd.repository.search.SdOrderItemSearchRepository;
import com.sude.sd.security.SecurityUtils;

/**
 * Service Implementation for managing SdOrderItem.
 */
@Service
@Transactional
public class SdOrderItemService {

    private final Logger log = LoggerFactory.getLogger(SdOrderItemService.class);
    
    @Inject
    private SdOrderItemRepository sdOrderItemRepository;

    @Inject
    private SdOrderItemSearchRepository sdOrderItemSearchRepository;
    
    @Inject
    private SdBalanceService sdBalanceService;

    /**
     * Save a sdOrderItem.
     *
     * @param sdOrderItem the entity to save
     * @return the persisted entity
     */
    public SdOrderItem save(SdOrderItem sdOrderItem) {
        log.debug("Request to save SdOrderItem : {}", sdOrderItem);
        SdOrderItem result = sdOrderItemRepository.save(sdOrderItem);
        sdOrderItemSearchRepository.save(result);
        //入账
        List<SdBalance> list = sdBalanceService.findByOrderNo(result.getId()+"");
        SdBalance sdBalance = new SdBalance();
        if(list.size() > 0 ){
        	sdBalance = list.get(0);
        }
        sdBalance.setOrderNo(result.getId()+"");
        Enumeration enums = new Enumeration();
        //收入
        enums.setId("IN_OUT_TYPE_1");
        sdBalance.setInOutType(enums);
        if(result.getPayType()!=null && !"".equals(result.getPayType())){
        	BigDecimal total = result.getTotalFreight();
        	Enumeration enums2 = new Enumeration();
        	String summary = "";
        	switch (result.getPayType()) {
			case "现付":
				enums2.setId("PAYTYPE_1");
				sdBalance.setMoney(result.getCashPay());
				BigDecimal pay =  result.getCashPay();
				if(pay!= null && !"".equals(pay)){
					if(total.compareTo(pay) > 0 ){
						summary = "未收款:"+total.subtract(pay);
					}else if(total.compareTo(pay) < 0 ){
						summary = "多收款:"+pay.subtract(total);
					}
				}
				break;
			case "提付":
				enums2.setId("PAYTYPE_2");
				sdBalance.setMoney(result.getFetchPay());
				pay =  result.getFetchPay();
				if(pay!= null && !"".equals(pay)){
					if(total.compareTo(pay) > 0 ){
						summary = "未收款:"+total.subtract(pay);
					}else if(total.compareTo(pay) < 0 ){
						summary = "多收款:"+pay.subtract(total);
					}
				}
				break;
			case "回单付":
				enums2.setId("PAYTYPE_3");
				sdBalance.setMoney(result.getReceiptPay());
				pay =  result.getReceiptPay();
				if(pay!= null && !"".equals(pay)){
					if(total.compareTo(pay) > 0 ){
						summary = "未收款:"+total.subtract(pay);
					}else if(total.compareTo(pay) < 0 ){
						summary = "多收款:"+pay.subtract(total);
					}
				}
				break;
			case "月结":
				enums2.setId("PAYTYPE_4");
				sdBalance.setMoney(result.getMonthPay());
				pay =  result.getMonthPay();
				if(pay!= null && !"".equals(pay)){
					if(total.compareTo(pay) > 0 ){
						summary = "未收款:"+total.subtract(pay);
					}else if(total.compareTo(pay) < 0 ){
						summary = "多收款:"+pay.subtract(total);
					}
				}
				break;
			case "贷款扣":
				enums2.setId("PAYTYPE_5");
				sdBalance.setMoney(result.getChargePay());
				pay =  result.getChargePay();
				if(pay!= null && !"".equals(pay)){
					if(total.compareTo(pay) > 0 ){
						summary = "未收款:"+total.subtract(pay);
					}else if(total.compareTo(pay) < 0 ){
						summary = "多收款:"+pay.subtract(total);
					}
				}
				break;
			}
        	sdBalance.setPayMent(enums2);
        	sdBalance.setSummary(summary);
        }
        sdBalance.setShouldMoney(result.getTotalFreight());
        sdBalanceService.save(sdBalance);
        return result;
    }

    /**
     *  Get all the sdOrderItems.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SdOrderItem> findAll(Pageable pageable) {
        log.debug("Request to get all SdOrderItems");
        String currentLogin = SecurityUtils.getCurrentUserLogin();
        Page<SdOrderItem> result = sdOrderItemRepository.findByCreatedBy(currentLogin,pageable);
        return result;
    }
    
    /**
     *  Get all the sdOrderItems.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SdOrderItem> findByOrderStat(String orderStat,Pageable pageable) {
    	log.debug("Request to get all SdOrderItems");
    	String currentLogin = SecurityUtils.getCurrentUserLogin();
    	Page<SdOrderItem> result = sdOrderItemRepository.findByOrderStatAndCreatedBy(orderStat,currentLogin,pageable);
    	return result;
    }
    
    /**
     *  Get all the sdOrderItems.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<SdOrderItem> findByOrderHeaderNo(String orderHeaderNo) {
    	log.debug("Request to get all SdOrderItems");
    	String currentLogin = SecurityUtils.getCurrentUserLogin();
    	List<SdOrderItem> result = sdOrderItemRepository.findByOrderHeaderNoAndCreatedBy(orderHeaderNo,currentLogin);
    	return result;
    }

    /**
     *  Get one sdOrderItem by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SdOrderItem findOne(String id) {
        log.debug("Request to get SdOrderItem : {}", id);
        SdOrderItem sdOrderItem = sdOrderItemRepository.findOne(id);
        return sdOrderItem;
    }

    /**
     *  Delete the  sdOrderItem by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete SdOrderItem : {}", id);
        sdOrderItemRepository.delete(id);
        sdOrderItemSearchRepository.delete(id);
    }

    /**
     * Search for the sdOrderItem corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SdOrderItem> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SdOrderItems for query {}", query);
        Page<SdOrderItem> result = sdOrderItemSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
    
    
    /**
     *  修改托运单状态
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Integer updateOrderItemStat(String orderStat,String ids,String orderHeaderNo) {
        log.debug("Request to get all SdOrderItems");
        Integer n = sdOrderItemRepository.updateStat(orderStat,ids,orderHeaderNo);
        return n;
    }
    
    /**
     *  查找ids的运单
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<SdOrderItem> findByQuery(String ids) {
    	log.debug("Request to get all SdOrderItems");
    	String[] idarray = ids.split(",");
    	List<SdOrderItem> result = sdOrderItemRepository.findByIdIn(idarray);
    	return result;
    }
    
}
