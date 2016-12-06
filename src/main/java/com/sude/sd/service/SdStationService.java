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
import com.sude.sd.domain.SdStation;
import com.sude.sd.repository.SdStationRepository;
import com.sude.sd.repository.search.SdStationSearchRepository;
import com.sude.sd.web.rest.util.PinYin2Abbreviation;

/**
 * Service Implementation for managing SdStation.
 */
@Service
@Transactional
public class SdStationService {

    private final Logger log = LoggerFactory.getLogger(SdStationService.class);
    
    @Inject
    private SdStationRepository sdStationRepository;

    @Inject
    private SdStationSearchRepository sdStationSearchRepository;

    /**
     * Save a sdStation.
     *
     * @param sdStation the entity to save
     * @return the persisted entity
     */
    public SdStation save(SdStation sdStation) {
        log.debug("Request to save SdStation : {}", sdStation);
        //汉字转字母
        String enName = PinYin2Abbreviation.cn2py(sdStation.getStationName());
        sdStation.setStationNM(enName);
        SdStation result = sdStationRepository.save(sdStation);
        sdStationSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the sdStations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SdStation> findAll(Pageable pageable) {
        log.debug("Request to get all SdStations");
        Page<SdStation> result = sdStationRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one sdStation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SdStation findOne(Long id) {
        log.debug("Request to get SdStation : {}", id);
        SdStation sdStation = sdStationRepository.findOne(id);
        return sdStation;
    }

    /**
     *  Delete the  sdStation by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SdStation : {}", id);
        sdStationRepository.delete(id);
        sdStationSearchRepository.delete(id);
    }

    /**
     * Search for the sdStation corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SdStation> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SdStations for query {}", query);
        Page<SdStation> result = sdStationSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
    
    /**
     * Search for the sdStation corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<SdStation> search(String query) {
    	log.debug("Request to search for a page of SdStations for query {}", query);
    	Iterable<SdStation> iter = sdStationSearchRepository.search(queryStringQuery(query));
		Iterator<SdStation> iterator = iter.iterator();
		List result = new ArrayList();
		for (;iterator.hasNext();) {
			Object element = (Object) iterator.next();
			result.add(element);
		}
    	return result;
    }
    
    /**
     * 检查是否已存在站点.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    public SdOrderItem checkHasStation(SdOrderItem sdOrderItem) {
    	String toStationName = sdOrderItem.getToStationName();
    	if(null !=toStationName && !"".equals(toStationName)){
    		List<SdStation> list = sdStationRepository.findByStationName(toStationName);
    		if(list.size() == 0){
    			SdStation sdStation = new SdStation();
    			sdStation.setStationName(sdOrderItem.getToStationName());
    			//汉字转字母
    			String enName = PinYin2Abbreviation.cn2py(toStationName);
    			sdStation.setStationNM(enName);
    			sdStation = save(sdStation);
    			sdOrderItem.setToStation(sdStation.getId()+"");
    		}
    	}
    	//启运地默认必须有值
    	String fromStation = sdOrderItem.getFromStation();
    	if(null !=fromStation && !"".equals(fromStation)){
    		SdStation sdStation = sdStationRepository.findOne(Long.valueOf(sdOrderItem.getFromStation()));
    		if(sdStation != null){
    			sdOrderItem.setFromStationName(sdStation.getStationName());
    		}
    	}
		return sdOrderItem;
	}
    
    /**
     * 检测是否已存在站点
     * @param stationName
     * @return
     */
    public String checkHasStation(String stationName) {
    	if(stationName == null){
    		return stationName;
    	}
    	List<SdStation> list = sdStationRepository.findByStationName(stationName);
    	SdStation sdStation = new SdStation();
    	if(list.size() == 0){
			sdStation.setStationName(stationName);
			//汉字转字母
			String enName = PinYin2Abbreviation.cn2py(stationName);
			sdStation.setStationNM(enName);
			sdStation = save(sdStation);
		}else{
			sdStation = list.get(0);
		}
		return sdStation.getId()+"";
    }
    
    /**
     * 获取站点名称
     * @param stationId
     * @return
     */
    public String getStationName(String stationId) {
    	if(stationId == null){return stationId;}
    	SdStation sdStation = sdStationRepository.findOne(Long.valueOf(stationId));
    	return sdStation.getStationName();
    }
    
}
