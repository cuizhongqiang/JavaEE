package com.cnbmtech.general.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnbmtech.base.controller.BaseController;
import com.cnbmtech.base.utils.JXLExcel;
import com.cnbmtech.base.utils.PropertyFilter;
import com.cnbmtech.general.entity.AttendReg;
import com.cnbmtech.general.service.AttendRegChildService;
import com.cnbmtech.general.service.AttendRegService;

@Controller
@RequestMapping(value = "attendReg")
public class AttendRegController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(AttendRegController.class);
	
	@Autowired
	private AttendRegService attendRegService;
	
	@Autowired
	private AttendRegChildService attendRegChildService;
	
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request, Model model) {
		return "meeting/attendRegList";
	}
	
	@RequestMapping(value = "jsonDate")
	@ResponseBody
	public Map<String, Object> jsonDate(HttpServletRequest request, Model model) {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		Page<AttendReg> page = attendRegService.findPage(request, filters);
		return getTableData(page);
	}
	
	@RequestMapping(value = "toUpdate/{action}/{id}")
    public String toUpdate(@PathVariable("action") String action, @PathVariable("id") Long id, Model model) {
		AttendReg attendReg = attendRegService.get(id);
		model.addAttribute("attendReg", attendReg);
		return "meeting/attendRegForm";
	}
	
	@RequestMapping(value = "save/{action}")
	@ResponseBody
	public String update(@PathVariable("action") String action, @ModelAttribute("attendReg") @RequestBody @Valid AttendReg attendReg) {
		try{
			attendReg.setUpdateDate(new Date());
			attendRegService.save(attendReg);
			//收集项
			attendRegChildService.update(attendReg);
		}catch(Exception ex){
			logger.error("save attendReg()--error", ex);
			return "error";
		}
		return "success";
	}
	
	@ModelAttribute
	public void getObject(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("attendReg", attendRegService.get(id));
		}
	}
	
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		attendRegService.delete(id);
		return "success";
	}
	
	/**
	 * 导出excel
	 */
	@RequestMapping(value = "excelOut")
	public void excelOut(HttpServletRequest request, HttpServletResponse response) {
		List<AttendReg> list = attendRegService.getAll();
		JXLExcel jxl = new JXLExcel();
		// excel名称
		String excelName = "meeting.xls";
		// 设置标题
		String[] columnNames = new String[] { "类别", "公司名称", "姓名", "手机", "电子邮箱", "职位", "渠道" };
		jxl.setColumnNames(columnNames);
		// 设置属性名称
		String[] dbColumnNames = new String[] { "type", "company", "realName", "phone", "email", "position", "channel" };
		jxl.setDbColumnNames(dbColumnNames);
		// 执行
		jxl.exportExcel(response, list, excelName);
	}
	
}
