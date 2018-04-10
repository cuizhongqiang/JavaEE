package com.cnbmtech.general.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.base.service.BaseService;
import com.cnbmtech.general.dao.AttendRegDao;
import com.cnbmtech.general.entity.AttendReg;

@Service
public class AttendRegService extends BaseService<AttendReg, Long>{
	
	@Autowired
	private AttendRegDao attendRegDao;
	
	@Override
	public BaseDao<AttendReg, Long> getEntityDao() {
		return attendRegDao;
	}
	
	public List<AttendReg> findByTypeAndRegIp(String type, String regIp) {
		return attendRegDao.findByTypeAndRegIp(type, regIp);
	}
	
}
