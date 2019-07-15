package com.cbmie.genMac.stock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.stock.dao.InStockDao;
import com.cbmie.genMac.stock.entity.InStock;
import com.cbmie.genMac.utils.DocumentCode;

/**
 * 入库service
 */
@Service
@Transactional
public class InStockService extends BaseService<InStock, Long> {
	
	@Autowired
	private InStockDao inStockDao;
	
	@Autowired
	private DocumentCode documentCode;

	@Override
	public HibernateDao<InStock, Long> getEntityDao() {
		return inStockDao;
	}
	
	public InStock findByNo(Long id, String no) {
		return inStockDao.findByNo(id, no);
	}

	public List<InStock> find(List<PropertyFilter> filters) {
		return inStockDao.find(filters);
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
			str += documentCode.getOrderNum(InStock.class, "inStockId", str);
			return str;
		} catch (Exception e) {
			return "生成编码失败！";
		}
	}
	
}
