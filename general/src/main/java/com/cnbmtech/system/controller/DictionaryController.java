package com.cnbmtech.system.controller;

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
import com.cnbmtech.system.entity.Dictionary;
import com.cnbmtech.system.service.DictionaryService;

@Controller
@RequestMapping(value = "dictionary")
public class DictionaryController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);
	
	@Autowired
	private DictionaryService dictionaryService;
	
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request, Model model) {
		return "system/dictionaryList";
	}
	
	@RequestMapping(value = "jsonDate")
	@ResponseBody
	public Map<String, Object> jsonDate(HttpServletRequest request, Model model) {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		Page<Dictionary> page = dictionaryService.findPage(request, filters);
		return getTableData(page);
	}
	
	@RequestMapping(value = "toCreate")
	public String toUpdate(@ModelAttribute("dictionary") @RequestBody Dictionary dictionary, Model model) {
		model.addAttribute("action", "create");
		model.addAttribute("dictionary", dictionary);
		return "system/dictionaryForm";
	}
	
	@RequestMapping(value = "toUpdate/{action}/{id}")
    public String toUpdate(@PathVariable("action") String action, @PathVariable("id") Long id, Model model) {
		Dictionary dictionary = dictionaryService.get(id);
		model.addAttribute("dictionary", dictionary);
		return "system/dictionaryForm";
	}
	
	@RequestMapping(value = "save/{action}")
	@ResponseBody
	public String update(@PathVariable("action") String action, @ModelAttribute("dictionary") @RequestBody Dictionary dictionary) {
		try{
			Date currentDate = new Date();
			if (action.equals("create")) {
				dictionary.setCreateDate(currentDate);
			} else {
				dictionary.setUpdateDate(currentDate);
			}
			dictionaryService.save(dictionary);
			if (dictionary.getPid() != null && dictionary.getPid() > 0) {
				dictionaryService.findByPcodeCachePut(dictionaryService.get(dictionary.getPid()).getCode());
			}
		}catch(Exception ex){
			logger.error("save dictionary()--error", ex);
			return "error";
		}
		return "success";
	}
	
	@ModelAttribute
	public void getObject(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("dictionary", dictionaryService.get(id));
		}
	}
	
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		Dictionary dictionary = dictionaryService.get(id);
		if (dictionary.getPid() != null && dictionary.getPid() > 0) {
			dictionaryService.removeCache(dictionaryService.get(dictionary.getPid()).getCode());
		}
		dictionaryService.delete(id);
		return "success";
	}
	
	@RequestMapping(value = "uniqueCode")
	@ResponseBody
	public Map<String, Boolean> uniqueCode(@RequestParam(name = "code") String code, @RequestParam(name = "id") Integer id) {
		Integer count = dictionaryService.uniqueCode(code, id);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("valid", count == 0);
		return map;
	}
	
}
