package com.cbmie.activiti.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.activiti.dao.EncyclicDao;
import com.cbmie.activiti.entity.ApprovalOpinion;
import com.cbmie.activiti.entity.Encyclic;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.UserService;
import com.cbmie.system.utils.UserUtil;

/**
 * 传阅service
 */
@Service
@Transactional
public class EncyclicService extends BaseService<Encyclic, Integer>{
	
	@Autowired
	private EncyclicDao encyclicDao;
	
	@Autowired
	private UserService userService;
	
	@Override
	public HibernateDao<Encyclic, Integer> getEntityDao() {
		return encyclicDao;
	}
	
	public List<Encyclic> findBy(final String propertyName, final Object value) {
		return encyclicDao.findBy(propertyName, value);
	}
	
	public void save(ApprovalOpinion approval, String businessInfo) {
		User user = UserUtil.getCurrentUser();
		if (user == null) {
			user = userService.getUser(approval.getLoginName());
		}
		for (String loginName : approval.getEncyclic()) {
			Encyclic encyclic  = new Encyclic();
			encyclic.setBusinessKey(approval.getBusinessKey());
			encyclic.setProcessKey(approval.getProcessKey());
			encyclic.setProcessInstanceId(approval.getProcessInstanceId());
			encyclic.setBusinessInfo(businessInfo);
			encyclic.setLoginName(loginName);
			encyclic.setSendName(user.getName());
			encyclic.setCreateDate(new Date());
			encyclicDao.save(encyclic);
		}
	}

}
