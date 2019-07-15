package com.cbmie.genMac.baseinfo.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.baseinfo.dao.WarehouseDao;
import com.cbmie.genMac.baseinfo.entity.Warehouse;
import com.cbmie.genMac.baseinfo.entity.WarehouseGoods;
import com.cbmie.genMac.stock.entity.InStock;
import com.cbmie.genMac.stock.entity.InStockGoods;
import com.cbmie.genMac.utils.DocumentCode;

/**
 * 仓库service
 */
@Service
@Transactional(readOnly = true)
public class WarehouseService extends BaseService<Warehouse, Long> {

	@Autowired
	private WarehouseDao warehouseDao;
	
	@Autowired
	private DocumentCode documentCode;

	@Override
	public HibernateDao<Warehouse, Long> getEntityDao() {
		return warehouseDao;
	}
	
	/**
	 * 仓库下商品列表
	 * @param inStockList 入库单集合
	 * @param warehouseName 仓库名称
	 * @return
	 */
	public List<WarehouseGoods> getWarehouseGoods(List<InStock> inStockList, String warehouseName) {
		List<WarehouseGoods> returnList = new ArrayList<WarehouseGoods>();
		try {
			for(InStock inStock : inStockList){
				if(inStock.getStorageUnit().equals(warehouseName)){
					for(InStockGoods inStockGoods : inStock.getInStockGoods()){
						WarehouseGoods warehouseGoods = new WarehouseGoods();
						BeanUtils.copyProperties(warehouseGoods, inStock);
						BeanUtils.copyProperties(warehouseGoods, inStockGoods);
						warehouseGoods.setNote(inStockGoods.getGoodsCategory() + "/" + inStockGoods.getSpecification());
						returnList.add(warehouseGoods);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 构造编号
	 * @param 企业名称
	 * @return
	 */
	public String generateCode(String enterpriseName) {
		try {
			//客户识别号
			String str = documentCode.customerNum(enterpriseName);
			//顺序自编号
			str += documentCode.getOrderNum(Warehouse.class, "warehouseCode", str);
			return str;
		} catch (Exception e) {
			return "生成编码失败！";
		}
	}
	
	public Warehouse findByNo(Long id, String no){
		return warehouseDao.findByNo(id, no);
	}

}
