package com.cbmie.genMac.selfRun.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.service.BaseService;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.genMac.credit.entity.OpenCredit;
import com.cbmie.genMac.credit.service.OpenCreditService;
import com.cbmie.genMac.selfRun.dao.SelfPurchaseDao;
import com.cbmie.genMac.selfRun.entity.SelfPurchase;
import com.cbmie.genMac.utils.DocumentCode;

/**
 * 采购合同service
 */
@Service
@Transactional
public class SelfPurchaseService extends BaseService<SelfPurchase, Long> {

	@Autowired
	private SelfPurchaseDao selfPurchaseDao;
	
	@Autowired
	private DocumentCode documentCode;
	
	@Autowired
	private OpenCreditService openCreditService;
	
	@Override
	public HibernateDao<SelfPurchase, Long> getEntityDao() {
		return selfPurchaseDao;
	}
	
	public SelfPurchase findByNo(Long id, String contractNo){
		return selfPurchaseDao.findByNo(id, contractNo);
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
			str += documentCode.getOrderNum(SelfPurchase.class, "contractNo", str);
			return str;
		} catch (Exception e) {
			return "生成编码失败！";
		}
	}
	
	/**
	 * 满足可申请开证的
	 * @param type LC/TT
	 * @return
	 */
	public List<SelfPurchase> findOpenCredit(String type) {
		List<SelfPurchase> rtnList = new ArrayList<SelfPurchase>();
		List<SelfPurchase> list = selfPurchaseDao.findBy("state", "生效");
		for (SelfPurchase selfPurchase : list) {
			if (type.equals("LC")) {
				if (StringUtils.containsAny(selfPurchase.getPaymentMethod(), new char[]{'即','远'})) {
					rtnList.add(selfPurchase);
				}
			} else if (type.equals("TT")) {
				if (StringUtils.contains(selfPurchase.getPaymentMethod(), "TT")) {
					rtnList.add(selfPurchase);
				}
			}
		}
		return rtnList;
	}
	
	/**
	 * 找出满足到单登记的合同
	 */
	public List<SelfPurchase> findInvoiceReg() {
		List<SelfPurchase> rtnList = new ArrayList<SelfPurchase>();
		List<SelfPurchase> list = selfPurchaseDao.findBy("state", "生效");
		for (SelfPurchase selfPurchase : list) {
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			PropertyFilter one = new PropertyFilter("EQS_contractNo", selfPurchase.getContractNo());
			PropertyFilter two = new PropertyFilter("NES_state", "作废");
			filters.add(one);
			filters.add(two);
			List<OpenCredit> openCreditList = openCreditService.search(filters);
			boolean flag = false;
			for (OpenCredit openCredit : openCreditList) {
				if (!openCredit.getState().equals("生效")) {
					flag = false;
					break;
				} else if (openCredit.getLcType() != 3 && StringUtils.isEmpty(openCredit.getLcNo())) {
					flag = false;
					break;
				} else {
					flag = true;
				}
			}
			if (flag) {
				rtnList.add(selfPurchase);
			}
		}
		return rtnList;
	}
	
}
