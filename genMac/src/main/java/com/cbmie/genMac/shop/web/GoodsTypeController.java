package com.cbmie.genMac.shop.web;

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
import com.cbmie.genMac.shop.entity.GoodsType;
import com.cbmie.genMac.shop.service.GoodsTypeService;

/**
 * 商品类型controller
 */
@Controller
@RequestMapping("shop/goodsType")
public class GoodsTypeController extends BaseController{

	@Autowired
	private GoodsTypeService goodsTypeService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "shop/goodsTypeList";
	}
	
	/**
	 * 获取商品类型类型json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public List<GoodsType> goodsTypeList(HttpServletRequest request) {
		List<GoodsType> goodsTypeList=goodsTypeService.getAll();
		return goodsTypeList;
	}
	
	/**
	 * 添加商品类型跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("goodsType", new GoodsType());
		model.addAttribute("action", "create");
		return "shop/goodsTypeForm";
	}

	/**
	 * 添加商品类型
	 * 
	 * @param goodsType
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid GoodsType goodsType, Model model) {
		goodsTypeService.save(goodsType);
		return "success";
	}

	/**
	 * 修改商品类型跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("goodsType", goodsTypeService.get(id));
		model.addAttribute("action", "update");
		return "shop/goodsTypeForm";
	}

	/**
	 * 修改商品类型
	 * 
	 * @param goodsType
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody GoodsType goodsType,Model model) {
		goodsTypeService.update(goodsType);
		return "success";
	}

	/**
	 * 删除商品类型
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		goodsTypeService.delete(id);
		return "success";
	}
	
	/**
	 * 根据id编号获取类型名称
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "typeAjax/{id}")
	@ResponseBody
	public String typeAjax(@PathVariable("id") Long id) {
		GoodsType gt = goodsTypeService.get(id);
		return gt.getName();
	}
	
	@RequestMapping(value = "allTypeAjax")
	@ResponseBody
	public List<GoodsType> allTypeAjax() {
		List<GoodsType> goodsTypeList=goodsTypeService.getAll();
		return goodsTypeList;
	}
	
	@ModelAttribute
	public void getGoodsType(@RequestParam(value = "id", defaultValue = "-1") Long id,Model model) {
		if (id != -1) {
			model.addAttribute("goodsType", goodsTypeService.get(id));
		}
	}
}
