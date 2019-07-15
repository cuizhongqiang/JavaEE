package com.cbmie.genMac.agent.web;

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
import com.cbmie.genMac.agent.entity.AgentImportSupplement;
import com.cbmie.genMac.agent.service.AgentImportSupplementService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 代理进口补充controller
 */
@Controller
@RequestMapping("agent/agentImportSupplement")
public class AgentImportSupplementController extends BaseController{
	
	@Autowired
	private AgentImportSupplementService agentImportSupplementService;
	
	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> agentImportList(HttpServletRequest request) {
		Page<AgentImportSupplement> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = agentImportSupplementService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取list
	 */
	@RequestMapping(value="filter",method = RequestMethod.GET)
	@ResponseBody
	public List<AgentImportSupplement> filter(HttpServletRequest request) {
		return agentImportSupplementService.search(PropertyFilter.buildFromHttpRequest(request));
	}
	
	/**
	 * 跳转
	 */
	@RequestMapping(value = "supplement/{pid}", method = RequestMethod.GET)
	public String createForm(@PathVariable("pid") Long pid, Model model) {
		return "agent/agentImportSupplementForm";
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid AgentImportSupplement agentImportSupplement, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		agentImportSupplement.setCreaterNo(currentUser.getLoginName());
		agentImportSupplement.setCreaterName(currentUser.getName());
		agentImportSupplement.setCreaterDept(currentUser.getOrganization().getOrgName());
		agentImportSupplement.setCreateDate(new Date());
		agentImportSupplementService.save(agentImportSupplement);
		return "success";
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody AgentImportSupplement agentImportSupplement, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		agentImportSupplement.setUpdaterNo(currentUser.getLoginName());
		agentImportSupplement.setUpdaterName(currentUser.getName());
		agentImportSupplement.setCreaterDept(currentUser.getOrganization().getOrgName());
		agentImportSupplement.setUpdateDate(new Date());
		agentImportSupplementService.update(agentImportSupplement);
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
		agentImportSupplementService.delete(id);
		return "success";
	}
	
	@ModelAttribute
	public void getAgentImport(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("agentImportSupplement", agentImportSupplementService.get(id));
		}
	}
	
	/**
	 * 验证编码唯一性
	 */
	@RequestMapping(value = "uniqueNo/{id}/{no}", method = RequestMethod.GET)
	@ResponseBody
	public boolean uniqueNo(@PathVariable("id") Long id, @PathVariable("no") String no) {
		return agentImportSupplementService.findByNo(id, no) != null;
	}
	
	/**
	 * 构造编号
	 */
	@RequestMapping(value = "generateCode/{pid}", method = RequestMethod.GET)
	@ResponseBody
	public String generateCode(@PathVariable("pid") Long pid) {
		return agentImportSupplementService.generateCode(pid);
	}
	
}
