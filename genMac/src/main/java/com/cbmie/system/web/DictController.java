package com.cbmie.system.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.cbmie.common.web.BaseController;
import com.cbmie.system.entity.DictChild;
import com.cbmie.system.entity.DictMain;
import com.cbmie.system.service.DictMainService;
import com.cbmie.system.utils.DictUtils;
import com.cbmie.system.utils.UserUtil;
import com.cbmie.system.service.DictChildService;

/**
 * 字典controller
 */
@Controller
@RequestMapping("system/dict")
public class DictController extends BaseController{
	
	@Autowired
	private DictMainService dictMainService;
	
	@Autowired
	private DictChildService dictChildService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "system/dictList";
	}

	/**
	 * 获取字典json
	 */
	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> dictList(HttpServletRequest request) {
		Page<DictMain> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = dictMainService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 查看字典跳转
	 */
	@RequiresPermissions("sys:dict:detail")
	@RequestMapping(value="detail/{id}",method = RequestMethod.GET)
	public String detail(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("dictMain", dictMainService.get(id));
		return "system/dictDetail";
	}
	
	/**
	 * 添加字典跳转
	 * 
	 * @param model
	 */
	@RequiresPermissions("sys:dict:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("dictMain", new DictMain());
		model.addAttribute("action", "create");
		return "system/dictForm";
	}

	/**
	 * 添加字典
	 * 
	 * @param dictMain
	 * @param model
	 */
	@RequiresPermissions("sys:dict:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid DictMain dictMain, Model model, @RequestParam("dictChildJson") String dictChildJson) {
		dictMain.setRegistrant(UserUtil.getCurrentUser().getName());
		dictMainService.save(dictMain);
		dictChildService.save(dictMain, dictChildJson);
		return "success";
	}

	/**
	 * 修改字典跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:dict:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("dictMain", dictMainService.get(id));
		model.addAttribute("action", "update");
		return "system/dictForm";
	}

	/**
	 * 修改字典
	 * 
	 * @param dictMain
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:dict:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody DictMain dictMain, Model model, @RequestParam("dictChildJson") String dictChildJson) {
		dictChildService.save(dictMain, dictChildJson);
		dictMainService.update(dictMain);
		return "success";
	}

	/**
	 * 删除字典
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:dict:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		dictMainService.delete(id);
		return "success";
	}
	
	/**
	 * 根据code获取字典
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "getDictByCode/{code}")
	@ResponseBody
	public List<DictChild> getDictByCode(@PathVariable("code") String code) {
		return DictUtils.getDictList(code);
	}
	
	/**
	 *  根据code和id 返回具体某个字典查询字典name
	 * @param code 字典code
	 * @param value 当前value
	 * @return
	 */
	@RequestMapping(value = "getDictName/{code}/{id}")
	@ResponseBody
	public String getDictByCode(@PathVariable("code") String code,@PathVariable("id") Integer id) {
		DictMain dictMain = dictMainService.findByCode(code);
		List<DictChild> dictChild = dictMain.getDictChild();
		return dictChildService.getDictName(dictChild.get(0).getPid(), id).getName();
	}
	
	/**
	 * 根据多选的字符串和字典值返回 多个字符串对应的值
	 * @param types 多选对应的字符串
	 * @param dict 字典值
	 * @return
	 */
	@RequestMapping(value = "getAllName/{types}/{dict}", method = RequestMethod.GET)
	@ResponseBody
	public String getAffiBankInfoByNO(@PathVariable("types") String types,@PathVariable("dict") String dict) {
		return dictChildService.findAllTypeNames(types,dict);
	}

	@ModelAttribute
	public void getDict(@RequestParam(value = "id", defaultValue = "-1") Integer id,Model model) {
		if (id != -1) {
			model.addAttribute("dictMain", dictMainService.get(id));
		}
	}

}
