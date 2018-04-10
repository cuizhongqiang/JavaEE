package com.cbmie.genMac.financial.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.financial.dao.ExpenseDetailDao;
import com.cbmie.genMac.financial.entity.Expense;
import com.cbmie.genMac.financial.entity.ExpenseDetail;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 付费service
 */
@Service
@Transactional
public class ExpenseDetailService extends BaseService<ExpenseDetail, Long> {

	@Autowired
	private ExpenseDetailDao expenseDetailDao;

	@Override
	public HibernateDao<ExpenseDetail, Long> getEntityDao() {
		return expenseDetailDao;
	}

	
	public void save(Expense expense, String expenseDetailJson) {
		// 转成标准的json字符串
		expenseDetailJson = StringEscapeUtils.unescapeHtml4(expenseDetailJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<ExpenseDetail> paymentList = new ArrayList<ExpenseDetail>();
		try {
			JsonNode jsonNode = objectMapper.readTree(expenseDetailJson);
			for (JsonNode jn : jsonNode) {
				ExpenseDetail payment = objectMapper.readValue(jn.toString(), ExpenseDetail.class);
				paymentList.add(payment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取持久化对象
		List<ExpenseDetail> dataExpenseDetailList = expense.getExpenseDetail();
		// 将数据库数据放入映射
		Map<Long, ExpenseDetail> dataExpenseDetailMap = new HashMap<Long, ExpenseDetail>(); 
		for (ExpenseDetail dataExpenseDetail : dataExpenseDetailList) {
			dataExpenseDetailMap.put(dataExpenseDetail.getId(), dataExpenseDetail);
		}
		// 排除没有发生改变的
		for (ExpenseDetail dataExpenseDetail : dataExpenseDetailList) {
			if (paymentList.contains(dataExpenseDetail)) {
				paymentList.remove(dataExpenseDetail);
				dataExpenseDetailMap.remove(dataExpenseDetail.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (ExpenseDetail payment : paymentList) {
			if (payment.getId() == null) {
				insert(expense.getId(), payment, dataExpenseDetailList); // 新增 
			}
			ExpenseDetail dataExpenseDetail = dataExpenseDetailMap.get(payment.getId());
			if (dataExpenseDetail != null) {
				update(dataExpenseDetail, payment); // 修改
				dataExpenseDetailMap.remove(payment.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, ExpenseDetail> entry : dataExpenseDetailMap.entrySet()) {
			dataExpenseDetailList.remove(entry.getValue());
			expenseDetailDao.delete(entry.getKey());
		}
	}

	private void update(ExpenseDetail dataExpenseDetail, ExpenseDetail expenseDetail) {
		dataExpenseDetail.setDocumentNo(expenseDetail.getDocumentNo());
		dataExpenseDetail.setPaymentChildType(expenseDetail.getPaymentChildType());
		dataExpenseDetail.setSettlement(expenseDetail.getSettlement());
		dataExpenseDetail.setReceiptStatus(expenseDetail.getReceiptStatus());
		dataExpenseDetail.setPayModel(expenseDetail.getPayModel());
		dataExpenseDetail.setBankName(expenseDetail.getBankName());
		dataExpenseDetail.setBankNo(expenseDetail.getBankNo());
		dataExpenseDetail.setMoney(expenseDetail.getMoney());
		dataExpenseDetail.setRemark(expenseDetail.getRemark());
	}
	
	private void insert(Long pid, ExpenseDetail expenseDetail, List<ExpenseDetail> dataExpenseDetailList) {
		expenseDetail.setPid(pid);
		dataExpenseDetailList.add(expenseDetail);
	}
	
}
