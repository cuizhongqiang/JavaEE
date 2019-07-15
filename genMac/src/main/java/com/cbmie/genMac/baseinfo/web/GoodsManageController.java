package com.cbmie.genMac.baseinfo.web;

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

import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.genMac.baseinfo.entity.GoodsManage;
import com.cbmie.genMac.baseinfo.service.GoodsManageService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 商品管理controller
 */
@Controller
@RequestMapping("baseinfo/goodsManage")
public class GoodsManageController extends BaseController{
	
	@Autowired
	private GoodsManageService goodsManageService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "baseinfo/goodsManageList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value="json", method = RequestMethod.GET)
	@ResponseBody
	public List<GoodsManage> search(HttpServletRequest request) {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		return filters.size() > 0 ? goodsManageService.search(filters) : goodsManageService.getAll();
	}
	
	/**
	 * 获取所有无子节点的叶子节点
	 */
	@RequestMapping(value="json/isLeaf", method = RequestMethod.GET)
	@ResponseBody
	public List<GoodsManage> isLeafSearch() {
		return goodsManageService.getIsLeaf();
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create/{pid}", method = RequestMethod.GET)
	public String createForm(@PathVariable("pid") Long pid, Model model) {
		GoodsManage goodsManage = new GoodsManage();
		goodsManage.setGoodsCode(goodsManageService.getMaxCode(pid));
		model.addAttribute("goodsManage", goodsManage);
		model.addAttribute("action", "create");
		return "baseinfo/goodsManageForm";
	}

	/**
	 * 添加
	 * 
	 * @param GoodsManage
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid GoodsManage goodsManage, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		goodsManage.setCreaterNo(currentUser.getLoginName());
		goodsManage.setCreaterName(currentUser.getName());
		goodsManage.setCreaterDept(currentUser.getOrganization().getOrgName());
		goodsManage.setCreateDate(new Date());
		
		goodsManageService.save(goodsManage);
		return "success";
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
		model.addAttribute("goodsManage", goodsManageService.get(id));
		model.addAttribute("action", "update");
		return "baseinfo/goodsManageForm";
	}

	/**
	 * 修改
	 * 
	 * @param goodsManage
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody GoodsManage goodsManage, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		goodsManage.setUpdaterNo(currentUser.getLoginName());
		goodsManage.setUpdaterName(currentUser.getName());
		goodsManage.setCreaterDept(currentUser.getOrganization().getOrgName());
		goodsManage.setUpdateDate(new Date());
		
		goodsManageService.update(goodsManage);
		return "success";
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
		goodsManageService.delete(id);
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
		model.addAttribute("goodsManage", goodsManageService.get(id));
		return "baseinfo/goodsManageDetail";
	}
	
	@ModelAttribute
	public void getAffi(@RequestParam(value = "id", defaultValue = "-1") Long id,Model model) {
		if (id != -1) {
			model.addAttribute("goodsManage", goodsManageService.get(id));
		}
	}
	
	/**
	 * 验证编码唯一性
	 */
	@RequestMapping(value = "uniqueNo/{id}/{no}", method = RequestMethod.GET)
	@ResponseBody
	public boolean uniqueNo(@PathVariable("id") Long id, @PathVariable("no") String no) {
		return goodsManageService.findByNo(id, no) != null;
	}
	
	/**
	 * 根据商品编码获取商品
	 */
	@RequestMapping(value = "findByCode/{code}", method = RequestMethod.GET)
	@ResponseBody
	public String findByCode(@PathVariable("code") String code) {
		return goodsManageService.findBy(code);
	}
}
