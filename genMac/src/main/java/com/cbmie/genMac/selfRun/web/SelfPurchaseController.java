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
import com.cbmie.genMac.selfRun.entity.SelfPurchase;
import com.cbmie.genMac.selfRun.service.SelfPurchaseGoodsService;
import com.cbmie.genMac.selfRun.service.SelfPurchaseService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 采购合同controller（自营）
 */
@Controller
@RequestMapping("selfRun/purchase")
public class SelfPurchaseController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SelfPurchaseService selfPurchaseService;
	
	@Autowired
	private SelfPurchaseGoodsService selfPurchaseGoodsService;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private ActivitiController activitiController;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "selfRun/selfPurchaseList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> selfPurchaseList(HttpServletRequest request) {
		Page<SelfPurchase> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = selfPurchaseService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取list
	 */
	@RequestMapping(value="filter",method = RequestMethod.GET)
	@ResponseBody
	public List<SelfPurchase> filter(HttpServletRequest request) {
		return selfPurchaseService.search(PropertyFilter.buildFromHttpRequest(request));
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("selfPurchase", new SelfPurchase());
		model.addAttribute("action", "create");
		return "selfRun/selfPurchaseForm";
	}

	/**
	 * 添加
	 * 
	 * @param selfPurchase
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid SelfPurchase selfPurchase, Model model,
			@RequestParam("selfPurchaseGoodsJson") String selfPurchaseGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		selfPurchase.setCreaterNo(currentUser.getLoginName());
		selfPurchase.setCreaterName(currentUser.getName());
		selfPurchase.setCreaterDept(currentUser.getOrganization().getOrgName());
		selfPurchase.setCreateDate(new Date());
		selfPurchase.setSummary("（自营）采购合同[" + selfPurchase.getContractNo() + "]");
		selfPurchaseService.save(selfPurchase);
		selfPurchaseGoodsService.save(selfPurchase, selfPurchaseGoodsJson, currentUser);
		return selfPurchase.getId().toString();
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
		model.addAttribute("selfPurchase", selfPurchaseService.get(id));
		model.addAttribute("action", "update");
		return "selfRun/selfPurchaseForm";
	}

	/**
	 * 修改
	 * 
	 * @param selfPurchase
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody SelfPurchase selfPurchase, Model model,
			@RequestParam("selfPurchaseGoodsJson") String selfPurchaseGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		selfPurchase.setUpdaterNo(currentUser.getLoginName());
		selfPurchase.setUpdaterName(currentUser.getName());
		selfPurchase.setCreaterDept(currentUser.getOrganization().getOrgName());
		selfPurchase.setUpdateDate(new Date());
		selfPurchase.setSummary("（自营）采购合同[" + selfPurchase.getContractNo() + "]");
		selfPurchaseGoodsService.save(selfPurchase, selfPurchaseGoodsJson, currentUser);
		selfPurchaseService.update(selfPurchase);
		return selfPurchase.getId().toString();
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
		selfPurchaseService.delete(id);
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
		model.addAttribute("selfPurchase", selfPurchaseService.get(id));
		return "selfRun/selfPurchaseDetail";
	}
	
	@ModelAttribute
	public void getSelfPurchase(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("selfPurchase", selfPurchaseService.get(id));
		}
	}
	
	/**
	 * 验证编码唯一性
	 */
	@RequestMapping(value = "uniqueNo/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean uniqueNo(@PathVariable("id") Long id, @RequestParam("contractNo") String contractNo) {
		return selfPurchaseService.findByNo(id, contractNo) != null;
	}
	
	/**
	 * 构造编号
	 */
	@RequestMapping(value = "generateCode", method = RequestMethod.GET)
	@ResponseBody
	public String generateCode(@RequestParam("customer") String customer, @RequestParam("documentType") String documentType) {
		return selfPurchaseService.generateCode(customer, documentType);
	}
	
	/**
	 * 找出满足可申请开证的合同
	 */
	@RequestMapping(value = "findOpenCredit/{type}", method = RequestMethod.GET)
	@ResponseBody
	public List<SelfPurchase> findOpenCredit(@PathVariable("type") String type){
		return selfPurchaseService.findOpenCredit(type);
	}
	
	/**
	 * 找出满足到单的合同
	 */
	@RequestMapping(value = "findInvoiceReg", method = RequestMethod.GET)
	@ResponseBody
	public List<SelfPurchase> findInvoiceReg(){
		return selfPurchaseService.findInvoiceReg();
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
			SelfPurchase selfPurchase = selfPurchaseService.get(id);
			selfPurchase.setUserId(user.getLoginName());
			selfPurchase.setState("已提交");
			selfPurchaseService.save(selfPurchase);
			Map<String, Object> variables = new HashMap<String, Object>();
			ProcessInstance processInstance = activitiService.startWorkflow(selfPurchase, "wf_selfPurchase", variables, user.getLoginName());//提交流程
			String taskId = activitiService.findTaskListByKey(processInstance.getId(), processInstance.getActivityId()).get(0).getId();
			activitiService.doClaim(taskId);//签收
			activitiController.approvalForm(processInstance.getId(), taskId, selfPurchase.getId(), "wf_selfPurchase", model);//处理参数
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
				SelfPurchase selfPurchase = selfPurchaseService.get(id);
				selfPurchase.setProcessInstanceId(null);
				selfPurchase.setState("草稿");
				selfPurchaseService.save(selfPurchase);
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
