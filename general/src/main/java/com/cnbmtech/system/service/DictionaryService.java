package com.cnbmtech.system.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.base.service.BaseService;
import com.cnbmtech.system.dao.DictionaryDao;
import com.cnbmtech.system.entity.Dictionary;

@Service
public class DictionaryService extends BaseService<Dictionary, Long> {
	
	private static final Logger logger = LoggerFactory.getLogger(DictionaryService.class);
	
	@Autowired
	private DictionaryDao dictionaryDao;

	@Override
	public BaseDao<Dictionary, Long> getEntityDao() {
		return dictionaryDao;
	}
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Dictionary> findByPcode(String pcode) {
		String sql = "SELECT sd2.* FROM sys_dictionary sd1 LEFT JOIN sys_dictionary sd2 ON sd1.id = sd2.pid WHERE sd1.`code` = ? ORDER BY sd2.sort";
		return jdbcTemplate.query(sql, new Object[]{pcode}, new RowMapper<Dictionary>(){

			@Override
			public Dictionary mapRow(ResultSet rs, int rowNum) throws SQLException {
				Dictionary dictionary = new Dictionary();
				dictionary.setId(rs.getLong("id"));
				dictionary.setPid(rs.getLong("pid"));
				dictionary.setName(rs.getString("name"));
				dictionary.setCode(rs.getString("code"));
				dictionary.setValue(rs.getString("value"));
				return dictionary;
			}
			
		});
	}
	
	@CacheEvict(value = "dictionary", key = "#pcode")
	public void removeCache(String pcode) {
		logger.info("@CacheEvict dictionary cache key {}", pcode);
	}
	
	/**
	 * Cacheable：当重复使用相同参数调用方法的时候，方法本身不会被调用执行，即方法本身被略过了，取而代之的是方法的结果直接从缓存中找到并返回了。
	 */
	@Cacheable(value = "dictionary", key = "#pcode")
	public List<Dictionary> findByPcodeCacheable(String pcode) {
		logger.info("@Cacheable dictionary cache key {}", pcode);
		return findByPcode(pcode);
	}
	
	/**
	 * CachePut：这个注释可以确保方法被执行，同时方法的返回值也被记录到缓存中。
	 */
	@CachePut(value = "dictionary", key = "#pcode")
	public List<Dictionary> findByPcodeCachePut(String pcode) {
		logger.info("@CachePut dictionary cache key {}", pcode);
		return findByPcode(pcode);
	}
	
	public Integer uniqueCode(String code, Integer id) {
		String sql = "SELECT count(0) FROM sys_dictionary WHERE `code` = ? AND id != ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{code, id});
	}
	
	public List<Dictionary> findByPidIsNull() {
		return dictionaryDao.findByPidIsNull();
	}
}
