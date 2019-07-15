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
import com.cbmie.genMac.domesticTrade.entity.DomesticPurchase;
import com.cbmie.genMac.domesticTrade.service.DomesticPurchaseGoodsService;
import com.cbmie.genMac.domesticTrade.service.DomesticPurchaseService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 采购合同controller
 */
@Controller
@RequestMapping("domesticTrade/domesticPurchase")
public class DomesticPurchaseController extends BaseController{
		
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DomesticPurchaseService domesticPurchaseService;
	
	@Autowired
	private DomesticPurchaseGoodsService domesticPurchaseGoodsService;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private ActivitiController activitiController;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "domesticTrade/domesticPurchaseList";
	}
	
	/**
	 * 获取json 
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> domesticPurchaseList(HttpServletRequest request) {
		Page<DomesticPurchase> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = domesticPurchaseService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取list
	 */
	@RequestMapping(value="filter",method = RequestMethod.GET)
	@ResponseBody
	public List<DomesticPurchase> filter(HttpServletRequest request) {
		return domesticPurchaseService.search(PropertyFilter.buildFromHttpRequest(request));
	}
	
	/**
	 * 添加采购合同跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("domesticPurchase", new DomesticPurchase());
		model.addAttribute("action", "create");
		return "domesticTrade/domesticPurchaseForm";
	}

	/**
	 * 添加采购合同
	 * 
	 * @param DomesticPurchase
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid DomesticPurchase domesticPurchase, Model model,
			@RequestParam("domesticPurchaseGoodsJson") String domesticPurchaseGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		domesticPurchase.setCreaterNo(currentUser.getLoginName());
		domesticPurchase.setCreaterName(currentUser.getName());
		domesticPurchase.setCreateDate(new Date());
		domesticPurchase.setCreaterDept(currentUser.getOrganization().getOrgName());
		domesticPurchase.setSummary("（内贸）采购合同[" + domesticPurchase.getContractNo() + "]");
		domesticPurchaseService.save(domesticPurchase);
		domesticPurchaseGoodsService.save(domesticPurchase, domesticPurchaseGoodsJson, currentUser);
		return domesticPurchase.getId().toString();
	}

	/**
	 * 修改采购合同跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("domesticPurchase", domesticPurchaseService.get(id));
		model.addAttribute("action", "update");
		return "domesticTrade/domesticPurchaseForm";
	}

	/**
	 * 修改采购合同
	 * 
	 * @param port
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody DomesticPurchase domesticPurchase, Model model,
			@RequestParam("domesticPurchaseGoodsJson") String domesticPurchaseGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		domesticPurchase.setUpdaterNo(currentUser.getLoginName());
		domesticPurchase.setUpdaterName(currentUser.getName());
		domesticPurchase.setUpdateDate(new Date());
		domesticPurchase.setSummary("（内贸）采购合同[" + domesticPurchase.getContractNo() + "]");
		domesticPurchaseGoodsService.save(domesticPurchase, domesticPurchaseGoodsJson, currentUser);
		try{
			domesticPurchaseService.update(domesticPurchase);
		}catch(Exception e){
			e.printStackTrace();
		}
		return domesticPurchase.getId().toString();
	}

	/**
	 * 删除采购合同
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		domesticPurchaseService.delete(id);
		return "success";
	}
	
	/**
	 * 采购合同明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("domesticPurchase", domesticPurchaseService.get(id));
		return "domesticTrade/domesticPurchaseDetail";
	}
	
	@ModelAttribute
	public void getDomesticPurchase(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("domesticPurchase", domesticPurchaseService.get(id));
		}
	}
	
	/**
	 * 构造编号
	 */
	@RequestMapping(value = "generateCode", method = RequestMethod.GET)
	@ResponseBody
	public String generateCode(@RequestParam("customer") String customer, @RequestParam("documentType") String documentType) {
		return domesticPurchaseService.generateCode(customer, documentType);
	}
	
	/**
	 * 验证编码唯一性
	 */
	@RequestMapping(value = "uniqueNo/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean uniqueNo(@PathVariable("id") Long id, @RequestParam("contractNo") String contractNo) {
		return domesticPurchaseService.findByNo(id, contractNo) != null;
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
			DomesticPurchase domesticPurchase = domesticPurchaseService.get(id);
			domesticPurchase.setUserId(user.getLoginName());
			domesticPurchase.setState("已提交");
			domesticPurchaseService.save(domesticPurchase);
			Map<String, Object> variables = new HashMap<String, Object>();
			ProcessInstance processInstance = activitiService.startWorkflow(domesticPurchase, "wf_domesticPurchase", variables, user.getLoginName());//提交流程
			String taskId = activitiService.findTaskListByKey(processInstance.getId(), processInstance.getActivityId()).get(0).getId();
			activitiService.doClaim(taskId);//签收
			activitiController.approvalForm(processInstance.getId(), taskId, domesticPurchase.getId(), "wf_domesticPurchase", model);//处理参数
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
				DomesticPurchase domesticPurchase = domesticPurchaseService.get(id);
				domesticPurchase.setProcessInstanceId(null);
				domesticPurchase.setState("草稿");
				domesticPurchaseService.save(domesticPurchase);
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
