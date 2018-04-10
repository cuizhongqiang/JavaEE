package com.cnbmtech.general.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnbmtech.base.controller.BaseController;
import com.cnbmtech.base.properties.ServerProperty;
import com.cnbmtech.base.utils.PropertyFilter;
import com.cnbmtech.base.utils.QrCodeCreateUtil;
import com.cnbmtech.base.utils.ServletUtils;
import com.cnbmtech.general.entity.AttendReg;
import com.cnbmtech.general.entity.Template;
import com.cnbmtech.general.entity.TemplateCollect;
import com.cnbmtech.general.service.AttendRegChildService;
import com.cnbmtech.general.service.AttendRegService;
import com.cnbmtech.general.service.TemplateService;

@Controller
@RequestMapping(value = "template")
public class TemplateController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(TemplateController.class);
	
	@Autowired
	private TemplateService templateService;
	
	@Autowired
	private AttendRegService attendRegService;
	
	@Autowired
	private AttendRegChildService attendRegChildService;
	
	@Autowired
	private ServerProperty serverProperty;
	
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request, Model model) {
		return "meeting/templateList";
	}
	
	@RequestMapping(value = "jsonDate")
	@ResponseBody
	public Map<String, Object> jsonDate(HttpServletRequest request, Model model) {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		Page<Template> page = templateService.findPage(request, filters);
		return getTableData(page);
	}
	
	@RequestMapping(value = "toCreate")
	public String toUpdate(Model model) {
		model.addAttribute("action", "create");
		model.addAttribute("template", new Template());
		return "meeting/templateForm";
	}
	
	@RequestMapping(value = "toUpdate/{action}/{id}")
    public String toUpdate(@PathVariable("action") String action, @PathVariable("id") Long id, Model model) {
		Template template = templateService.get(id);
		List<Long> collectIds = new ArrayList<Long>();
		for (TemplateCollect tc : template.getTemplateCollect()) {
			collectIds.add(tc.getCollect().getId());
		}
		model.addAttribute("template", template);
		model.addAttribute("collectIds", collectIds);
		return "meeting/templateForm";
	}
	
	@RequestMapping(value = "save/{action}")
	@ResponseBody
	public String update(@PathVariable("action") String action, @ModelAttribute("template") @RequestBody Template template, HttpServletRequest request) {
		try{
			Date currentDate = new Date();
			if (action.equals("create")) {
				template.setCreateDate(currentDate);
			} else {
				template.setUpdateDate(currentDate);
			}
			templateService.save(template);
			String[] collectIds = request.getParameterValues("collectIds");
			templateService.saveCollect(collectIds, template);
		}catch(Exception ex){
			logger.error("save template()--error", ex);
			return "error";
		}
		return "success";
	}
	
	@ModelAttribute
	public void getObject(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("template", templateService.get(id));
		}
	}
	
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		templateService.delete(id);
		return "success";
	}
	
	@RequestMapping(value = "updateStatus/{id}")
	@ResponseBody
	public String updateStatus(@PathVariable("id") Long id) {
		Template template = templateService.get(id);
		if (template.getStatus() == 0) {
			template.setStatus(1);
		} else {
			template.setStatus(0);
		}
		templateService.update(template);
		return "success";
	}
	
	@RequestMapping(value = "con_add/{id}/{channel}")
	public String con_add(@PathVariable("id") Long id, @PathVariable("channel") String channel, Model model, HttpServletRequest request) {
		Template template = templateService.get(id);
		model.addAttribute("template", template);
		//验证ip是否已注册
		List<AttendReg> flagList = attendRegService.findByTypeAndRegIp(template.getTitle(), ServletUtils.getIp(request));
		model.addAttribute("ipFlag", flagList.size() > 0);
		return "meeting/Con_Add";
	}
	
	@RequestMapping(value = "con_add_save")
	@ResponseBody
	public String con_add_save(@ModelAttribute("attendReg") @RequestBody @Valid AttendReg attendReg, HttpServletRequest request) {
		try{
			attendReg.setRegIp(ServletUtils.getIp(request));
			attendReg.setCreateDate(new Date());
			attendRegService.save(attendReg);
			//收集项
			attendRegChildService.insert(attendReg);
		}catch(Exception ex){
			logger.error("con_add_save --error", ex);
			return "error";
		}
		return "success";
	}
	
	/**
	 * 返回二维码
	 */
	@RequestMapping("qrCode/{id}/{channel}")
    public ResponseEntity<byte[]> qrCode(@PathVariable("id") Long id, @PathVariable("channel") Integer channel) {
    	String url = "http://" + serverProperty.getUrl() + "/template/con_add/" + id + "/self";
    	if (channel == 1) {
    		url = "http://" + serverProperty.getUrl() + "/template/channel/" + id;
    	}
        try {
			return QrCodeCreateUtil.getResponseEntity(url, 150, 150, "png");
		} catch (Exception ex) {
			logger.error("qrCode --error", ex);
		}
        return null;
    }
    
    @RequestMapping(value = "channel/{id}")
	public String channel(@PathVariable("id") Long id, Model model) {
    	Template template = templateService.get(id);
		model.addAttribute("template", template);
		return "meeting/channel";
	}
    
    @RequestMapping(value = "channel/{id}/{name}")
	public String channelImg(@PathVariable("id") Long id, @PathVariable("name") String name) {
		return "meeting/channelImg";
	}
    
    /**
	 * 返回渠道注册二维码
	 */
	@RequestMapping("qrCode/channel/{id}/{name}")
    public ResponseEntity<byte[]> qrCodeChannel(@PathVariable("id") Long id, @PathVariable("name") String name) {
    	String url = "http://" + serverProperty.getUrl() + "/template/con_add/" + id + "/" + name;
        try {
			return QrCodeCreateUtil.getResponseEntity(url, 150, 150, "png");
		} catch (Exception ex) {
			logger.error("qrCode --error", ex);
		}
        return null;
    }
	
}
