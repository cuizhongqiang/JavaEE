package com.cbmie.genMac.logistics.web;

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
import com.cbmie.genMac.logistics.entity.InvoiceReg;
import com.cbmie.genMac.logistics.service.InvoiceGoodsService;
import com.cbmie.genMac.logistics.service.InvoiceRegService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 到单登记controller
 */
@Controller
@RequestMapping("logistics/invoiceReg")
public class InvoiceRegController extends BaseController{
	
	@Autowired
	private InvoiceRegService invoiceRegService;
	
	@Autowired
	private InvoiceGoodsService invoiceGoodsService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "logistics/invoiceRegList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> invoiceRegList(HttpServletRequest request) {
		Page<InvoiceReg> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = invoiceRegService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取list
	 */
	@RequestMapping(value="filter",method = RequestMethod.GET)
	@ResponseBody
	public List<InvoiceReg> filter(HttpServletRequest request) {
		return invoiceRegService.search(PropertyFilter.buildFromHttpRequest(request));
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("invoiceReg", new InvoiceReg());
		model.addAttribute("action", "create");
		return "logistics/invoiceRegForm";
	}

	/**
	 * 添加
	 * 
	 * @param invoiceReg
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid InvoiceReg invoiceReg, Model model, @RequestParam("invoiceGoodsJson") String invoiceGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		invoiceReg.setCreaterNo(currentUser.getLoginName());
		invoiceReg.setCreaterName(currentUser.getName());
		invoiceReg.setCreaterDept(currentUser.getOrganization().getOrgName());
		invoiceReg.setCreateDate(new Date());
		invoiceRegService.save(invoiceReg);
		invoiceGoodsService.save(invoiceReg, invoiceGoodsJson, currentUser);
		return "success";
	}

	/**
	 * 修改跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("invoiceReg", invoiceRegService.get(id));
		model.addAttribute("action", "update");
		return "logistics/invoiceRegForm";
	}

	/**
	 * 修改
	 * 
	 * @param invoiceReg
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody InvoiceReg invoiceReg, Model model, @RequestParam("invoiceGoodsJson") String invoiceGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		invoiceReg.setUpdaterNo(currentUser.getLoginName());
		invoiceReg.setUpdaterName(currentUser.getName());
		invoiceReg.setCreaterDept(currentUser.getOrganization().getOrgName());
		invoiceReg.setUpdateDate(new Date());
		invoiceGoodsService.save(invoiceReg, invoiceGoodsJson, currentUser);
		invoiceRegService.update(invoiceReg);
		return "success";
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		invoiceRegService.delete(id);
		return "success";
	}
	
	/**
	 * 查看明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("invoiceReg", invoiceRegService.get(id));
		return "logistics/invoiceRegInfo";
	}
	
	@ModelAttribute
	public void getInvoiceReg(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("invoiceReg", invoiceRegService.get(id));
		}
	}
	
	/**
	 * 验证提单号唯一性
	 */
	@RequestMapping(value = "uniqueNo/{id}/{no}", method = RequestMethod.GET)
	@ResponseBody
	public boolean uniqueNo(@PathVariable("id") Long id, @PathVariable("no") String no) {
		return invoiceRegService.findByNo(id, no) != null;
	}
	
	/**
	 * 清关列表
	 */
	@RequestMapping(value = "customsDeclarationList", method = RequestMethod.GET)
	public String customsDeclaration() {
		return "logistics/customsDeclarationList";
	}
	
	/**
	 * 清关跳转
	 */
	@RequestMapping(value = "customsDeclaration/{id}", method = RequestMethod.GET)
	public String customsDeclaration(@PathVariable("id") Long id, Model model) {
		model.addAttribute("invoiceReg", invoiceRegService.get(id));
		model.addAttribute("action", "customsDeclaration");
		return "logistics/customsDeclarationForm";
	}
	
	/**
	 * 清关
	 */
	@RequestMapping(value = "customsDeclaration", method = RequestMethod.POST)
	@ResponseBody
	public String customsDeclaration(@Valid @ModelAttribute @RequestBody InvoiceReg invoiceReg) {
		invoiceRegService.update(invoiceReg);
		return "success";
	}
	
	/**
	 * 跟踪清关跳转
	 */
	@RequestMapping(value = "customsDeclarationTrace/{id}", method = RequestMethod.GET)
	public String customsDeclarationTrace(@PathVariable("id") Long id, Model model) {
		model.addAttribute("datascource", invoiceRegService.customsDeclarationTrace(id));
		return "logistics/customsDeclarationTrace";
	}
	
	/**
	 * 查询已经确认货代的到单(才可入库)
	 */
	@RequestMapping(value = "findHaveFreight/{inventoryWay}", method = RequestMethod.GET)
	@ResponseBody
	public List<InvoiceReg> findHaveFreight(@PathVariable("inventoryWay") String inventoryWay) {
		return invoiceRegService.findHaveFreight(inventoryWay);
	}
}
