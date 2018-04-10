package com.cbmie.activiti.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.activiti.entity.Encyclic;
import com.cbmie.activiti.service.EncyclicService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;

/**
 * 传阅controller
 */
@Controller
@RequestMapping("workflow/encyclic")
public class EncyclicController extends BaseController{

	@Autowired
	private EncyclicService encyclicService;

	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> encyclicList(HttpServletRequest request) {
		Page<Encyclic> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = encyclicService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute Encyclic encyclic, Model model) {
		encyclic.setState(1);
		encyclicService.update(encyclic);
		return "success";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		encyclicService.delete(id);
		return "success";
	}
	
	@ModelAttribute
	public void getCountry(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
		if (id != -1) {
			model.addAttribute("encyclic", encyclicService.get(id));
		}
	}
	
}
