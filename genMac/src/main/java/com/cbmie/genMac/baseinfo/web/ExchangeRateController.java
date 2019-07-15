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
import com.cbmie.genMac.baseinfo.entity.ExchangeRate;
import com.cbmie.genMac.baseinfo.service.ExchangeRateService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 汇率controller
 */
@Controller
@RequestMapping("baseinfo/exchangerate")
public class ExchangeRateController extends BaseController{
	
	
	@Autowired
	private ExchangeRateService exchangeRateService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "baseinfo/exchangeRateList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> exchangeRateList(HttpServletRequest request) {
		Page<ExchangeRate> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = exchangeRateService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取list
	 */
	@RequestMapping(value="filter",method = RequestMethod.GET)
	@ResponseBody
	public List<ExchangeRate> filter(HttpServletRequest request) {
		return exchangeRateService.search(PropertyFilter.buildFromHttpRequest(request));
	}
	
	/**
	 * 添加汇率跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("exchangeRate", new ExchangeRate());
		model.addAttribute("action", "create");
		return "baseinfo/exchangeRateForm";
	}

	/**
	 * 添加汇率
	 * 
	 * @param exchangeRate
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid ExchangeRate exchangeRate, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		exchangeRate.setCreaterNo(currentUser.getLoginName());
		exchangeRate.setCreaterName(currentUser.getName());
		exchangeRate.setCreateDate(new Date());
		exchangeRate.setCreaterDept(currentUser.getOrganization().getOrgName());
		exchangeRateService.save(exchangeRate);
		return "success";
	}

	/**
	 * 修改汇率跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("exchangeRate", exchangeRateService.get(id));
		model.addAttribute("action", "update");
		return "baseinfo/exchangeRateForm";
	}

	/**
	 * 修改汇率
	 * 
	 * @param exchangeRate
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody ExchangeRate exchangeRate,Model model) {
		User currentUser = UserUtil.getCurrentUser();
		exchangeRate.setUpdaterNo(currentUser.getLoginName());
		exchangeRate.setUpdaterName(currentUser.getName());
		exchangeRate.setUpdateDate(new Date());
		exchangeRateService.update(exchangeRate);
		return "success";
	}

	/**
	 * 删除汇率
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		exchangeRateService.delete(id);
		return "success";
	}
	
	/**
	 * 汇率明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("exchangeRate", exchangeRateService.get(id));
		return "baseinfo/exchangeRateDetail";
	}
	
	@ModelAttribute
	public void getExchangeRate(@RequestParam(value = "id", defaultValue = "-1") Long id,Model model) {
		if (id != -1) {
			model.addAttribute("exchangeRate", exchangeRateService.get(id));
		}
	}
	
	/**
	 * 获取所有币种的最新汇率
	 */
	@RequestMapping(value = "getNewExchangeRate", method = RequestMethod.GET)
	@ResponseBody
	public List<ExchangeRate> getNewExchangeRate() {
		return exchangeRateService.getNewExchangeRate();
	}
	
	/**
	 * 获取该币种的最新汇率信息
	 */
	@RequestMapping(value = "getThisNewExchangeRate/{currency}", method = RequestMethod.GET)
	@ResponseBody
	public ExchangeRate getThisNewExchangeRate(@PathVariable("currency") String currency) {
		return exchangeRateService.getThisNewExchangeRate(currency);
	}
	
}
