package com.cbmie.genMac.selfRun.web;

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
import com.cbmie.genMac.selfRun.entity.SelfSales;
import com.cbmie.genMac.selfRun.service.SelfSalesGoodsService;
import com.cbmie.genMac.selfRun.service.SelfSalesService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 销售合同controller（自营）
 */
@Controller
@RequestMapping("selfRun/sales")
public class SelfSalesController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SelfSalesService selfSalesService;
	
	@Autowired
	private SelfSalesGoodsService selfSalesGoodsService;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private ActivitiController activitiController;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "selfRun/selfSalesList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> selfSalesList(HttpServletRequest request) {
		Page<SelfSales> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = selfSalesService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取list
	 */
	@RequestMapping(value="filter",method = RequestMethod.GET)
	@ResponseBody
	public List<SelfSales> filter(HttpServletRequest request) {
		return selfSalesService.search(PropertyFilter.buildFromHttpRequest(request));
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("selfSales", new SelfSales());
		model.addAttribute("action", "create");
		return "selfRun/selfSalesForm";
	}

	/**
	 * 添加
	 * 
	 * @param selfSales
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid SelfSales selfSales, Model model,
			@RequestParam("selfSalesGoodsJson") String selfSalesGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		selfSales.setCreaterNo(currentUser.getLoginName());
		selfSales.setCreaterName(currentUser.getName());
		selfSales.setCreaterDept(currentUser.getOrganization().getOrgName());
		selfSales.setCreateDate(new Date());
		selfSales.setSummary("（自营）销售合同[" + selfSales.getContractNo() + "]");
		selfSalesService.save(selfSales);
		selfSalesGoodsService.save(selfSales, selfSalesGoodsJson, currentUser);
		return selfSales.getId().toString();
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
		model.addAttribute("selfSales", selfSalesService.get(id));
		model.addAttribute("action", "update");
		return "selfRun/selfSalesForm";
	}

	/**
	 * 修改
	 * 
	 * @param selfSales
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody SelfSales selfSales, Model model,
			@RequestParam("selfSalesGoodsJson") String selfSalesGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		selfSales.setUpdaterNo(currentUser.getLoginName());
		selfSales.setUpdaterName(currentUser.getName());
		selfSales.setCreaterDept(currentUser.getOrganization().getOrgName());
		selfSales.setUpdateDate(new Date());
		selfSales.setSummary("（自营）销售合同[" + selfSales.getContractNo() + "]");
		selfSalesGoodsService.save(selfSales, selfSalesGoodsJson, currentUser);
		selfSalesService.update(selfSales);
		return selfSales.getId().toString();
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
		selfSalesService.delete(id);
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
		model.addAttribute("selfSales", selfSalesService.get(id));
		return "selfRun/selfSalesDetail";
	}
	
	@ModelAttribute
	public void getSelfSales(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("selfSales", selfSalesService.get(id));
		}
	}
	
	
	/**
	 * 验证编码唯一性
	 */
	@RequestMapping(value = "uniqueNo/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean uniqueNo(@PathVariable("id") Long id, @RequestParam("contractNo") String contractNo) {
		return selfSalesService.findByNo(id, contractNo) != null;
	}
	
	/**
	 * 构造编号
	 */
	@RequestMapping(value = "generateCode", method = RequestMethod.GET)
	@ResponseBody
	public String generateCode(@RequestParam("customer") String customer, @RequestParam("documentType") String documentType) {
		return selfSalesService.generateCode(customer, documentType);
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
			SelfSales selfSales = selfSalesService.get(id);
			selfSales.setUserId(user.getLoginName());
			selfSales.setState("已提交");
			selfSalesService.save(selfSales);
			Map<String, Object> variables = new HashMap<String, Object>();
			ProcessInstance processInstance = activitiService.startWorkflow(selfSales, "wf_selfSales", variables, user.getLoginName());//提交流程
			String taskId = activitiService.findTaskListByKey(processInstance.getId(), processInstance.getActivityId()).get(0).getId();
			activitiService.doClaim(taskId);//签收
			activitiController.approvalForm(processInstance.getId(), taskId, selfSales.getId(), "wf_selfSales", model);//处理参数
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
				SelfSales selfSales = selfSalesService.get(id);
				selfSales.setProcessInstanceId(null);
				selfSales.setState("草稿");
				selfSalesService.save(selfSales);
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
