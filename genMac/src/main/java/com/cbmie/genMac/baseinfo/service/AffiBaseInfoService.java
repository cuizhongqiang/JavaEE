package com.cbmie.genMac.baseinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.baseinfo.dao.AffiBaseInfoDao;
import com.cbmie.genMac.baseinfo.entity.AffiBaseInfo;
import com.cbmie.genMac.utils.DocumentCode;

/**
 * 关联单位__基本信息service
 */
@Service
@Transactional(readOnly = true)
public class AffiBaseInfoService extends BaseService<AffiBaseInfo, Long> {

	@Autowired
	private AffiBaseInfoDao affiBaseInfoDao;
	
	@Autowired
	private DocumentCode documentCode;

	@Override
	public HibernateDao<AffiBaseInfo, Long> getEntityDao() {
		return affiBaseInfoDao;
	}
	
	public List<AffiBaseInfo> getCompany(String num) {
		return affiBaseInfoDao.getCompany(num);
	}
	
	public AffiBaseInfo findByNo(Long id, String customerCode) {
		return affiBaseInfoDao.findByNo(id, customerCode);
	}
	
	public String generateCode(String customerName) {
		return documentCode.customerNum(customerName);
	}

}
