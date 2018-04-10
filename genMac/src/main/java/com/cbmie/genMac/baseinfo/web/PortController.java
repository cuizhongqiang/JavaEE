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
import com.cbmie.genMac.baseinfo.entity.Port;
import com.cbmie.genMac.baseinfo.service.PortService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 港口controller
 */
@Controller
@RequestMapping("baseinfo/port")
public class PortController extends BaseController{
	
	
	@Autowired
	private PortService portService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "baseinfo/portList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> portList(HttpServletRequest request) {
		Page<Port> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = portService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取list
	 */
	@RequestMapping(value="filter",method = RequestMethod.GET)
	@ResponseBody
	public List<Port> filter(HttpServletRequest request) {
		return portService.search(PropertyFilter.buildFromHttpRequest(request));
	}
	
	/**
	 * 添加港口跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("port", new Port());
		model.addAttribute("action", "create");
		return "baseinfo/portForm";
	}

	/**
	 * 添加港口
	 * 
	 * @param port
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Port port, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		port.setCreaterNo(currentUser.getLoginName());
		port.setCreaterName(currentUser.getName());
		port.setCreateDate(new Date());
		port.setCreaterDept(currentUser.getOrganization().getOrgName());
		portService.save(port);
		return "success";
	}

	/**
	 * 修改港口跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("port", portService.get(id));
		model.addAttribute("action", "update");
		return "baseinfo/portForm";
	}

	/**
	 * 修改港口
	 * 
	 * @param port
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody Port port,Model model) {
		User currentUser = UserUtil.getCurrentUser();
		port.setUpdaterNo(currentUser.getLoginName());
		port.setUpdaterName(currentUser.getName());
		port.setUpdateDate(new Date());
		portService.update(port);
		return "success";
	}

	/**
	 * 删除港口
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		portService.delete(id);
		return "success";
	}
	
	/**
	 * 港口明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("port", portService.get(id));
		return "baseinfo/portDetail";
	}
	
	@ModelAttribute
	public void getPort(@RequestParam(value = "id", defaultValue = "-1") Long id,Model model) {
		if (id != -1) {
			model.addAttribute("port", portService.get(id));
		}
	}
	
}
