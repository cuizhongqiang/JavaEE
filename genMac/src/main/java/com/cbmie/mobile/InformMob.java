package com.cbmie.mobile;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cbmie.system.entity.Inform;
import com.cbmie.system.service.InformService;
import com.cbmie.system.utils.UserUtil;

@Controller
@RequestMapping("mobile/inform")
public class InformMob {
	
	@Autowired
	private InformService informService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model) throws Exception {
		String loginName = UserUtil.getCurrentUser().getLoginName();
		model.addAttribute("informList", informService.findBy("person", loginName));
		model.addAttribute("loginName", loginName);
		return "mobile/inform/informList";
	}
	
	/**
	 * 详情
	 */
	@RequestMapping(value = "detail", method = RequestMethod.POST)
	public String detail(HttpServletRequest request, Model model) throws Exception {
		Inform inform = informService.get(Integer.valueOf(request.getParameter("id")));
		model.addAttribute("inform", inform);
		model.addAttribute("loginName", request.getParameter("loginName"));
		return "mobile/inform/informDetail";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(HttpServletRequest request, Model model) {
		informService.delete(Integer.valueOf(request.getParameter("id")));
		model.addAttribute("informList", informService.findBy("person", request.getParameter("loginName")));
		model.addAttribute("loginName", request.getParameter("loginName"));
		return "mobile/inform/informList";
	}
}
