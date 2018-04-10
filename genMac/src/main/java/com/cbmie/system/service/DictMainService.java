package com.cbmie.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.common.utils.EhCacheUtils;
import com.cbmie.system.dao.DictMainDao;
import com.cbmie.system.entity.DictMain;
import com.cbmie.system.utils.DictUtils;

/**
 * 字典主表service
 */
@Service
@Transactional
public class DictMainService extends BaseService<DictMain, Integer> {
	
	@Autowired
	private DictMainDao dictMainDao;

	@Override
	public HibernateDao<DictMain, Integer> getEntityDao() {
		return dictMainDao;
	}
	
	public DictMain findByCode(String code) {
		return dictMainDao.findByCode(code);
	}
	
	public void save(final DictMain entity) {
		super.save(entity);
		EhCacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}
	
	public void update(final DictMain entity){
		super.update(entity);
		EhCacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}
	
	public void delete(final DictMain entity){
		super.delete(entity);
		EhCacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}
}
