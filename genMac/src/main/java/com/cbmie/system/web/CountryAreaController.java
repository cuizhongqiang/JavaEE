package com.cbmie.system.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import com.cbmie.common.web.BaseController;
import com.cbmie.system.entity.CountryArea;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.CountryAreaService;
import com.cbmie.system.utils.UserUtil;

/**
 * 国别区域controller
 */
@Controller
@RequestMapping("system/countryArea")
public class CountryAreaController extends BaseController{

	@Autowired
	private CountryAreaService countryAreaService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "system/countryAreaList";
	}
	
	/**
	 * 获取国别区域json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public List<CountryArea> countryAreaList(HttpServletRequest request) {
		List<CountryArea> countryAreaList = countryAreaService.getAll();
		return countryAreaList;
	}
	
	/**
	 * 添加国别区域跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("countryArea", new CountryArea());
		model.addAttribute("action", "create");
		return "system/countryAreaForm";
	}

	/**
	 * 添加国别区域
	 * 
	 * @param countryArea
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid CountryArea countryArea, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		countryArea.setRegistrant(currentUser.getName());
		countryArea.setRegistrantDept(currentUser.getOrganization().getOrgName());
		countryArea.setRegistrantDate(new Date());
		
		countryAreaService.save(countryArea);
		return "success";
	}

	/**
	 * 修改国别区域跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("countryArea", countryAreaService.get(id));
		model.addAttribute("action", "update");
		return "system/countryAreaForm";
	}

	/**
	 * 修改国别区域
	 * 
	 * @param countryArea
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody CountryArea countryArea, Model model) {
		countryAreaService.update(countryArea);
		return "success";
	}

	/**
	 * 删除国别区域
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		countryAreaService.delete(id);
		return "success";
	}
	
	/**
	 * 查看国别区域跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Integer id, Model model) {
		CountryArea countryArea = countryAreaService.get(id);
		model.addAttribute("countryArea", countryArea);
		if (countryArea.getPid() != null) {
			CountryArea pCountryArea = countryAreaService.get(countryArea.getPid());
			model.addAttribute("pName", pCountryArea.getName());
		}
		return "system/countryAreaDetail";
	}
	
	/**
	 * 获取国别区域名称
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getName/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String get(@PathVariable("id") Integer id, Model model) {
		CountryArea countryArea = countryAreaService.get(id);
		return countryArea.getName();
	}
	
	@ModelAttribute
	public void getCountry(@RequestParam(value = "id", defaultValue = "-1") Integer id,Model model) {
		if (id != -1) {
			model.addAttribute("countryArea", countryAreaService.get(id));
		}
	}
	
}
