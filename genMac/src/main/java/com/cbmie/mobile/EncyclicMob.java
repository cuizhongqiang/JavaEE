package com.cbmie.mobile;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cbmie.activiti.entity.Encyclic;
import com.cbmie.activiti.service.EncyclicService;
import com.cbmie.activiti.web.ActivitiController;
import com.cbmie.system.utils.UserUtil;

@Controller
@RequestMapping("mobile/encyclic")
public class EncyclicMob {
	
	@Autowired
	private EncyclicService encyclicService;
	
	@Autowired
	private ActivitiController activitiController;
	
	@Autowired
	private TaskTodo taskTodo;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model) throws Exception {
		String loginName = UserUtil.getCurrentUser().getLoginName();
		model.addAttribute("encyclicList", encyclicService.findBy("loginName", loginName));
		model.addAttribute("loginName", loginName);
		return "mobile/encyclic/encyclicList";
	}
	
	/**
	 * 详情
	 */
	@RequestMapping(value = "detail", method = RequestMethod.POST)
	public String detail(HttpServletRequest request, Model model) throws Exception {
		Encyclic encyclic = new Encyclic();
		BeanUtils.copyProperties(encyclic, request.getParameterMap());
		taskTodo.detail(request, model);
		model.addAttribute("encyclic", encyclic);
		return "mobile/encyclic/encyclicDetail";
	}
	
	/**
	 * 流程跟踪
	 */
	@RequestMapping(value = "trace", method = RequestMethod.POST)
	public String trace(HttpServletRequest request, Model model) {
		//修改状态
		Encyclic encyclic = encyclicService.get(Integer.valueOf(request.getParameter("id")));
		encyclic.setState(1);
		encyclicService.update(encyclic);
		//获取流程跟踪
		model.addAttribute("traceList", activitiController.trace(request, request.getParameter("processInstanceId")).get("rows"));
		return "mobile/trace";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(HttpServletRequest request, Model model) {
		encyclicService.delete(Integer.valueOf(request.getParameter("id")));
		model.addAttribute("encyclicList", encyclicService.findBy("loginName", request.getParameter("loginName")));
		model.addAttribute("loginName", request.getParameter("loginName"));
		return "mobile/encyclic/encyclicList";
	}
}
