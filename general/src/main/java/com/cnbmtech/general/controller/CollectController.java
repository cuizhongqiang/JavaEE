package com.cnbmtech.general.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnbmtech.base.controller.BaseController;
import com.cnbmtech.base.utils.PropertyFilter;
import com.cnbmtech.general.entity.Collect;
import com.cnbmtech.general.service.CollectService;

@Controller
@RequestMapping(value = "collect")
public class CollectController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(CollectController.class);
	
	@Autowired
	private CollectService collectService;
	
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request, Model model) {
		return "meeting/collectList";
	}
	
	@RequestMapping(value = "jsonDate")
	@ResponseBody
	public Map<String, Object> jsonDate(HttpServletRequest request, Model model) {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		Page<Collect> page = collectService.findPage(request, filters);
		return getTableData(page);
	}
	
	@RequestMapping(value = "toCreate")
	public String toUpdate(Model model) {
		model.addAttribute("action", "create");
		model.addAttribute("collect", new Collect());
		return "meeting/collectForm";
	}
	
	@RequestMapping(value = "toUpdate/{action}/{id}")
    public String toUpdate(@PathVariable("action") String action, @PathVariable("id") Long id, Model model) {
		Collect collect = collectService.get(id);
		model.addAttribute("collect", collect);
		return "meeting/collectForm";
	}
	
	@RequestMapping(value = "save/{action}")
	@ResponseBody
	public String update(@PathVariable("action") String action, @ModelAttribute("collect") @RequestBody Collect collect) {
		try{
			Date currentDate = new Date();
			if (action.equals("create")) {
				collect.setCreateDate(currentDate);
			} else {
				collect.setUpdateDate(currentDate);
			}
			collectService.save(collect);
		}catch(Exception ex){
			logger.error("save collect()--error", ex);
			return "error";
		}
		return "success";
	}
	
	@ModelAttribute
	public void getObject(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("collect", collectService.get(id));
		}
	}
	
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		collectService.delete(id);
		return "success";
	}
	
	@RequestMapping(value = "uniqueCode")
	@ResponseBody
	public Map<String, Boolean> uniqueCode(@RequestParam(name = "code") String code, @RequestParam(name = "id") Long id) {
		Integer count = collectService.uniqueCode(code, id);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("valid", count == 0);
		return map;
	}
	
}
