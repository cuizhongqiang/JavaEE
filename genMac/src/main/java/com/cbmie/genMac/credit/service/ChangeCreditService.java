package com.cbmie.genMac.credit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.credit.dao.ChangeCreditDao;
import com.cbmie.genMac.credit.entity.ChangeCredit;

/**
 * 改证service
 */
@Service
@Transactional
public class ChangeCreditService extends BaseService<ChangeCredit, Long> {

	@Autowired
	private ChangeCreditDao changeCreditDao;

	@Override
	public HibernateDao<ChangeCredit, Long> getEntityDao() {
		return changeCreditDao;
	}
	
	/**
	 * 是否存在正在审批的改证
	 */
	public List<ChangeCredit> findOnlyApply(Long changeId) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		PropertyFilter two = new PropertyFilter("EQS_state", "已提交");
		PropertyFilter one = new PropertyFilter("EQL_changeId", changeId.toString());
		filters.add(one);
		filters.add(two);
		return changeCreditDao.find(filters);
	}
	
}
