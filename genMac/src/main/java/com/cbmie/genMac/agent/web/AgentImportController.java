package com.cbmie.genMac.agent.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.activiti.web.ActivitiController;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.genMac.agent.entity.AgentImport;
import com.cbmie.genMac.agent.service.AgentImportGoodsService;
import com.cbmie.genMac.agent.service.AgentImportService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 代理进口controller
 */
@Controller
@RequestMapping("agent/agentImport")
public class AgentImportController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AgentImportService agentImportService;
	
	@Autowired
	private AgentImportGoodsService agentImportGoodsService;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private ActivitiController activitiController;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "agent/agentImportList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> agentImportList(HttpServletRequest request) {
		Page<AgentImport> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = agentImportService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取list
	 */
	@RequestMapping(value="filter",method = RequestMethod.GET)
	@ResponseBody
	public List<AgentImport> filter(HttpServletRequest request) {
		return agentImportService.search(PropertyFilter.buildFromHttpRequest(request));
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("agentImport", new AgentImport());
		model.addAttribute("action", "create");
		return "agent/agentImportForm";
	}

	/**
	 * 添加
	 * 
	 * @param agentImport
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid AgentImport agentImport, Model model,
			@RequestParam("agentImportGoodsJson") String agentImportGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		agentImport.setCreaterNo(currentUser.getLoginName());
		agentImport.setCreaterName(currentUser.getName());
		agentImport.setCreaterDept(currentUser.getOrganization().getOrgName());
		agentImport.setCreateDate(new Date());
		agentImport.setSummary("代理进口[" + agentImport.getContractNo() + "]");
		agentImportService.saveAffiBaseInfo(agentImport, currentUser);
		agentImportService.save(agentImport);
		agentImportGoodsService.save(agentImport, agentImportGoodsJson, currentUser);
		return agentImport.getId().toString();
	}

	/**
	 * 修改跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("agentImport", agentImportService.get(id));
		model.addAttribute("action", "update");
		return "agent/agentImportForm";
	}

	/**
	 * 修改
	 * 
	 * @param agentImport
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody AgentImport agentImport, Model model,
			@RequestParam("agentImportGoodsJson") String agentImportGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		agentImport.setUpdaterNo(currentUser.getLoginName());
		agentImport.setUpdaterName(currentUser.getName());
		agentImport.setCreaterDept(currentUser.getOrganization().getOrgName());
		agentImport.setUpdateDate(new Date());
		agentImport.setSummary("代理进口[" + agentImport.getContractNo() + "]");
		agentImportService.updateAffiBaseInfo(agentImport, currentUser);
		agentImportGoodsService.save(agentImport, agentImportGoodsJson, currentUser);
		agentImportService.update(agentImport);
		return agentImport.getId().toString();
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
		agentImportService.delete(id);
		return "success";
	}
	
	/**
	 * 查看明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("agentImport", agentImportService.get(id));
		return "agent/agentImportDetail";
	}
	
	@ModelAttribute
	public void getAgentImport(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("agentImport", agentImportService.get(id));
		}
	}
	
	/**
	 * 目前敞口
	 */
	@RequestMapping(value = "currentOpen/{customerId}", method = RequestMethod.GET)
	@ResponseBody
	public Double currentOpen(@PathVariable("customerId") Long customerId, Model model) {
		return agentImportService.currentOpen(customerId);
	}
	
	/**
	 * 已超期金额
	 */
	@RequestMapping(value = "hasBeyond/{customerId}", method = RequestMethod.GET)
	@ResponseBody
	public Double hasBeyond(@PathVariable("customerId") Long customerId, Model model) {
		return agentImportService.hasBeyond(customerId);
	}
	
	/**
	 * 验证编码唯一性
	 */
	@RequestMapping(value = "uniqueNo/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean uniqueNo(@PathVariable("id") Long id, @RequestParam("contractNo") String contractNo,
			@RequestParam("foreignContractNo") String foreignContractNo) {
		return agentImportService.findByNo(id, contractNo, foreignContractNo) != null;
	}
	
	/**
	 * 构造编号
	 */
	@RequestMapping(value = "generateCode", method = RequestMethod.GET)
	@ResponseBody
	public String generateCode(@RequestParam("customerType") String customerType,
			@RequestParam("customer") String customer,
			@RequestParam("documentType") String documentType) {
		return agentImportService.generateCode(customerType, customer, documentType);
	}
	
	/**
	 * 找出满足可申请开证的进口合同
	 */
	@RequestMapping(value = "findOpenCredit/{type}", method = RequestMethod.GET)
	@ResponseBody
	public List<AgentImport> findOpenCredit(@PathVariable("type") String type){
		return agentImportService.findOpenCredit(type);
	}
	
	/**
	 * 找出满足到单的进口合同
	 */
	@RequestMapping(value = "findInvoiceReg", method = RequestMethod.GET)
	@ResponseBody
	public List<AgentImport> findInvoiceReg(){
		return agentImportService.findInvoiceReg();
	}
	
	/**
	 * 提交流程申请
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "apply/{id}")
	public String apply(@PathVariable("id") Long id, Model model) {
		try {
			User user = UserUtil.getCurrentUser();
			AgentImport agentImport = agentImportService.get(id);
			agentImport.setUserId(user.getLoginName());
			agentImport.setState("已提交");
			agentImportService.save(agentImport);
			Map<String, Object> variables = new HashMap<String, Object>();
			ProcessInstance processInstance = activitiService.startWorkflow(agentImport, "wf_agentImport", variables, user.getLoginName());//提交流程
			String taskId = activitiService.findTaskListByKey(processInstance.getId(), processInstance.getActivityId()).get(0).getId();
			activitiService.doClaim(taskId);//签收
			activitiController.approvalForm(processInstance.getId(), taskId, agentImport.getId(), "wf_agentImport", model);//处理参数
		} catch (ActivitiException e) {
			if (e.getMessage().indexOf("no processes deployed with key") != -1) {
				logger.warn("没有部署流程!", e);
			} else {
				logger.error("启动流程失败：", e);
			}
		} catch (Exception e) {
			logger.error("启动流程失败：", e);
		}
		return "activiti/submitApplication";
	}
	
	/**
	 * 撤回流程申请
	 * @return
	 */
	@RequestMapping(value = "callBack/{id}/{processInstanceId}")
	@ResponseBody
	public String callBack(@PathVariable("id") Long id, @PathVariable("processInstanceId") String processInstanceId) {
		try {
			if (activitiService.deleteProcessInstance(processInstanceId, false)) {
				AgentImport agentImport = agentImportService.get(id);
				agentImport.setProcessInstanceId(null);
				agentImport.setState("草稿");
				agentImportService.save(agentImport);
				return "success";
			} else {
				return "已被签收，不能撤回！";
			}
		} catch (Exception e) {
			logger.error("撤回申请失败：", e);
			return "撤回申请失败！";
		}
	}
	
}
