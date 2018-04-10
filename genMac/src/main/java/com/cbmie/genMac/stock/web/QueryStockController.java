package com.cbmie.genMac.stock.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.web.BaseController;
import com.cbmie.genMac.stock.entity.QueryStockDetail;
import com.cbmie.genMac.stock.service.QueryStockService;

/**
 * 库存商品查询controller
 */
@Controller
@RequestMapping("stock/queryStock")
public class QueryStockController extends BaseController {

	@Autowired
	private QueryStockService queryStockService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "stock/queryStockList";
	}

	/**
	 * 获取库存商品明细json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public List<QueryStockDetail> planStockList(HttpServletRequest request) {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		return filters.size() > 0 || StringUtils.isNotBlank(request.getParameter("goodsNameSpecification"))
				? queryStockService.queryStock(filters, request) : new ArrayList<QueryStockDetail>();
	}
	
	/**
	 * 获取盘库的亏、盈和
	 * @param warehouseName 仓库名称
	 * @param goodsNameSpecification 商品
	 * @return
	 */
	@RequestMapping(value = "findPlanSum", method = RequestMethod.POST)
	@ResponseBody
	public Object findPlanSum(@RequestParam("warehouseName") String warehouseName, 
			@RequestParam("goodsNameSpecification") String goodsNameSpecification) {
		return queryStockService.findPlanSum(warehouseName, goodsNameSpecification);
	}

}
