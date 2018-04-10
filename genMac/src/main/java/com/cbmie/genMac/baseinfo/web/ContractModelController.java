package com.cbmie.genMac.baseinfo.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.genMac.accessory.entity.Accessory;
import com.cbmie.genMac.accessory.service.AccessoryService;

/**
 * 合同函电controller
 */
@Controller
@RequestMapping("baseinfo/contracts")
public class ContractModelController extends BaseController{
	
	@Autowired
	private AccessoryService accessoryService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "baseinfo/contractModelList";
	}
	
	/**
	 * 获取json 
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> AccessoryList(HttpServletRequest request) {
		Page<Accessory> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = accessoryService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取list
	 */
	@RequestMapping(value="filter",method = RequestMethod.GET)
	@ResponseBody
	public List<Accessory> filter(HttpServletRequest request) {
		return accessoryService.search(PropertyFilter.buildFromHttpRequest(request));
	}
	
	/**
	 * 上传附件页面跳转
	 */
	@RequestMapping(value = "toUpload/{pid}", method = RequestMethod.GET)
	public String toUpload(@PathVariable("pid") String pid) {
		return "baseinfo/contractModelForm";
	}
	
}
