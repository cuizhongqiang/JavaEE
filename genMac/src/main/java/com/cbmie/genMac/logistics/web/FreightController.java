package com.cbmie.genMac.logistics.web;

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
import com.cbmie.genMac.logistics.entity.Freight;
import com.cbmie.genMac.logistics.service.FreightGoodsService;
import com.cbmie.genMac.logistics.service.FreightService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 货代controller
 */
@Controller
@RequestMapping("logistics/freight")
public class FreightController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FreightService freightService;
	
	@Autowired
	private FreightGoodsService freightGoodsService;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private ActivitiController activitiController;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "logistics/freightList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> freightList(HttpServletRequest request) {
		Page<Freight> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = freightService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取list
	 */
	@RequestMapping(value="filter",method = RequestMethod.GET)
	@ResponseBody
	public List<Freight> filter(HttpServletRequest request) {
		return freightService.search(PropertyFilter.buildFromHttpRequest(request));
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("freight", new Freight());
		model.addAttribute("action", "create");
		return "logistics/freightForm";
	}

	/**
	 * 添加
	 * 
	 * @param freight
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Freight freight, Model model, @RequestParam("freightGoodsJson") String freightGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		freight.setCreaterNo(currentUser.getLoginName());
		freight.setCreaterName(currentUser.getName());
		freight.setCreaterDept(currentUser.getOrganization().getOrgName());
		freight.setCreateDate(new Date());
		freight.setSummary("确认货代[" + freight.getContractNo() + "]");
		freightService.save(freight);
		freightGoodsService.save(freight, freightGoodsJson, currentUser);
		return freight.getId().toString();
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
		model.addAttribute("freight", freightService.get(id));
		model.addAttribute("action", "update");
		return "logistics/freightForm";
	}

	/**
	 * 修改
	 * 
	 * @param freight
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody Freight freight, Model model, @RequestParam("freightGoodsJson") String freightGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		freight.setUpdaterNo(currentUser.getLoginName());
		freight.setUpdaterName(currentUser.getName());
		freight.setCreaterDept(currentUser.getOrganization().getOrgName());
		freight.setUpdateDate(new Date());
		freight.setSummary("确认货代[" + freight.getContractNo() + "]");
		freightGoodsService.save(freight, freightGoodsJson, currentUser);
		freightService.update(freight);
		return freight.getId().toString();
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
		freightService.delete(id);
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
		model.addAttribute("freight", freightService.get(id));
		return "logistics/freightInfo";
	}
	
	@ModelAttribute
	public void getFreight(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("freight", freightService.get(id));
		}
	}
	
	/**
	 * 已经确认货代的合同号
	 */
	@RequestMapping(value = "getHavFreCons")
	@ResponseBody
	public List<String> getHavFreCons() {
		return freightService.getHavFreCons();
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
			Freight freight = freightService.get(id);
			freight.setUserId(user.getLoginName());
			freight.setState("已提交");
			freightService.save(freight);
			Map<String, Object> variables = new HashMap<String, Object>();
			ProcessInstance processInstance = activitiService.startWorkflow(freight, "wf_freight", variables, user.getLoginName());
			String taskId = activitiService.findTaskListByKey(processInstance.getId(), processInstance.getActivityId()).get(0).getId();
			activitiService.doClaim(taskId);//签收
			activitiController.approvalForm(processInstance.getId(), taskId, freight.getId(), "wf_freight", model);//处理参数
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
				Freight freight = freightService.get(id);
				freight.setProcessInstanceId(null);
				freight.setState("草稿");
				freightService.save(freight);
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
