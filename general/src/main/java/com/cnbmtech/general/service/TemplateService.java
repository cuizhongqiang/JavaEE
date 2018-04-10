package com.cnbmtech.general.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.base.service.BaseService;
import com.cnbmtech.general.dao.TemplateDao;
import com.cnbmtech.general.entity.Collect;
import com.cnbmtech.general.entity.Template;
import com.cnbmtech.general.entity.TemplateCollect;

@Service
public class TemplateService extends BaseService<Template, Long>{
	
	@Autowired
	private TemplateDao templateDao;
	
	@Autowired
	private CollectService CollectService;
	
	@Autowired
	private TemplateCollectService templateCollectService;
	
	@Override
	public BaseDao<Template, Long> getEntityDao() {
		return templateDao;
	}
	
	/**
	 * 保存采集项
	 */
	public void saveCollect(String[] collectIdArray, Template template) {
		if (collectIdArray == null) {
			collectIdArray = new String[0];
		}
		Set<Long> collectIdSet = new HashSet<Long>();
		for (String collectId : collectIdArray) {
			if (collectId.trim().length() > 0) {
				collectIdSet.add(Long.valueOf(collectId));
			}
		}
		List<TemplateCollect> templateCollects = template.getTemplateCollect();
		//处理重复,删除多余
		for (TemplateCollect templateCollect : templateCollects) {
			Long collectId = templateCollect.getCollect().getId();
			if (!collectIdSet.remove(collectId)) {
				templateCollectService.deleteById(templateCollect.getId());
			}
		}
		//新增
		for (Long collectId : collectIdSet) {
			Collect collect = CollectService.get(collectId);
			TemplateCollect tc = new TemplateCollect();
			tc.setTemplate(template);
			tc.setCollect(collect);
			templateCollectService.save(tc);
		}
	}
	
}
