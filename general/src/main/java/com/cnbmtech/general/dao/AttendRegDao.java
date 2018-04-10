package com.cnbmtech.general.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.general.entity.AttendReg;

@Repository
public interface AttendRegDao extends BaseDao<AttendReg, Long> {
	
	public List<AttendReg> findByTypeAndRegIp(String type, String regIp);
	
}
