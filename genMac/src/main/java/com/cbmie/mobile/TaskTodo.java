package com.cbmie.mobile;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cbmie.activiti.entity.ApprovalOpinion;
import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.activiti.web.ActivitiController;
import com.cbmie.system.service.UserService;
import com.cbmie.system.utils.UserUtil;

@Controller
@RequestMapping("mobile/taskTodo")
public class TaskTodo {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private ActivitiController activitiController;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 待办任务列表
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("taskList", activitiService.getTodoList(request));
		model.addAttribute("loginName", UserUtil.getCurrentUser().getLoginName());
		return "mobile/todo/taskList";
	}
	
	/**
	 * 初始化流程详细
	 */
	@RequestMapping(value = "detail", method = RequestMethod.POST)
	public String detail(HttpServletRequest request, Model model) throws Exception {
		ApprovalOpinion approval = new ApprovalOpinion();
		BeanUtils.copyProperties(approval, request.getParameterMap());
		//签收
		if("待签收".equals(request.getParameter("status"))){
			taskService.claim(approval.getTaskId(), request.getParameter("loginName"));
		}
		//初始化
		Class<?> clazz = activitiService.getWorkflowClass(approval.getProcessKey());
		Object obj = activitiService.getReflectionObj(clazz, approval.getBusinessKey());
		model.addAttribute("obj", obj);
		model.addAttribute("approval", approval);
		return "mobile/todo/taskTodo";
	}
	
	/**
	 * 审批跳转
	 */
	@RequestMapping(value = "approval", method = RequestMethod.POST)
	public String approvalForm(HttpServletRequest request, Model model) throws Exception {
		//初始化参数
		ApprovalOpinion approval = new ApprovalOpinion();
		BeanUtils.copyProperties(approval, request.getParameterMap());
		//请求工作流
		activitiController.approvalForm(approval.getProcessInstanceId(), approval.getTaskId(),
				approval.getBusinessKey(), approval.getProcessKey(), model);
		//获取用户列表用于传阅
		model.addAttribute("userList", userService.getAll());
		//流程跟踪列表
		Map<String, Object> map = activitiController.trace(request, approval.getProcessInstanceId());
		model.addAttribute("traceList", map.get("rows"));
		model.addAttribute("approval", approval);
		return "mobile/todo/doTask";
	}
	
	/**
	 * 同意
	 */
	@RequestMapping(value = "agree", method = RequestMethod.POST)
	public String agree(HttpServletRequest request, Model model) throws Exception {
		ApprovalOpinion approval = new ApprovalOpinion();
		BeanUtils.copyProperties(approval, request.getParameterMap());
		approval.setSms(Integer.valueOf(request.getParameter("sms")));
		approval.setEmail(Integer.valueOf(request.getParameter("email")));
		activitiController.passComplete(approval, model);
		model.addAttribute("taskList", activitiService.getTodoList(request));
		model.addAttribute("loginName", request.getParameter("loginName"));
		return "mobile/todo/taskList";
	}
	
	/**
	 * 驳回/退回
	 */
	@RequestMapping(value = "back", method = RequestMethod.POST)
	public String back(HttpServletRequest request, Model model) throws Exception {
		ApprovalOpinion approval = new ApprovalOpinion();
		BeanUtils.copyProperties(approval, request.getParameterMap());
		approval.setSms(Integer.valueOf(request.getParameter("sms")));
		approval.setEmail(Integer.valueOf(request.getParameter("email")));
		activitiController.backComplete(approval, Integer.valueOf(request.getParameter("flag")), model);
		model.addAttribute("taskList", activitiService.getTodoList(request));
		model.addAttribute("loginName", request.getParameter("loginName"));
		return "mobile/todo/taskList";
	}
	
}
