package com.cbmie.genMac.selfRun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.selfRun.dao.SelfSalesDao;
import com.cbmie.genMac.selfRun.entity.SelfSales;
import com.cbmie.genMac.utils.DocumentCode;

/**
 * 销售合同service
 */
@Service
@Transactional
public class SelfSalesService extends BaseService<SelfSales, Long> {

	@Autowired
	private SelfSalesDao selfSalesDao;
	
	@Autowired
	private DocumentCode documentCode;
	
	@Override
	public HibernateDao<SelfSales, Long> getEntityDao() {
		return selfSalesDao;
	}
	
	public SelfSales findByNo(Long id, String contractNo){
		return selfSalesDao.findByNo(id, contractNo);
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
			str += documentCode.getOrderNum(SelfSales.class, "contractNo", str);
			return str;
		} catch (Exception e) {
			return "生成编码失败！";
		}
	}
	
}
