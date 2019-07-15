package com.cbmie.common.web;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.cbmie.common.persistence.Page;
import com.cbmie.common.utils.DateUtils;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.system.entity.User;


/**
 * 基础控制器 
 * 其他控制器继承此控制器获得日期字段类型转换和防止XSS攻击的功能
 * @description 
 */
public class BaseController {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
		});
		
		// Timestamp 类型转换
		binder.registerCustomEditor(Timestamp.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				Date date = DateUtils.parseDate(text);
				setValue(date==null?null:new Timestamp(date.getTime()));
			}
		});
	}
	
	/**
	 * 获取page对象
	 * @param request
	 * @return page对象
	 */
	public <T> Page<T> getPage(HttpServletRequest request){
		int pageNo=1;	//当前页码
		int pageSize=20;	//每页行数
		String orderBy="id";	//排序字段
		String order="asc";	//排序顺序
		if(StringUtils.isNotEmpty(request.getParameter("page")))
			pageNo=Integer.valueOf(request.getParameter("page"));
		if(StringUtils.isNotEmpty(request.getParameter("rows")))
			pageSize=Integer.valueOf(request.getParameter("rows"));
		if(StringUtils.isNotEmpty(request.getParameter("sort")))
			orderBy=request.getParameter("sort").toString();
		if(StringUtils.isNotEmpty(request.getParameter("order")))
			order=request.getParameter("order").toString();
		return new Page<T>(pageNo, pageSize, orderBy, order);
	}
	
	/**
	 * 获取easyui分页数据
	 * @param page
	 * @return map对象
	 */
	public <T> Map<String, Object> getEasyUIData(Page<T> page){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", page.getResult());
		map.put("total", page.getTotalCount());
		return map;
	}
	
	/**
	 * 根据结果集构建分页
	 * @param page
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	public <T> void createPageByResult(Page<T> page, List<?> result) {
		page.setTotalCount(result.size());
		int firstResult = page.getFirst() - 1;
		int endResult = result.size() > (firstResult + page.getPageSize()) ? firstResult + page.getPageSize() : result.size();
		for (int i = firstResult; i < endResult; i++) {
			page.getResult().add((T) result.get(i));
		}
	}
	
	/**
	 * 用来获取当前登录用户
	 * @return 当前登录用户
	 */
	public User getCurUser() {
		
		//Object = null;
		User curUser = (User) SecurityUtils.getSubject().getPrincipal();
		return curUser;
	}
	
}
