package com.cnbmtech.general.dao;

import org.springframework.stereotype.Repository;

import com.cnbmtech.base.dao.BaseDao;
import com.cnbmtech.general.entity.StaffInfor;

@Repository
public interface StaffInforDao extends BaseDao<StaffInfor, Long> {
	
	public StaffInfor findBySysCode(String sysCode);
	
}
