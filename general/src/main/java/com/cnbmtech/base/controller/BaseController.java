package com.cnbmtech.base.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;

public class BaseController {
	
	/**
	 * 获取分页数据
	 * @param page
	 * @return map对象
	 */
	public <T> Map<String, Object> getTableData(Page<T> page){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", page.getContent());
		map.put("total", page.getTotalElements());
		return map;
	}

}
