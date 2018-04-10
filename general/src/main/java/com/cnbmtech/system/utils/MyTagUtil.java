package com.cnbmtech.system.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cnbmtech.base.utils.SpringContextUtils;
import com.cnbmtech.general.entity.Collect;
import com.cnbmtech.general.entity.Template;
import com.cnbmtech.general.service.CollectService;
import com.cnbmtech.general.service.TemplateService;
import com.cnbmtech.system.entity.Dictionary;
import com.cnbmtech.system.service.DictionaryService;

/**
 * 自定义标签工具类
 */
@Component
public class MyTagUtil {
	
	@Autowired
	private static DictionaryService dictionaryService = SpringContextUtils.getBean(DictionaryService.class);
	
	@Autowired
	private static CollectService collectService = SpringContextUtils.getBean(CollectService.class);
	
	@Autowired
	private static TemplateService templateService = SpringContextUtils.getBean(TemplateService.class);
	
	public static List<Dictionary> findDictByPcode(String pcode) {
		return dictionaryService.findByPcodeCacheable(pcode);
	}
	
	public static List<Dictionary> findDictByPidIsNull() {
		return dictionaryService.findByPidIsNull();
	}
	
	public static List<Collect> findMeetingCollect() {
		return collectService.getAll();
	}
	
	public static List<Template> findAllTemplate() {
		return templateService.getAll();
	}
	
}