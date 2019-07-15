package com.cbmie.genMac.shop.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.activiti.engine.ActivitiException;
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
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.utils.YesOrNo;
import com.cbmie.common.web.BaseController;
import com.cbmie.genMac.shop.entity.Goods;
import com.cbmie.genMac.shop.service.GoodsChildService;
import com.cbmie.genMac.shop.service.GoodsService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 商品controller
 */
@Controller
@RequestMapping("shop/goods")
public class GoodsController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private GoodsChildService goodsChildService;

	@Autowired
	protected ActivitiService activitiService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "shop/goodsList";
	}

	/**
	 * 获取商品json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> goodsList(HttpServletRequest request) {
		Page<Goods> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = goodsService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加商品跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("goods", new Goods());
		model.addAttribute("action", "create");
		return "shop/goodsForm";
	}

	/**
	 * 添加商品
	 * 
	 * @param goods
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Goods goods, Model model, @RequestParam("goodsChildJson") String goodsChildJson) {
		User currentUser = UserUtil.getCurrentUser();
		goods.setCreaterNo(currentUser.getLoginName());
		goods.setCreaterName(currentUser.getName());
		goods.setCreateDate(new Date());

		// 设置表单摘要，在工作流中展示使用
		goods.setSummary("商品Demo[" + goods.getName() + "]");

		// 提交新增
		goodsService.save(goods);
		// 保存子表对象
		goodsChildService.save(goods, goodsChildJson);
		return "success";
	}

	/**
	 * 修改商品跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("goods", goodsService.get(id));
		model.addAttribute("action", "update");
		return "shop/goodsForm";
	}

	/**
	 * 修改商品
	 * 
	 * @param goods
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody Goods goods, @RequestParam("goodsChildJson") String goodsChildJson, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		goods.setUpdaterNo(currentUser.getLoginName());
		goods.setUpdaterName(currentUser.getName());
		goods.setUpdateDate(new Date());

		// 修改表单摘要，在工作流中展示使用
		goods.setSummary("商品Demo[" + goods.getName() + "]");
		// 保存子表对象
		goodsChildService.save(goods, goodsChildJson);
		// 提交修改
		goodsService.update(goods);
		return "success";
	}

	/**
	 * 删除商品
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		goodsService.delete(id);
		return "success";
	}

	/**
	 * 提交流程申请
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "apply/{id}")
	@ResponseBody
	public String apply(@PathVariable("id") Long id, HttpSession session) {
		try {
			User user = UserUtil.getCurrentUser();
			Goods goods = goodsService.get(id);
			goods.setUserId(user.getLoginName());
			goodsService.save(goods);
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("price", goods.getPrice());// 传递开始参数
			activitiService.startWorkflow(goods, "goods", variables, user.getLoginName());

			return "success";
		} catch (ActivitiException e) {
			if (e.getMessage().indexOf("no processes deployed with key") != -1) {
				logger.warn("没有部署流程!", e);
				return "no deployment";
			} else {
				logger.error("启动流程失败：", e);
				return "start fail";
			}
		} catch (Exception e) {
			logger.error("启动流程失败：", e);
			return "start fail";
		}
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
				Goods goods = goodsService.get(id);
				goods.setProcessInstanceId(null);
				goods.setState(null);
				goodsService.save(goods);
				return "success";
			} else {
				return "已被签收，不能撤回！";
			}
		} catch (Exception e) {
			logger.error("撤回申请失败：", e);
			return "撤回申请失败！";
		}
	}

	@RequestMapping(value = "yesNoAjax")
	@ResponseBody
	public List<YesOrNo> yesNoAjax() {
		List<YesOrNo> yesOrNoList = new ArrayList<YesOrNo>();
		yesOrNoList.add(new YesOrNo("1", "是"));
		yesOrNoList.add(new YesOrNo("0", "否"));
		return yesOrNoList;
	}

	@ModelAttribute
	public void getGoods(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("goods", goodsService.get(id));
		}
	}

	/**
	 * 查看商品明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("goods", goodsService.get(id));
		return "shop/goodsInfo";
	}

	/**
	 * 打印页面跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "print", method = RequestMethod.GET)
	public String print() {
		return "shop/goodsPrint";
	}
	
	
}
