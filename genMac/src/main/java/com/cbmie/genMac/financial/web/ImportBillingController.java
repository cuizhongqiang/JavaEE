package com.cbmie.genMac.financial.web;

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
import com.cbmie.genMac.financial.entity.ImportBilling;
import com.cbmie.genMac.financial.service.ImportBillingGoodsService;
import com.cbmie.genMac.financial.service.ImportBillingService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 进口结算联系单controller
 */
@Controller
@RequestMapping("financial/importBilling")
public class ImportBillingController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ImportBillingService importBillingService;
	
	@Autowired
	private ImportBillingGoodsService importBillingGoodsService;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private ActivitiController activitiController;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "financial/importBillingList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> importBillingList(HttpServletRequest request) {
		Page<ImportBilling> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = importBillingService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取list
	 */
	@RequestMapping(value="filter",method = RequestMethod.GET)
	@ResponseBody
	public List<ImportBilling> filter(HttpServletRequest request) {
		return importBillingService.search(PropertyFilter.buildFromHttpRequest(request));
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("importBilling", new ImportBilling());
		model.addAttribute("action", "create");
		return "financial/importBillingForm";
	}

	/**
	 * 添加
	 * 
	 * @param importBilling
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid ImportBilling importBilling, Model model,
			@RequestParam("importBillingGoodsJson") String importBillingGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		importBilling.setCreaterNo(currentUser.getLoginName());
		importBilling.setCreaterName(currentUser.getName());
		importBilling.setCreaterDept(currentUser.getOrganization().getOrgName());
		importBilling.setCreateDate(new Date());
		importBilling.setSummary("进口结算联系单[" + importBilling.getContractNo() + "]");
		importBillingService.save(importBilling);
		importBillingGoodsService.save(importBilling, importBillingGoodsJson, currentUser);
		return importBilling.getId().toString();
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
		model.addAttribute("importBilling", importBillingService.get(id));
		model.addAttribute("action", "update");
		return "financial/importBillingForm";
	}

	/**
	 * 修改
	 * 
	 * @param importBilling
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody ImportBilling importBilling, Model model,
			@RequestParam("importBillingGoodsJson") String importBillingGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		importBilling.setUpdaterNo(currentUser.getLoginName());
		importBilling.setUpdaterName(currentUser.getName());
		importBilling.setCreaterDept(currentUser.getOrganization().getOrgName());
		importBilling.setUpdateDate(new Date());
		importBilling.setSummary("进口结算联系单[" + importBilling.getContractNo() + "]");
		importBillingGoodsService.save(importBilling, importBillingGoodsJson, currentUser);
		importBillingService.update(importBilling);
		return importBilling.getId().toString();
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
		importBillingService.delete(id);
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
		model.addAttribute("importBilling", importBillingService.get(id));
		return "financial/importBillingDetail";
	}
	
	@ModelAttribute
	public void getImportBilling(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("importBilling", importBillingService.get(id));
		}
	}
	
	/**
	 * 配置进口合同跳转
	 */
	@RequestMapping(value = "config", method = RequestMethod.GET)
	public String toConfig() {
		return "financial/importBillingConfig";
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
			ImportBilling importBilling = importBillingService.get(id);
			importBilling.setUserId(user.getLoginName());
			importBilling.setState("已提交");
			importBillingService.save(importBilling);
			Map<String, Object> variables = new HashMap<String, Object>();
			ProcessInstance processInstance = activitiService.startWorkflow(importBilling, "wf_importBilling", variables, user.getLoginName());
			String taskId = activitiService.findTaskListByKey(processInstance.getId(), processInstance.getActivityId()).get(0).getId();
			activitiService.doClaim(taskId);//签收
			activitiController.approvalForm(processInstance.getId(), taskId, importBilling.getId(), "wf_importBilling", model);//处理参数
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
				ImportBilling importBilling = importBillingService.get(id);
				importBilling.setProcessInstanceId(null);
				importBilling.setState("草稿");
				importBillingService.save(importBilling);
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
