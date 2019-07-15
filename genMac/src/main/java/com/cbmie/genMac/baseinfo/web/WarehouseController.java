package com.cbmie.genMac.baseinfo.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.genMac.baseinfo.entity.Warehouse;
import com.cbmie.genMac.baseinfo.entity.WarehouseGoods;
import com.cbmie.genMac.baseinfo.service.WarehouseService;
import com.cbmie.genMac.stock.entity.InStock;
import com.cbmie.genMac.stock.service.InStockService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 仓库controller
 */
@Controller
@RequestMapping("baseinfo/warehouse")
public class WarehouseController extends BaseController{
	
	
	@Autowired
	private WarehouseService warehouseService;
	
	@Autowired
	private InStockService inStockService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "baseinfo/warehouseList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> warehouseList(HttpServletRequest request) {
		Page<Warehouse> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = warehouseService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取list
	 */
	@RequestMapping(value="filter",method = RequestMethod.GET)
	@ResponseBody
	public List<Warehouse> filter(HttpServletRequest request) {
		return warehouseService.search(PropertyFilter.buildFromHttpRequest(request));
	}
	
	/**
	 * 添加仓库跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("warehouse", new Warehouse());
		model.addAttribute("action", "create");
		return "baseinfo/warehouseForm";
	}

	/**
	 * 添加仓库
	 * 
	 * @param warehouse
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Warehouse warehouse, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		warehouse.setCreaterNo(currentUser.getLoginName());
		warehouse.setCreaterName(currentUser.getName());
		warehouse.setCreateDate(new Date());
		warehouse.setCreaterDept(currentUser.getOrganization().getOrgName());
		warehouseService.save(warehouse);
		return "success";
	}

	/**
	 * 修改仓库跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("warehouse", warehouseService.get(id));
		model.addAttribute("action", "update");
		return "baseinfo/warehouseForm";
	}
	
	/**
	 * 仓库下的商品明细
	 */
	@RequestMapping(value = "warehouseGoodsDetail/{warehouseName}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> warehouseGoodsDetail(@PathVariable("warehouseName") String warehouseName, HttpServletRequest request) {
		Page<WarehouseGoods> page = getPage(request);
		List<PropertyFilter> filterList = PropertyFilter.buildFromHttpRequest(request);
		List<InStock> inStockList = inStockService.search(filterList);
		List<WarehouseGoods> warehouseGoodsList = warehouseService.getWarehouseGoods(inStockList, warehouseName);
		createPageByResult(page, warehouseGoodsList);
		return getEasyUIData(page);
	}

	/**
	 * 修改仓库
	 * 
	 * @param warehouse
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody Warehouse warehouse,Model model) {
		User currentUser = UserUtil.getCurrentUser();
		warehouse.setUpdaterNo(currentUser.getLoginName());
		warehouse.setUpdaterName(currentUser.getName());
		warehouse.setUpdateDate(new Date());
		warehouseService.update(warehouse);
		return "success";
	}

	/**
	 * 删除仓库
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		warehouseService.delete(id);
		return "success";
	}
	
	/**
	 * 仓库明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("warehouse", warehouseService.get(id));
		return "baseinfo/warehouseDetail";
	}
	
	/**
	 * 根据仓库id获取仓库名称
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getWarehouseName/{id}")
	@ResponseBody
	public String getWarehouseName(@PathVariable("id") Long id) {
		return warehouseService.get(id).getWarehouseName();
	}
	
	/**
	 * 构造编号
	 */
	@RequestMapping(value = "generateCode", method = RequestMethod.GET)
	@ResponseBody
	public String generateCode(@RequestParam("enterpriseName") String enterpriseName) {
		return warehouseService.generateCode(enterpriseName);
	}
	
	/**
	 * 验证仓库编码唯一性
	 */
	@RequestMapping(value = "uniqueNo/{id}/{no}", method = RequestMethod.GET)
	@ResponseBody
	public boolean uniqueNo(@PathVariable("id") Long id, @PathVariable("no") String no) {
		return warehouseService.findByNo(id, no) != null;
	}
	
	@ModelAttribute
	public void getWarehouse(@RequestParam(value = "id", defaultValue = "-1") Long id,Model model) {
		if (id != -1) {
			model.addAttribute("warehouse", warehouseService.get(id));
		}
	}
	
}
