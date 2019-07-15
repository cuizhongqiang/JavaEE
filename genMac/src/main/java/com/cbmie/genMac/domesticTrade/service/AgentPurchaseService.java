package com.cbmie.genMac.domesticTrade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.domesticTrade.dao.AgentPurchaseDao;
import com.cbmie.genMac.domesticTrade.entity.AgentPurchase;
import com.cbmie.genMac.utils.DocumentCode;

/**
 * 代理采购合同service
 */
@Service
@Transactional(readOnly = true)
public class AgentPurchaseService extends BaseService<AgentPurchase, Long> {

	@Autowired
	private AgentPurchaseDao agentPurchaseDao;
	
	@Autowired
	private DocumentCode documentCode;

	@Override
	public HibernateDao<AgentPurchase, Long> getEntityDao() {
		return agentPurchaseDao;
	}
	
	public AgentPurchase findByNo(Long id, String contractNo){
		return agentPurchaseDao.findByNo(id, contractNo);
	}
	
	/**
	 * 构造编号
	 * @param customer 客户编码
	 * @param documentType 文档类型-合同
	 * @return
	 */
	public String generateCode(String customer, String documentType) {
		try {
			//年份+制作人+文档类型+文档签订日
			String str = documentCode.combination(documentType);
			//客户识别号
			str += customer;
			//顺序自编号
			str += documentCode.getOrderNum(AgentPurchase.class, "contractNo", str);
			return str;
		} catch (Exception e) {
			return "生成编码失败！";
		}
	}
}
