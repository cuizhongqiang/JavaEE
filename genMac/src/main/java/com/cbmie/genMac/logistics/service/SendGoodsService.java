package com.cbmie.genMac.logistics.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.logistics.dao.SendGoodsDao;
import com.cbmie.genMac.logistics.entity.SendGoods;
import com.cbmie.genMac.utils.DocumentCode;

/**
 * 放货service
 */
@Service
@Transactional
public class SendGoodsService extends BaseService<SendGoods, Long> {

	@Autowired
	private SendGoodsDao sendGoodsDao;
	
	@Autowired
	private DocumentCode documentCode;

	@Override
	public HibernateDao<SendGoods, Long> getEntityDao() {
		return sendGoodsDao;
	}
	
	public List<SendGoods> findBy(String propertyName, String value) {
		return sendGoodsDao.findBy(propertyName, value);
	}
	
	public SendGoods findByNo(Long id, String no){
		return sendGoodsDao.findByNo(id, no);
	}
	
	public List<SendGoods> findForPlan() {
		return sendGoodsDao.findForPlan();
	}
	
	/**
	 * 找出对应合同下的其他放货总金额
	 * @param contractNo 合同号
	 * @param id 排除发货id
	 * @return
	 */
	public Double sum(String contractNo, Long id) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		PropertyFilter one = new PropertyFilter("EQS_contractNo", contractNo);
		PropertyFilter two = new PropertyFilter("EQS_state", "生效");
		filters.add(one);
		filters.add(two);
		List<SendGoods> sendGoodsList = sendGoodsDao.find(filters);
		Double sum = 0d;
		for (SendGoods sendGoods : sendGoodsList) {
			if (id != sendGoods.getId() && sendGoods.getTotalMoney() != null) {
				sum += sendGoods.getTotalMoney();
			}
		}
		return sum;
	}
	
	/**
	 * 构造编号
	 * @param contractNo 合同号
	 * @param documentType 文档类型
	 * @return
	 */
	public String generateCode(String contractNo, String documentType) {
		try {
			//年份+制作人+文档类型+文档签订日
			String str = documentCode.combination(documentType);
			//客户识别号
			str += documentCode.getCustomerNumFromContractNo(contractNo);
			//顺序自编号
			str += documentCode.getOrderNum(SendGoods.class, "sendGoodsNo", str);
			return str;
		} catch (Exception e) {
			return "生成编码失败！";
		}
	}
	
}
