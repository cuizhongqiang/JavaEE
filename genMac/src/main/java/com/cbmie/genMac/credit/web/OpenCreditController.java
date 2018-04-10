package com.cbmie.genMac.credit.web;

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
import com.cbmie.genMac.credit.entity.OpenCredit;
import com.cbmie.genMac.credit.service.OpenCreditGoodsService;
import com.cbmie.genMac.credit.service.OpenCreditService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 开证controller
 */
@Controller
@RequestMapping("credit/openCredit")
public class OpenCreditController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private OpenCreditService openCreditService;
	
	@Autowired
	private OpenCreditGoodsService openCreditGoodsService;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private ActivitiController activitiController;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "type") String type, Model model) {
		model.addAttribute("type", type);
		return "credit/openCreditApplyList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> openCreditList(HttpServletRequest request) {
		Page<OpenCredit> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = openCreditService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取list
	 */
	@RequestMapping(value="filter",method = RequestMethod.GET)
	@ResponseBody
	public List<OpenCredit> filter(HttpServletRequest request) {
		return openCreditService.search(PropertyFilter.buildFromHttpRequest(request));
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create/{type}", method = RequestMethod.GET)
	public String createForm(@PathVariable("type") String type, Model model) {
		model.addAttribute("openCredit", new OpenCredit());
		model.addAttribute("action", "create");
		return "credit/openCreditApplyForm";
	}

	/**
	 * 添加
	 * 
	 * @param openCredit
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid OpenCredit openCredit, Model model, @RequestParam("openCreditGoodsJson") String openCreditGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		openCredit.setCreaterNo(currentUser.getLoginName());
		openCredit.setCreaterName(currentUser.getName());
		openCredit.setCreaterDept(currentUser.getOrganization().getOrgName());
		openCredit.setCreateDate(new Date());
		if (openCredit.getLcType() > 1) {
			openCredit.setSummary("TT申请[" + openCredit.getContractNo() + "]");
		} else {
			openCredit.setSummary("信用证申请[" + openCredit.getContractNo() + "]");
		}
		openCreditService.save(openCredit);
		openCreditGoodsService.save(openCredit, openCreditGoodsJson);
		return openCredit.getId().toString();
	}

	/**
	 * 修改跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{type}/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("type") String type, @PathVariable("id") Long id, Model model) {
		model.addAttribute("openCredit", openCreditService.get(id));
		model.addAttribute("action", "update");
		return "credit/openCreditApplyForm";
	}

	/**
	 * 修改
	 * 
	 * @param openCredit
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody OpenCredit openCredit, Model model, @RequestParam("openCreditGoodsJson") String openCreditGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		openCredit.setUpdaterNo(currentUser.getLoginName());
		openCredit.setUpdaterName(currentUser.getName());
		openCredit.setCreaterDept(currentUser.getOrganization().getOrgName());
		openCredit.setUpdateDate(new Date());
		if (openCredit.getLcType() > 1) {
			openCredit.setSummary("TT申请[" + openCredit.getContractNo() + "]");
		} else {
			openCredit.setSummary("信用证申请[" + openCredit.getContractNo() + "]");
		}
		openCreditGoodsService.save(openCredit, openCreditGoodsJson);
		openCreditService.update(openCredit);
		return openCredit.getId().toString();
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
		openCreditService.delete(id);
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
		model.addAttribute("workflow", request.getParameter("workflow") != null ? true : false);
		model.addAttribute("openCredit", openCreditService.get(id));
		return "credit/openCreditDetail";
	}
	
	/**
	 * 登记跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "reg/{id}", method = RequestMethod.GET)
	public String regForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("openCredit", openCreditService.get(id));
		return "credit/openCreditRegForm";
	}
	
	/**
	 * 登记
	 */
	@RequestMapping(value = "registration", method = RequestMethod.POST)
	@ResponseBody
	public String registration(@Valid @ModelAttribute @RequestBody OpenCredit openCredit, Model model) {
		openCreditService.update(openCredit);
		return "success";
	}
	
	@ModelAttribute
	public void getOpenCredit(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("openCredit", openCreditService.get(id));
		}
	}
	
	/**
	 * 配置合同
	 */
	@RequestMapping(value = "config/{type}/{contractType}", method = RequestMethod.GET)
	public String toConfig(@PathVariable("type") String type, @PathVariable("contractType") String contractType) {
		return "credit/openCreditConfig";
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
			OpenCredit openCredit = openCreditService.get(id);
			openCredit.setUserId(user.getLoginName());
			openCredit.setState("已提交");
			openCreditService.save(openCredit);
			Map<String, Object> variables = new HashMap<String, Object>();
			ProcessInstance processInstance = activitiService.startWorkflow(openCredit, "wf_openCredit", variables, user.getLoginName());
			String taskId = activitiService.findTaskListByKey(processInstance.getId(), processInstance.getActivityId()).get(0).getId();
			activitiService.doClaim(taskId);//签收
			activitiController.approvalForm(processInstance.getId(), taskId, openCredit.getId(), "wf_openCredit", model);//处理参数
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
				OpenCredit openCredit = openCreditService.get(id);
				openCredit.setProcessInstanceId(null);
				openCredit.setState("草稿");
				openCreditService.save(openCredit);
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
