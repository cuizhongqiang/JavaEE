package com.cbmie.genMac.accessory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.accessory.dao.AccessoryDao;
import com.cbmie.genMac.accessory.entity.Accessory;

/**
 * 附件service
 */
@Service
@Transactional(readOnly = true)
public class AccessoryService extends BaseService<Accessory, Long> {

	@Autowired
	private AccessoryDao accessoryDao;

	@Override
	public HibernateDao<Accessory, Long> getEntityDao() {
		return accessoryDao;
	}

	/**
	 * 按accId查询附件
	 * @param accId
	 * @return 附件对象
	 */
	public Accessory getAcc(Long accId) {
		Accessory accessory = new Accessory();
		accessory = accessoryDao.findUniqueBy("accId", accId);
		return accessory;
	}
}
