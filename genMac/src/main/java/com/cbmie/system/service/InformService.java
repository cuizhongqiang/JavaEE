package com.cbmie.system.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.system.dao.InformDao;
import com.cbmie.system.entity.Inform;

/**
 * 系统通知service
 */
@Service
@Transactional
public class InformService extends BaseService<Inform, Integer> {
	
	@Autowired
	private InformDao informDao;

	@Override
	public HibernateDao<Inform, Integer> getEntityDao() {
		return informDao;
	}
	
	public Inform findBusinessInform(String className, Long businessId){
		return informDao.findBusinessInform(className, businessId);
	}
	
	public List<Inform> findBy(String propertyName, String value) {
		return informDao.findBy(propertyName, value);
	}
	
	public void insert(String className, Long businessId, String name, String subject, String content, int days) {
		Inform inform = new Inform();
		inform.setClassName(className);
		inform.setBusinessId(businessId);
		inform.setPerson(name);
		inform.setSubject(subject);
		inform.setContent(content);
		inform.setResidueDays(days);
		inform.setCreateDate(new Date());
		save(inform);
	}
	
}
