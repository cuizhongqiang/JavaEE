package com.cbmie.genMac.stock.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.genMac.logistics.entity.SendGoods;
import com.cbmie.genMac.logistics.entity.SendGoodsGoods;
import com.cbmie.genMac.logistics.service.SendGoodsService;
import com.cbmie.genMac.stock.dao.PlanStockDetailDao;
import com.cbmie.genMac.stock.entity.InStock;
import com.cbmie.genMac.stock.entity.InStockGoods;
import com.cbmie.genMac.stock.entity.QueryStockDetail;

/**
 * 库存商品查询service
 */
@Service
@Transactional
public class QueryStockService {
	
	@Autowired
	private SendGoodsService sendGoodsService;
	
	@Autowired
	private InStockService inStockService;
	
	@Autowired
	private PlanStockDetailDao planStockDetailDao;

	public List<QueryStockDetail> queryStock(List<PropertyFilter> filters, HttpServletRequest request) {
		List<QueryStockDetail> resultList = new ArrayList<QueryStockDetail>();
		// 找出入库
		List<InStock> inStockList = inStockService.find(filters);
		// 找出"走库存"且"生效"的出库,并按出库时间进行排序
		List<SendGoods> sendGoodsList = sendGoodsService.findForPlan();
		
		// 出库map<入库单号, 出库对象集合(一个入库单可能对应多个出库单)>
		Map<String, List<SendGoods>> outMap = new HashMap<String, List<SendGoods>>();
		// 放入→出库map
		for (SendGoods sendGoods : sendGoodsList) {
			if (outMap.get(sendGoods.getInStockId()) == null) {
				List<SendGoods> sendGoodsMapList = new ArrayList<SendGoods>();
				sendGoodsMapList.add(sendGoods);
				outMap.put(sendGoods.getInStockId(), sendGoodsMapList);
			} else {
				outMap.get(sendGoods.getInStockId()).add(sendGoods);
			}
		}
		
		// 循环迭代入库
		for (InStock inStock : inStockList) {
			// key(商品名称/规格型号),value(盘库明细)
			Map<String, QueryStockDetail> psdMap = new HashMap<String, QueryStockDetail>();
			// 入库商品
			List<InStockGoods> inStockGoodsList = inStock.getInStockGoods();
			for (InStockGoods inStockGoods : inStockGoodsList) {
				// 构造盘库明细
				QueryStockDetail psd = generateDetailByIn(inStock, inStockGoods);
				psdMap.put(psd.getGoodsNameSpecification(), psd);
			}
			// 根据入库单号获取出库单集合
			List<SendGoods> sendGoodsMapList = outMap.get(inStock.getInStockId());
			if (sendGoodsMapList != null) {
				for (SendGoods sendGoods : sendGoodsMapList) {
					// 出库商品
					for (SendGoodsGoods sendGoodsGoods : sendGoods.getSendGoodsGoods()) {
						QueryStockDetail qsd = psdMap.get(sendGoodsGoods.getGoodsCategory() + "/" + sendGoodsGoods.getSpecification());
						if (qsd == null) {// 此商品有出库，没入库
							QueryStockDetail psdNoin = generateDetailByOut(inStock, sendGoods, sendGoodsGoods);
							psdMap.put(psdNoin.getGoodsNameSpecification(), psdNoin);
						} else {
							// 如果入库的商品已经存在出库了,先保存，再放新的
							if (qsd.getSendGoodsNo() != null && qsd.getSendGoodsNo().length() > 0) {
								// 保存
								saveResult(resultList, request, qsd);
								QueryStockDetail psdNew = new QueryStockDetail();
								try {
									BeanUtils.copyProperties(psdNew, qsd);
								} catch (Exception e) {
									e.printStackTrace();
								}
								psdNew.setInStockAmount(qsd.getInStockAmount() - qsd.getSendGoodsAmount());// 出库后的剩余数量
								psdNew.setSendGoodsNo(sendGoods.getSendGoodsNo());
								psdNew.setSendGoodsAmount(sendGoodsGoods.getAmount());
								psdNew.setSendDate(sendGoods.getSendDate());
								psdMap.put(sendGoodsGoods.getGoodsCategory() + "/" + sendGoodsGoods.getSpecification(), psdNew);
							} else {
								qsd.setSendGoodsNo(sendGoods.getSendGoodsNo());
								qsd.setSendGoodsAmount(sendGoodsGoods.getAmount());
								qsd.setSendDate(sendGoods.getSendDate());
							}
						}
					}
				}
			}
			for (Entry<String, QueryStockDetail> entry : psdMap.entrySet()) {
				// 保存
				saveResult(resultList, request, entry.getValue());
			}
		}
		return resultList;
	}
	
	private void saveResult(List<QueryStockDetail> resultList, HttpServletRequest request, QueryStockDetail psd) {
		String goodsNameSpecification = request.getParameter("goodsNameSpecification");
		if (StringUtils.isNotBlank(goodsNameSpecification)) {
			if (psd.getGoodsNameSpecification().contains(goodsNameSpecification)) {
				resultList.add(psd);
			}
		} else {
			resultList.add(psd);
		}
	}
	
	private QueryStockDetail generateDetailByIn(InStock inStock, InStockGoods inStockGoods) {
		QueryStockDetail qsd = new QueryStockDetail();
		qsd.setGoodsNameSpecification(inStockGoods.getGoodsCategory() + "/" + inStockGoods.getSpecification());
		qsd.setInvoiceNo(inStock.getInvoiceNo());
		qsd.setWarehouseName(inStock.getStorageUnit());
		qsd.setInStockId(inStock.getInStockId());
		qsd.setInStockAmount(inStockGoods.getAmount());
		qsd.setInStockDate(inStock.getInStockDate());
		return qsd;
	}
	
	private QueryStockDetail generateDetailByOut(InStock inStock, SendGoods sendGoods, SendGoodsGoods sendGoodsGoods) {
		QueryStockDetail qsd = new QueryStockDetail();
		qsd.setGoodsNameSpecification(sendGoodsGoods.getGoodsCategory() + "/" + sendGoodsGoods.getSpecification());
		qsd.setInvoiceNo(sendGoods.getInvoiceNo());
		qsd.setWarehouseName(inStock.getStorageUnit());
		qsd.setInStockId(inStock.getInStockId());
		qsd.setInStockAmount(0d);
		qsd.setInStockDate(inStock.getInStockDate());
		qsd.setSendGoodsNo(sendGoods.getSendGoodsNo());
		qsd.setSendGoodsAmount(sendGoodsGoods.getAmount());
		qsd.setSendDate(sendGoods.getSendDate());
		return qsd;
	}
	
	public Object findPlanSum(String warehouseName, String goodsNameSpecification) {
		return planStockDetailDao.findPlanSum(warehouseName, goodsNameSpecification);
	}
}
