package com.cbmie.genMac.stock.service;

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
import com.cbmie.genMac.stock.dao.PlanStockDetailDao;
import com.cbmie.genMac.stock.entity.PlanStock;
import com.cbmie.genMac.stock.entity.PlanStockDetail;
import com.cbmie.system.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 盘库明细service
 */
@Service
@Transactional
public class PlanStockDetailService extends BaseService<PlanStockDetail, Long> {
	
	@Autowired
	private PlanStockDetailDao planStockDetailDao;

	@Override
	public HibernateDao<PlanStockDetail, Long> getEntityDao() {
		return planStockDetailDao;
	}
	
	public void save(PlanStock planStock, String planStockDetailJson, User currentUser) {
		try {
			// 转成标准的json字符串
			planStockDetailJson = StringEscapeUtils.unescapeHtml4(planStockDetailJson);
			// 把json转成对象
			ObjectMapper objectMapper = new ObjectMapper();
			List<PlanStockDetail> planStockDetailList = new ArrayList<PlanStockDetail>();
			try {
				JsonNode jsonNode = objectMapper.readTree(planStockDetailJson);
				for (JsonNode jn : jsonNode) {
					PlanStockDetail planStockDetail = objectMapper.readValue(jn.toString(), PlanStockDetail.class);
					planStockDetailList.add(planStockDetail);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 获取持久化对象
			List<PlanStockDetail> dataPlanStockDetailList = planStock.getPlanStockDetail();
			// 将数据库数据放入映射
			Map<Long, PlanStockDetail> dataPlanStockDetailMap = new HashMap<Long, PlanStockDetail>(); 
			for (PlanStockDetail dataPlanStockDetail : dataPlanStockDetailList) {
				dataPlanStockDetailMap.put(dataPlanStockDetail.getId(), dataPlanStockDetail);
			}
			// 排除没有发生改变的
			for (PlanStockDetail dataPlanStockDetail : dataPlanStockDetailList) {
				if (planStockDetailList.contains(dataPlanStockDetail)) {
					planStockDetailList.remove(dataPlanStockDetail);
					dataPlanStockDetailMap.remove(dataPlanStockDetail.getId()); //从映射中移除未变化的数据
				}
			}
			// 保存数据
			for (PlanStockDetail planStockDetail : planStockDetailList) {
				if (planStockDetail.getId() == null) {
					insert(planStock.getId(), planStockDetail, dataPlanStockDetailList); // 新增 
				}
				PlanStockDetail dataPlanStockDetail = dataPlanStockDetailMap.get(planStockDetail.getId());
				if (dataPlanStockDetail != null) {
					update(dataPlanStockDetail, planStockDetail); // 修改
					dataPlanStockDetailMap.remove(planStockDetail.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
				}
			}
			// 删除数据
			for (Entry<Long, PlanStockDetail> entry : dataPlanStockDetailMap.entrySet()) {
				dataPlanStockDetailList.remove(entry.getValue());
				planStockDetailDao.delete(entry.getKey());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update(PlanStockDetail dataPlanStockDetail, PlanStockDetail planStockDetail) {
		dataPlanStockDetail.setWarehouseName(planStockDetail.getWarehouseName());
		dataPlanStockDetail.setGoodsNameSpecification(planStockDetail.getGoodsNameSpecification());
		dataPlanStockDetail.setBookNum(planStockDetail.getBookNum());
		dataPlanStockDetail.setProfitNum(planStockDetail.getProfitNum());
		dataPlanStockDetail.setLossNum(planStockDetail.getLossNum());
		dataPlanStockDetail.setInventoryNum(planStockDetail.getInventoryNum());
		dataPlanStockDetail.setDiffInstruction(planStockDetail.getDiffInstruction());
		dataPlanStockDetail.setRemark(planStockDetail.getRemark());
	}
	
	private void insert(Long pid, PlanStockDetail planStockDetail, List<PlanStockDetail> dataPlanStockDetailList) {
		planStockDetail.setParentId(pid);
		dataPlanStockDetailList.add(planStockDetail);
	}
	
	public List<String> findGoodsForWarehouse(String warehouseName) {
		return planStockDetailDao.findGoodsForWarehouse(warehouseName);
	}
	
	public Double findGoodsSumForWarehouse(String warehouseName, String goodsNameSpecification, Long id) {
		return planStockDetailDao.findGoodsSumForWarehouse(warehouseName, goodsNameSpecification, id);
	}
	
}
