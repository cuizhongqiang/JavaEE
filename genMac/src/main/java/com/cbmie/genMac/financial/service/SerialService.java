package com.cbmie.genMac.financial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.financial.dao.SerialDao;
import com.cbmie.genMac.financial.entity.Serial;

/**
 * 水单service
 */
@Service
@Transactional
public class SerialService extends BaseService<Serial, Long> {
	
	@Autowired
	private SerialDao serialDao;

	@Override
	public HibernateDao<Serial, Long> getEntityDao() {
		return serialDao;
	}
	
	public Serial findByNo(Long id, String no) {
		return serialDao.findByNo(id, no);
	}
	
	public List<Serial> findByParentId(String id) {
		return serialDao.findBy("splitStatus", id);
	}
	
	public Double sum(String contractNo, String serialCategory) {
		return serialDao.sum(contractNo, serialCategory);
	}
	
	/**
	 * 客户来款水单总金额
	 */
	public Double sumCustomerSerial(Long customerId, String serialCategory) {
		return serialDao.sumCustomerSerial(customerId, serialCategory);
	}

}
