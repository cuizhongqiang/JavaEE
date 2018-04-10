package com.cnbmtech.system.controller;

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
import com.cnbmtech.base.utils.bootstrap.TreeView;
import com.cnbmtech.system.entity.Role;
import com.cnbmtech.system.service.RoleService;

@Controller
@RequestMapping(value = "role")
public class RoleController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request, Model model) {
		return "system/roleList";
	}
	
	@RequestMapping(value = "jsonDate")
	@ResponseBody
	public Map<String, Object> jsonDate(HttpServletRequest request, Model model) {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		Page<Role> page = roleService.findPage(request, filters);
		return getTableData(page);
	}
	
	@RequestMapping(value = "toCreate")
	public String toUpdate(Model model) {
		model.addAttribute("action", "create");
		model.addAttribute("role", new Role());
		return "system/roleForm";
	}
	
	@RequestMapping(value = "toUpdate/{action}/{id}")
    public String toUpdate(@PathVariable("action") String action, @PathVariable("id") Long id, Model model) {
		Role role = roleService.get(id);
		model.addAttribute("role", role);
		return "system/roleForm";
	}
	
	@RequestMapping(value = "save/{action}")
	@ResponseBody
	public String update(@PathVariable("action") String action, @ModelAttribute("role") @RequestBody Role role) {
		try{
			Date currentDate = new Date();
			if (action.equals("create")) {
				role.setCreateDate(currentDate);
			} else {
				role.setUpdateDate(currentDate);
			}
			roleService.save(role);
		}catch(Exception ex){
			logger.error("save role()--error", ex);
			return "error";
		}
		return "success";
	}
	
	@ModelAttribute
	public void getObject(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("role", roleService.get(id));
		}
	}
	
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		roleService.delete(id);
		return "success";
	}
	
	@RequestMapping(value = "getTreeView/{roleId}", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<TreeView> getTreeView(@PathVariable("roleId") Long roleId) {
		return roleService.getTreeView(roleId);
	}
	
	@RequestMapping(value = "permissionSave")
	@ResponseBody
	public String permissionSave(HttpServletRequest request) {
		roleService.permissionSave(request);
		return "success";
	}

}
