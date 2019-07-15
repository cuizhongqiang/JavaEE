package com.cbmie.genMac.stock.web;

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
import com.cbmie.genMac.stock.entity.PlanStock;
import com.cbmie.genMac.stock.service.PlanStockDetailService;
import com.cbmie.genMac.stock.service.PlanStockService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 盘库controller
 */
@Controller
@RequestMapping("stock/planStock")
public class PlanStockController extends BaseController {


	@Autowired
	private PlanStockService planStockService;
	
	@Autowired
	private PlanStockDetailService planStockDetailService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "stock/planStockList";
	}

	/**
	 * 获取盘库json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> planStockList(HttpServletRequest request) {
		Page<PlanStock> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = planStockService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取list
	 */
	@RequestMapping(value="filter",method = RequestMethod.GET)
	@ResponseBody
	public List<PlanStock> filter(HttpServletRequest request) {
		return planStockService.search(PropertyFilter.buildFromHttpRequest(request));
	}
	
	/**
	 * 添加跳转
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("planStock", new PlanStock());
		model.addAttribute("action", "create");
		return "stock/planStockForm";
	}
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@RequestParam("planStockDetailJson") String planStockDetailJson) {
		PlanStock planStock = new PlanStock();
		User currentUser = UserUtil.getCurrentUser();
		planStock.setCreaterNo(currentUser.getLoginName());
		planStock.setCreaterName(currentUser.getName());
		planStock.setCreateDate(new Date());
		planStock.setCreaterDept(currentUser.getOrganization().getOrgName());
		planStockService.save(planStock);
		planStockDetailService.save(planStock, planStockDetailJson, currentUser);
		return "success";
	}
	
	/**
	 * 修改跳转
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("planStock", planStockService.get(id));
		model.addAttribute("action", "update");
		return "stock/planStockForm";
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody PlanStock planStock, Model model, 
			@RequestParam("planStockDetailJson") String planStockDetailJson) {
		User currentUser = UserUtil.getCurrentUser();
		planStock.setUpdaterNo(currentUser.getLoginName());
		planStock.setUpdaterName(currentUser.getName());
		planStock.setUpdateDate(new Date());
		planStockDetailService.save(planStock, planStockDetailJson, currentUser);
		planStockService.update(planStock);
		return "success";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		planStockService.delete(id);
		return "success";
	}
	
	/**
	 * 查看入库明细跳转
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("planStock", planStockService.get(id));
		return "stock/planStockDetail";
	}
	
	@ModelAttribute
	public void getPlanStockDetail(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("planStock", planStockService.get(id));
		}
	}
	
	/**
	 * 根据仓库名称获取商品目录
	 */
	@RequestMapping(value = "findGoodsForWarehouse/{warehouseName}", method = RequestMethod.GET)
	@ResponseBody
	public List<String> findGoodsForWarehouse(@PathVariable("warehouseName") String warehouseName) {
		return planStockDetailService.findGoodsForWarehouse(warehouseName);
	}
	
	/**
	 * 根据仓库名称和商品获取账面数
	 */
	@RequestMapping(value = "findGoodsSumForWarehouse", method = RequestMethod.GET)
	@ResponseBody
	public Double findGoodsSumForWarehouse(@RequestParam("warehouseName") String warehouseName, 
			@RequestParam("goodsNameSpecification") String goodsNameSpecification, 
			@RequestParam("id") Long id) {
		return planStockDetailService.findGoodsSumForWarehouse(warehouseName, goodsNameSpecification, id);
	}
	
}
