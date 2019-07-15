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
import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.web.BaseController;
import com.cbmie.genMac.stock.entity.InStock;
import com.cbmie.genMac.stock.service.InStockGoodsService;
import com.cbmie.genMac.stock.service.InStockService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;


/**
 * 入库controller
 */
@Controller
@RequestMapping("stock/inStock")
public class InStockController extends BaseController {


	@Autowired
	private InStockService inStockService;
	
	@Autowired
	private InStockGoodsService inStockGoodsService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "stock/inStockList";
	}

	/**
	 * 获取入库json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> inStockList(HttpServletRequest request) {
		Page<InStock> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = inStockService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 获取list
	 */
	@RequestMapping(value="filter",method = RequestMethod.GET)
	@ResponseBody
	public List<InStock> filter(HttpServletRequest request) {
		return inStockService.search(PropertyFilter.buildFromHttpRequest(request));
	}
	
	/**
	 * 添加入库跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("inStock", new InStock());
		model.addAttribute("action", "create");
		return "stock/inStockForm";
	}

	/**
	 * 添加入库
	 * 
	 * @param inStock
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid InStock inStock, Model model,@RequestParam("inStockGoodsJson") String inStockGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		inStock.setCreaterNo(currentUser.getLoginName());
		inStock.setCreaterName(currentUser.getName());
		inStock.setCreateDate(new Date());
		inStock.setCreaterDept(currentUser.getOrganization().getOrgName());
		inStockService.save(inStock);
		if(StringUtils.isNotBlank(inStockGoodsJson)){
			inStockGoodsService.save(inStock, inStockGoodsJson, currentUser);
		}
		return "success";
	}

	/**
	 * 修改入库跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("inStock", inStockService.get(id));
		model.addAttribute("action", "update");
		return "stock/inStockForm";
	}

	/**
	 * 修改入库
	 * 
	 * @param inStock
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody InStock inStock, Model model,@RequestParam("inStockGoodsJson") String inStockGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		inStock.setUpdaterNo(currentUser.getLoginName());
		inStock.setUpdaterName(currentUser.getName());
		inStock.setUpdateDate(new Date());
		if(StringUtils.isNotBlank(inStockGoodsJson)){
			inStockGoodsService.save(inStock, inStockGoodsJson, currentUser);
		}
		inStockService.update(inStock);
		return "success";
	}
	
	/**
	 * 删除入库
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		inStockService.delete(id);
		return "success";
	}
	
	/**
	 * 查看入库明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("inStock", inStockService.get(id));
		return "stock/inStockDetail";
	}
	
	@ModelAttribute
	public void getInStock(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("inStock", inStockService.get(id));
		}
	}
	
	/**
	 * 验证入库单号唯一性
	 */
	@RequestMapping(value = "uniqueNo/{id}/{no}", method = RequestMethod.GET)
	@ResponseBody
	public boolean uniqueNo(@PathVariable("id") Long id, @PathVariable("no") String no) {
		return inStockService.findByNo(id, no) != null;
	}
	
	/**
	 * 构造编号
	 */
	@RequestMapping(value = "generateCode", method = RequestMethod.GET)
	@ResponseBody
	public String generateCode(@RequestParam("contractNo") String contractNo, @RequestParam("documentType") String documentType) {
		return inStockService.generateCode(contractNo, documentType);
	}
	
}
