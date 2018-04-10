package com.cnbmtech.general.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.base.service.BaseService;
import com.cnbmtech.general.dao.TemplateCollectDao;
import com.cnbmtech.general.entity.TemplateCollect;

@Service
public class TemplateCollectService extends BaseService<TemplateCollect, Long>{
	
	@Autowired
	private TemplateCollectDao templateCollectDao;
	
	@Override
	public BaseDao<TemplateCollect, Long> getEntityDao() {
		return templateCollectDao;
	}
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	public void deleteById(Long id) {
		jdbcTemplate.update("DELETE FROM template_collect WHERE id = ?", id);
	}
	
}
