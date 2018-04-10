package com.cnbmtech.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.system.entity.Dictionary;

@Repository
public interface DictionaryDao extends BaseDao<Dictionary, Long> {
	
	public Dictionary findByCode(String code);
	
	public List<Dictionary> findByPidIsNull();
	
}
