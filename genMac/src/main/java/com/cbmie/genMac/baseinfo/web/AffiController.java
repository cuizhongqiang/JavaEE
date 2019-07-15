package com.cbmie.genMac.baseinfo.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.web.BaseController;
import com.cbmie.genMac.baseinfo.entity.AffiBankInfo;
import com.cbmie.genMac.baseinfo.entity.AffiBaseInfo;
import com.cbmie.genMac.baseinfo.service.AffiAssureInfoService;
import com.cbmie.genMac.baseinfo.service.AffiBankInfoService;
import com.cbmie.genMac.baseinfo.service.AffiBaseInfoService;
import com.cbmie.genMac.baseinfo.service.AffiCustomerInfoService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 关联单位controller
 */
@Controller
@RequestMapping("baseinfo/affiliates")
public class AffiController extends BaseController{
	
	@Autowired
	private AffiBankInfoService affiBankInfoService;
	
	@Autowired
	private AffiBaseInfoService affiBaseInfoService;
	
	@Autowired
	private AffiCustomerInfoService affiCustomerInfoService;
	
	@Autowired
	private AffiAssureInfoService affiAssureInfoService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "baseinfo/affiBaseInfoList";
	}
	
	/**
	 * 获取json 关联单位基本信息
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> affiBaseInfoList(HttpServletRequest request) {
		Page<AffiBaseInfo> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		String customerType = request.getParameter("customerType");
		// 增加客户类型过滤条件
		if (StringUtils.isNotBlank(customerType)) {
			for (String str : StringUtils.split(customerType, ",")) {
				PropertyFilter filter = new PropertyFilter("LIKES_customerType", str);
				filters.add(filter);
			}
		}
		page = affiBaseInfoService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取list
	 */
	@RequestMapping(value="filter",method = RequestMethod.GET)
	@ResponseBody
	public List<AffiBaseInfo> filter(HttpServletRequest request) {
		return affiBaseInfoService.search(PropertyFilter.buildFromHttpRequest(request));
	}
	
	/**
	 * 添加关联单位跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("affiBaseInfo", new AffiBaseInfo());
		model.addAttribute("action", "create");
		return "baseinfo/affiBaseInfoForm";
	}

	/**
	 * 添加关联单位
	 * 
	 * @param AffiBaseInfo
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid AffiBaseInfo affiBaseInfo, Model model,
			@RequestParam("affiBankJson") String affiBankJson,
			@RequestParam("affiCustomerJson") String affiCustomerJson,
			@RequestParam("affiAssureJson") String affiAssureJson) {
		User currentUser = UserUtil.getCurrentUser();
		affiBaseInfo.setCreaterNo(currentUser.getLoginName());
		affiBaseInfo.setCreaterName(currentUser.getName());
		affiBaseInfo.setCreateDate(new Date());
		affiBaseInfo.setCreaterDept(currentUser.getOrganization().getOrgName());
		affiBaseInfoService.save(affiBaseInfo);
		if (StringUtils.isNotBlank(affiBankJson)) {
			affiBankInfoService.save(affiBaseInfo, affiBankJson);
		}
		if (StringUtils.isNotBlank(affiCustomerJson)) {
			affiCustomerInfoService.save(affiBaseInfo, affiCustomerJson);
		}
		if (StringUtils.isNotBlank(affiAssureJson)) {
			affiAssureInfoService.save(affiBaseInfo, affiAssureJson);
		}
		return "success";
	}

	/**
	 * 修改关联单位跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("affiBaseInfo", affiBaseInfoService.get(id));
		model.addAttribute("action", "update");
		return "baseinfo/affiBaseInfoForm";
	}

	/**
	 * 修改关联单位
	 * 
	 * @param port
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody AffiBaseInfo affiBaseInfo, Model model,
			@RequestParam("affiBankJson") String affiBankJson,
			@RequestParam("affiCustomerJson") String affiCustomerJson,
			@RequestParam("affiAssureJson") String affiAssureJson) {
		User currentUser = UserUtil.getCurrentUser();
		affiBaseInfo.setUpdaterNo(currentUser.getLoginName());
		affiBaseInfo.setUpdaterName(currentUser.getName());
		affiBaseInfo.setUpdateDate(new Date());
		affiBaseInfoService.update(affiBaseInfo);
		if (StringUtils.isNotBlank(affiBankJson)) {
			affiBankInfoService.save(affiBaseInfo, affiBankJson);
		}
		if (StringUtils.isNotBlank(affiCustomerJson)) {
			affiCustomerInfoService.save(affiBaseInfo, affiCustomerJson);
		}
		if (StringUtils.isNotBlank(affiAssureJson)) {
			affiAssureInfoService.save(affiBaseInfo, affiAssureJson);
		}
		return "success";
	}

	/**
	 * 删除关联单位
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		affiBaseInfoService.delete(id);
		return "success";
	}
	
	/**
	 * 关联单位明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("affiBaseInfo", affiBaseInfoService.get(id));
		model.addAttribute("affiCustomerInfo", affiCustomerInfoService.getByParentId(Long.toString(id)));
		return "baseinfo/affiliatesDetail";
	}
	
	/**
	 * 验证编码唯一性
	 */
	@RequestMapping(value = "uniqueNo/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean uniqueNo(@PathVariable("id") Long id, @RequestParam("customerCode") String customerCode) {
		return affiBaseInfoService.findByNo(id, customerCode) != null;
	}
	
	/**
	 * 构造客户编码
	 */
	@RequestMapping(value = "generateCode", method = RequestMethod.GET)
	@ResponseBody
	public String generateCode(@RequestParam("customerName") String customerName) {
		return affiBaseInfoService.generateCode(customerName);
	}
	
	
	@RequestMapping(value = "getCompany/{code}")
	@ResponseBody
	public List<AffiBaseInfo> getCompany(@PathVariable("code") String code) {
		List<AffiBaseInfo> returnList = new ArrayList<AffiBaseInfo>();
		for (String str : code.split(",")) {
			returnList.addAll(affiBaseInfoService.getCompany(str));
		}
		return returnList;
	}
	
	/**
	 * 根据父id获取银行信息
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "selectBank/{pid}")
	@ResponseBody
	public  List<AffiBankInfo> selectBank(@PathVariable("pid") String pid) {
		List<AffiBankInfo> affiBankInfoList = affiBankInfoService.getByParentId(pid);
		return affiBankInfoList;
	}
	
	/**
	 *  全部银行信息
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "selectBank")
	@ResponseBody
	public  List<AffiBankInfo> selectBank() {
		List<AffiBankInfo> affiBankInfoList = affiBankInfoService.getAll();
		return affiBankInfoList;
	}
	
	@ModelAttribute
	public void getAffi(@RequestParam(value = "id", defaultValue = "-1") Long id,Model model) {
		if (id != -1) {
			model.addAttribute("affiBaseInfo", affiBaseInfoService.get(id));
		}
	}
	
}
