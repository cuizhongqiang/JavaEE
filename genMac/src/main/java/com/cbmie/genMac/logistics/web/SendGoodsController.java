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
import com.cbmie.genMac.financial.service.SerialService;
import com.cbmie.genMac.logistics.entity.SendGoods;
import com.cbmie.genMac.logistics.service.SendGoodsGoodsService;
import com.cbmie.genMac.logistics.service.SendGoodsService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 放货controller
 */
@Controller
@RequestMapping("logistics/sendGoods")
public class SendGoodsController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SendGoodsService sendGoodsService;
	
	@Autowired
	private SendGoodsGoodsService sendGoodsGoodsService;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private ActivitiController activitiController;
	
	@Autowired
	private SerialService serialService;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "logistics/sendGoodsList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> sendGoodsList(HttpServletRequest request) {
		Page<SendGoods> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = sendGoodsService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取list
	 */
	@RequestMapping(value="filter",method = RequestMethod.GET)
	@ResponseBody
	public List<SendGoods> filter(HttpServletRequest request) {
		return sendGoodsService.search(PropertyFilter.buildFromHttpRequest(request));
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("sendGoods", new SendGoods());
		model.addAttribute("action", "create");
		return "logistics/sendGoodsForm";
	}

	/**
	 * 添加
	 * 
	 * @param sendGoods
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid SendGoods sendGoods, Model model, @RequestParam("sendGoodsGoodsJson") String sendGoodsGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		sendGoods.setCreaterNo(currentUser.getLoginName());
		sendGoods.setCreaterName(currentUser.getName());
		sendGoods.setCreaterDept(currentUser.getOrganization().getOrgName());
		sendGoods.setCreateDate(new Date());
		sendGoods.setSummary("放货[" + sendGoods.getContractNo() + "]");
		sendGoodsService.save(sendGoods);
		sendGoodsGoodsService.save(sendGoods, sendGoodsGoodsJson, currentUser);
		return sendGoods.getId().toString();
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
		model.addAttribute("sendGoods", sendGoodsService.get(id));
		model.addAttribute("action", "update");
		return "logistics/sendGoodsForm";
	}

	/**
	 * 修改
	 * 
	 * @param sendGoods
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody SendGoods sendGoods, Model model,
			@RequestParam("sendGoodsGoodsJson") String sendGoodsGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		sendGoods.setUpdaterNo(currentUser.getLoginName());
		sendGoods.setUpdaterName(currentUser.getName());
		sendGoods.setCreaterDept(currentUser.getOrganization().getOrgName());
		sendGoods.setUpdateDate(new Date());
		sendGoods.setSummary("放货[" + sendGoods.getContractNo() + "]");
		sendGoodsGoodsService.save(sendGoods, sendGoodsGoodsJson, currentUser);
		sendGoodsService.update(sendGoods);
		return sendGoods.getId().toString();
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
		sendGoodsService.delete(id);
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
		model.addAttribute("sendGoods", sendGoodsService.get(id));
		return "logistics/sendGoodsInfo";
	}
	
	/**
	 * 配置库存跳转
	 */
	@RequestMapping(value = "toStockConfig", method = RequestMethod.GET)
	public String toStockConfig() {
		return "logistics/stockConfig";
	}
	
	/**
	 * 配置直运跳转
	 */
	@RequestMapping(value = "toInvoiceConfig", method = RequestMethod.GET)
	public String toInvoiceConfig() {
		return "logistics/invoiceConfig";
	}
	
	@ModelAttribute
	public void getSendGoods(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("sendGoods", sendGoodsService.get(id));
		}
	}
	
	/**
	 * 构造编号
	 */
	@RequestMapping(value = "generateCode", method = RequestMethod.GET)
	@ResponseBody
	public String generateCode(@RequestParam("contractNo") String contractNo, @RequestParam("documentType") String documentType) {
		return sendGoodsService.generateCode(contractNo, documentType);
	}
	
	/**
	 * 验证发货单号唯一性
	 */
	@RequestMapping(value = "uniqueNo/{id}/{no}", method = RequestMethod.GET)
	@ResponseBody
	public boolean uniqueNo(@PathVariable("id") Long id, @PathVariable("no") String no) {
		return sendGoodsService.findByNo(id, no) != null;
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
			SendGoods sendGoods = sendGoodsService.get(id);
			sendGoods.setUserId(user.getLoginName());
			sendGoods.setState("已提交");
			sendGoodsService.save(sendGoods);
			
			Map<String, Object> variables = new HashMap<String, Object>();
			Double serialSum = serialService.sum(sendGoods.getContractNo(), "货款");//货款水单总金额
			Double othersSum = sendGoodsService.sum(sendGoods.getContractNo(), sendGoods.getId());//其他放货总金额
			variables.put("advances", sendGoods.getTotalMoney() - (serialSum - othersSum));//是否垫款
			
			ProcessInstance processInstance = activitiService.startWorkflow(sendGoods, "wf_sendGoods", variables, user.getLoginName());
			String taskId = activitiService.findTaskListByKey(processInstance.getId(), processInstance.getActivityId()).get(0).getId();
			activitiService.doClaim(taskId);//签收
			activitiController.approvalForm(processInstance.getId(), taskId, sendGoods.getId(), "wf_sendGoods", model);//处理参数
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
				SendGoods sendGoods = sendGoodsService.get(id);
				sendGoods.setProcessInstanceId(null);
				sendGoods.setState("草稿");
				sendGoodsService.save(sendGoods);
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
