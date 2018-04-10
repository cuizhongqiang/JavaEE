package com.cbmie.system.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.system.entity.Log;
import com.cbmie.system.service.LogService;

/**
 * 日志controller
 */
@Controller
@RequestMapping("system/log")
public class LogController extends BaseController{

	@Autowired
	private LogService logService;
	
	/**
	 * 默认页面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(){
		return "system/logList";
	}
	
	/**
	 * 获取日志json
	 */
	@RequiresPermissions("sys:log:view")
	@RequestMapping("json")
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request) {
		Page<Log> logPage=getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		if(filters.size()>0){
			logPage.setPageNo(1);
		}
		logPage = logService.search(logPage, filters);
		//构造easyui表格数据
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("rows", logPage.getResult());
		map.put("total", logPage.getTotalCount());
		return map;
	}
	
	/**
	 * 删除日志
	 * @param id
	 */
	@RequiresPermissions("sys:log:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		logService.delete(id);
		return "success";
	}
	
	/**
	 * 批量删除日志
	 * @param idList
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestBody List<Integer> idList) {
		logService.deleteLog(idList);
		return "success";
	}

	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<Log> list = logService.getAll();//获取数据
        logService.excelLog(response, list);
	}
}
