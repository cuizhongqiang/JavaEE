package com.cbmie.genMac.domesticTrade.web;

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
import com.cbmie.genMac.domesticTrade.entity.AgentPurchase;
import com.cbmie.genMac.domesticTrade.service.AgentPurchaseGoodsService;
import com.cbmie.genMac.domesticTrade.service.AgentPurchaseService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 代理采购合同controller
 */
@Controller
@RequestMapping("domesticTrade/agentPurchase")
public class AgentPurchaseController extends BaseController{
		
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AgentPurchaseService agentPurchaseService;
	
	@Autowired
	private AgentPurchaseGoodsService agentPurchaseGoodsService;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private ActivitiController activitiController;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "domesticTrade/agentPurchaseList";
	}
	
	/**
	 * 获取json 
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> agentPurchaseList(HttpServletRequest request) {
		Page<AgentPurchase> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = agentPurchaseService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取list
	 */
	@RequestMapping(value="filter",method = RequestMethod.GET)
	@ResponseBody
	public List<AgentPurchase> filter(HttpServletRequest request) {
		return agentPurchaseService.search(PropertyFilter.buildFromHttpRequest(request));
	}
	
	/**
	 * 添加采购合同跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("agentPurchase", new AgentPurchase());
		model.addAttribute("action", "create");
		return "domesticTrade/agentPurchaseForm";
	}

	/**
	 * 添加代理采购合同
	 * 
	 * @param agentPurchase
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid AgentPurchase agentPurchase, Model model,
			@RequestParam("agentPurchaseGoodsJson") String agentPurchaseGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		agentPurchase.setCreaterNo(currentUser.getLoginName());
		agentPurchase.setCreaterName(currentUser.getName());
		agentPurchase.setCreateDate(new Date());
		agentPurchase.setCreaterDept(currentUser.getOrganization().getOrgName());
		agentPurchase.setSummary("（内贸）代理采购合同[" + agentPurchase.getContractNo() + "]");
		agentPurchaseService.save(agentPurchase);
		agentPurchaseGoodsService.save(agentPurchase, agentPurchaseGoodsJson, currentUser);
		return agentPurchase.getId().toString();
	}

	/**
	 * 修改代理采购合同跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("agentPurchase", agentPurchaseService.get(id));
		model.addAttribute("action", "update");
		return "domesticTrade/agentPurchaseForm";
	}

	/**
	 * 修改代理采购合同
	 * 
	 * @param port
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody AgentPurchase agentPurchase, Model model,
			@RequestParam("agentPurchaseGoodsJson") String agentPurchaseGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		agentPurchase.setUpdaterNo(currentUser.getLoginName());
		agentPurchase.setUpdaterName(currentUser.getName());
		agentPurchase.setUpdateDate(new Date());
		agentPurchase.setSummary("（内贸）代理采购合同[" + agentPurchase.getContractNo() + "]");
		agentPurchaseGoodsService.save(agentPurchase, agentPurchaseGoodsJson, currentUser);
		agentPurchaseService.update(agentPurchase);
		return agentPurchase.getId().toString();
	}

	/**
	 * 删除代理采购合同
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		agentPurchaseService.delete(id);
		return "success";
	}
	
	/**
	 * 代理采购合同明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("agentPurchase", agentPurchaseService.get(id));
		return "domesticTrade/agentPurchaseDetail";
	}
	
	@ModelAttribute
	public void getAgentPurchase(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("agentPurchase", agentPurchaseService.get(id));
		}
	}
	
	/**
	 * 构造编号
	 */
	@RequestMapping(value = "generateCode", method = RequestMethod.GET)
	@ResponseBody
	public String generateCode(@RequestParam("customer") String customer, @RequestParam("documentType") String documentType) {
		return agentPurchaseService.generateCode(customer, documentType);
	}
	
	/**
	 * 验证编码唯一性
	 */
	@RequestMapping(value = "uniqueNo/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean uniqueNo(@PathVariable("id") Long id, @RequestParam("contractNo") String contractNo) {
		return agentPurchaseService.findByNo(id, contractNo) != null;
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
			AgentPurchase agentPurchase = agentPurchaseService.get(id);
			agentPurchase.setUserId(user.getLoginName());
			agentPurchase.setState("已提交");
			agentPurchaseService.save(agentPurchase);
			Map<String, Object> variables = new HashMap<String, Object>();
			ProcessInstance processInstance = activitiService.startWorkflow(agentPurchase, "wf_agentPurchase", variables, user.getLoginName());//提交流程
			String taskId = activitiService.findTaskListByKey(processInstance.getId(), processInstance.getActivityId()).get(0).getId();
			activitiService.doClaim(taskId);//签收
			activitiController.approvalForm(processInstance.getId(), taskId, agentPurchase.getId(), "wf_agentPurchase", model);//处理参数
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
				AgentPurchase agentPurchase = agentPurchaseService.get(id);
				agentPurchase.setProcessInstanceId(null);
				agentPurchase.setState("草稿");
				agentPurchaseService.save(agentPurchase);
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
