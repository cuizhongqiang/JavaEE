package com.cnbmtech.general.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.base.service.BaseService;
import com.cnbmtech.general.dao.BriefInforDao;
import com.cnbmtech.general.entity.BriefInfor;

@Service
public class BriefInforService extends BaseService<BriefInfor, Long>{
	
	@Autowired
	private BriefInforDao briefInforDao;
	
	@Override
	public BaseDao<BriefInfor, Long> getEntityDao() {
		return briefInforDao;
	}
	
}
