package com.cbmie.system.web;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.system.entity.Inform;
import com.cbmie.system.service.InformService;

/**
 * 通知controller
 */
@Controller
@RequestMapping("system/inform")
public class InformController extends BaseController{
	
	@Autowired
	private InformService informService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "system/informList";
	}

	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> dictList(HttpServletRequest request) {
		Page<Inform> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = informService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 查看跳转
	 */
	@RequestMapping(value="detail/{id}",method = RequestMethod.GET)
	public String detail(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("inform", informService.get(id));
		return "system/informDetail";
	}
	
	/**
	 * 添加跳转
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("inform", new Inform());
		model.addAttribute("action", "create");
		return "system/dictForm";
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Inform inform, Model model) {
		informService.save(inform);
		return "success";
	}

	/**
	 * 修改跳转
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("inform", informService.get(id));
		model.addAttribute("action", "update");
		return "system/dictForm";
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody Inform inform, Model model) {
		informService.update(inform);
		return "success";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		informService.delete(id);
		return "success";
	}
	
}
