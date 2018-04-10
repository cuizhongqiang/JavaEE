package com.cnbmtech.general.controller;

import java.util.Date;
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
import com.cnbmtech.general.entity.BriefInfor;
import com.cnbmtech.general.service.BriefInforService;

@Controller
@RequestMapping(value = "briefInfor")
public class BriefInforController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(BriefInforController.class);
	
	@Autowired
	private BriefInforService briefInforService;
	
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request, Model model) {
		return "briefInfor/briefInforList";
	}
	
	@RequestMapping(value = "jsonDate")
	@ResponseBody
	public Map<String, Object> jsonDate(HttpServletRequest request, Model model) {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		Page<BriefInfor> page = briefInforService.findPage(request, filters);
		return getTableData(page);
	}
	
	@RequestMapping(value = "toCreate")
	public String toUpdate(Model model) {
		model.addAttribute("action", "create");
		model.addAttribute("briefInfor", new BriefInfor());
		return "briefInfor/briefInforForm";
	}
	
	@RequestMapping(value = "toUpdate/{action}/{id}")
    public String toUpdate(@PathVariable("action") String action, @PathVariable("id") Long id, Model model) {
		BriefInfor briefInfor = briefInforService.get(id);
		model.addAttribute("briefInfor", briefInfor);
		return "briefInfor/briefInforForm";
	}
	
	@RequestMapping(value = "save/{action}")
	@ResponseBody
	public String update(@PathVariable("action") String action, @ModelAttribute("briefInfor") @RequestBody BriefInfor briefInfor) {
		try{
			Date currentDate = new Date();
			if (action.equals("create")) {
				briefInfor.setCreateDate(currentDate);
			} else {
				briefInfor.setUpdateDate(currentDate);
			}
			briefInforService.save(briefInfor);
		}catch(Exception ex){
			logger.error("save briefInfor()--error", ex);
			return "error";
		}
		return "success";
	}
	
	
	
	@RequestMapping(value = "findAll")
	@ResponseBody
	public List<BriefInfor> findAll() {
		List<BriefInfor> briefInforList = briefInforService.getAll();
		return briefInforList;
	}
	
	
	@ModelAttribute
	public void getObject(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("briefInfor", briefInforService.get(id));
		}
	}
	
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		briefInforService.delete(id);
		return "success";
	}
	
}
