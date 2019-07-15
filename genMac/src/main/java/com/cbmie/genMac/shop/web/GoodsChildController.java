package com.cbmie.genMac.shop.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.web.BaseController;

/**
 * 商品controller
 */
@Controller
@RequestMapping("shop/goodsChild")
public class GoodsChildController extends BaseController {

	/**
	 * 页面获取type
	 */
	@RequestMapping(value = "getType", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getType() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("type", "1");
		map1.put("typeText", "重要");
		list.add(map1);
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("type", "2");
		map2.put("typeText", "次要");
		list.add(map2);
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("type", "3");
		map3.put("typeText", "一般");
		list.add(map3);
		return list;
	}

}
