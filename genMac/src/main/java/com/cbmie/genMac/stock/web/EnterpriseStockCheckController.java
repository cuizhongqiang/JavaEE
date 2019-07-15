package com.cbmie.genMac.stock.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import com.cbmie.genMac.stock.entity.EnterpriseStockCheck;
import com.cbmie.genMac.stock.service.EnterpriseStockCheckService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 仓储企业评审controller
 */
@Controller
@RequestMapping("stock/enterpriseStockCheck")
public class EnterpriseStockCheckController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private EnterpriseStockCheckService enterpriseStockCheckService;

	@Autowired
	protected ActivitiService activitiService;
	
	@Autowired
	private ActivitiController activitiController;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "stock/stockCheckList";
	}

	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> enterpriseStockCheckList(HttpServletRequest request) {
		Page<EnterpriseStockCheck> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = enterpriseStockCheckService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取list
	 */
	@RequestMapping(value="filter",method = RequestMethod.GET)
	@ResponseBody
	public List<EnterpriseStockCheck> filter(HttpServletRequest request) {
		return enterpriseStockCheckService.search(PropertyFilter.buildFromHttpRequest(request));
	}

	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("enterpriseStockCheck", new EnterpriseStockCheck());
		model.addAttribute("action", "create");
		return "stock/stockCheckForm";
	}

	/**
	 * 添加仓储企业评审
	 * @param enterpriseStockCheck
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid EnterpriseStockCheck enterpriseStockCheck, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		enterpriseStockCheck.setSummary("仓储企业评审[" + enterpriseStockCheck.getEnterpriseName() + "]");
		enterpriseStockCheck.setCreaterNo(currentUser.getLoginName());
		enterpriseStockCheck.setCreaterName(currentUser.getName());
		enterpriseStockCheck.setCreateDate(new Date());
		enterpriseStockCheck.setCreaterDept(currentUser.getOrganization().getOrgName());
		enterpriseStockCheckService.save(enterpriseStockCheck);
		return enterpriseStockCheck.getId().toString();
	}

	/**
	 * 修改仓储企业评审跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		EnterpriseStockCheck enterpriseStockCheck = new EnterpriseStockCheck();
		enterpriseStockCheck = enterpriseStockCheckService.get(id);
		model.addAttribute("enterpriseStockCheck", enterpriseStockCheck);
		model.addAttribute("action", "update");
		return "stock/stockCheckForm";
	}

	/**
	 * 修改仓储企业评审
	 * 
	 * @param enterpriseStockCheck
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody EnterpriseStockCheck enterpriseStockCheck, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		enterpriseStockCheck.setSummary("仓储企业评审[" + enterpriseStockCheck.getEnterpriseName() + "]");
		enterpriseStockCheck.setUpdaterNo(currentUser.getLoginName());
		enterpriseStockCheck.setUpdaterName(currentUser.getName());
		enterpriseStockCheck.setUpdateDate(new Date());
		enterpriseStockCheckService.update(enterpriseStockCheck);
		return enterpriseStockCheck.getId().toString();
	}

	/**
	 * 删除仓储企业评审
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		enterpriseStockCheckService.delete(id);
		return "success";
	}

	/**
	 * 仓储企业评审明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("enterpriseStockCheck", enterpriseStockCheckService.get(id));
		return "stock/stockCheckDetail";
	}

	@ModelAttribute
	public void getEnterpriseStockCheck(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("enterpriseStockCheck", enterpriseStockCheckService.get(id));
		}
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
			EnterpriseStockCheck enterpriseStockCheck = enterpriseStockCheckService.get(id);
			enterpriseStockCheck.setUserId(user.getLoginName());
			enterpriseStockCheck.setState("已提交");
			enterpriseStockCheckService.save(enterpriseStockCheck);
			Map<String, Object> variables = new HashMap<String, Object>();
			ProcessInstance processInstance = activitiService.startWorkflow(enterpriseStockCheck, "wf_enterpriseStock", variables, user.getLoginName());
			String taskId = activitiService.findTaskListByKey(processInstance.getId(), processInstance.getActivityId()).get(0).getId();
			activitiService.doClaim(taskId);//签收
			activitiController.approvalForm(processInstance.getId(), taskId, enterpriseStockCheck.getId(), "wf_enterpriseStock", model);//处理参数
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
	public String callBack(@PathVariable("id") Long id, @PathVariable("processInstanceId") String processInstanceId, HttpSession session) {
		try {
			if (activitiService.deleteProcessInstance(processInstanceId, false)) {
				EnterpriseStockCheck enterpriseStockCheck  = enterpriseStockCheckService.get(id);
				enterpriseStockCheck.setProcessInstanceId(null);
				enterpriseStockCheck.setState("草稿");
				enterpriseStockCheckService.save(enterpriseStockCheck);
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
