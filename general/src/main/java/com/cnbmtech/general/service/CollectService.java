package com.cnbmtech.general.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.base.service.BaseService;
import com.cnbmtech.general.dao.CollectDao;
import com.cnbmtech.general.entity.Collect;

@Service
public class CollectService extends BaseService<Collect, Long>{
	
	@Autowired
	private CollectDao collectDao;
	
	@Override
	public BaseDao<Collect, Long> getEntityDao() {
		return collectDao;
	}
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Integer uniqueCode(String code, Long id) {
		String sql = "SELECT count(0) FROM meeting_collect WHERE `code` = ? AND id != ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{code, id});
	}
	
}
