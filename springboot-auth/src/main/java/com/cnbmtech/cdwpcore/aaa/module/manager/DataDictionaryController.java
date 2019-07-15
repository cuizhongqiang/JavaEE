
    /**  
    * @Title: DataDictionaryRepository.java
    * @Package com.cnbmtech.cdwpcore.aaa.jpa
    * @Description: TODO(用一句话描述该文件做什么)
    * @author markzgwu
    * @date 2017年12月24日
    * @version V1.0  
    */
    
package com.cnbmtech.cdwpcore.aaa.module.manager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
    * @ClassName: DataDictionaryRepository
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author markzgwu
    * @date 2017年12月22日
    *
    */
@RestController
@RequestMapping(value = { "/datadictionary" }, method = RequestMethod.GET)
public class DataDictionaryController{
	@Autowired
	DataDictionaryRepository dataDictionaryRepository;
	
	@RequestMapping(value = { "/countListCatalogs" }, method = RequestMethod.GET)
	List<Map<String,?>> countListCatalogs(){
		return dataDictionaryRepository.countListCatalogs();
	}
}
