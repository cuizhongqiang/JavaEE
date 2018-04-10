package com.cnbmtech.general.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.alibaba.fastjson.JSON;
import com.cnbmtech.base.controller.BaseController;
import com.cnbmtech.base.entity.Result;
import com.cnbmtech.base.service.AccessoryService;
import com.cnbmtech.base.utils.PropertyFilter;
import com.cnbmtech.general.entity.BriefInfor;
import com.cnbmtech.general.entity.StaffInfor;
import com.cnbmtech.general.service.BriefInforService;
import com.cnbmtech.general.service.StaffInforService;
import com.google.zxing.WriterException;

@Controller
@RequestMapping(value = "staffInfor")
public class StaffInforController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(StaffInforController.class);
	
	@Autowired
	private StaffInforService staffInforService;
	
	@Autowired
	private BriefInforService briefInforService;
	
	@Autowired
	private AccessoryService accessoryService;
	
	
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request, Model model) {
		return "staffInfor/staffInforList";
	}
	
	@RequestMapping(value = "jsonDate")
	@ResponseBody
	public Map<String, Object> jsonDate(HttpServletRequest request, Model model) {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		Page<StaffInfor> page = staffInforService.findPage(request, filters);
		return getTableData(page);
	}
	
	@RequestMapping(value = "toCreate")
	public String toUpdate(Model model) {
		model.addAttribute("action", "create");
		model.addAttribute("staffInfor", new StaffInfor());
		List<BriefInfor> briefInforList = briefInforService.getAll();
		model.addAttribute("briefInforList", briefInforList);
		return "staffInfor/staffInforForm";
	}
	
	@RequestMapping(value = "toUpdate/{action}/{id}")
    public String toUpdate(@PathVariable("action") String action, @PathVariable("id") Long id, Model model) {
		StaffInfor staffInfor = staffInforService.get(id);
		List<BriefInfor> briefInforList = briefInforService.getAll();
		model.addAttribute("briefInforList", briefInforList);
		model.addAttribute("staffInfor", staffInfor);
		return "staffInfor/staffInforForm";
	}
	
	@RequestMapping(value = "save/{action}")
	@ResponseBody
	public String update(@PathVariable("action") String action, @ModelAttribute("staffInfor") @RequestBody StaffInfor staffInfor) {
		try{
			Date currentDate = new Date();
			if (action.equals("create")) {
				staffInfor.setCreateDate(currentDate);
			} else {
				staffInfor.setUpdateDate(currentDate);
			}
			staffInforService.save(staffInfor);
		}catch(Exception ex){
			logger.error("save staffInfor()--error", ex);
			return "error";
		}
		return "success";
	}
	
	@RequestMapping(value = "toImg/{id}", method = RequestMethod.GET)
	public String toImg(Model model,@PathVariable("id") Long id) {
		model.addAttribute("currentPid", id);
		return "staffInfor/toUserImg";
	}
	
	
	@RequestMapping(value = "toShowImg/{id}", method = RequestMethod.GET)
	public String toShowImg(Model model,@PathVariable("id") Long id) {
		StaffInfor staffInfor = staffInforService.get(id);
		BriefInfor briefInfor = briefInforService.get(staffInfor.getRelationBrief());
		model.addAttribute("staffInfor", staffInfor);
		model.addAttribute("briefInfor", briefInfor);
		return "staffInfor/showAllImg";
	}
	
	/**
	 * 头像上传
	 * @param request
	 * @param id
	 * @throws IOException 
	 * @throws FileUploadException 
	 */
	@ResponseBody
	@RequestMapping(value = "avatarUpload/{id}", method = RequestMethod.POST)
	public String avatarUpload(HttpServletRequest request, @PathVariable("id") Long id) throws IOException, FileUploadException {
		StaffInfor staffInfor = staffInforService.get(id);
		Result result =  accessoryService.avatarUpload(request, staffInfor.getId(), StaffInfor.class.getName(), staffInfor.getAccId());
		staffInfor.setAccId(Long.valueOf(result.getSourceUrl()));
		staffInforService.save(staffInfor);
		return JSON.toJSONString(result);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws WriterException
	 * @throws IOException
	 * @throws FileUploadException 
	 */
	@RequestMapping("avatarView/{accId}")
    public ResponseEntity<byte[]> toUserSummer(@PathVariable("accId") Long accId)
            throws WriterException, IOException, FileUploadException{
        return accessoryService.avatarView(accId);
    }
	
    
    
    @RequestMapping(value = "toUpload", method = RequestMethod.GET)
	public String toUpload(Model model) {
		return "staffInfor/toUpload";
	}
    
    
	@RequestMapping(value = "readExcel", method = RequestMethod.POST)
	@ResponseBody
	public String readExcel( HttpServletRequest request) throws Exception   {
			MultipartRequest multipartRequest=(MultipartRequest) request;
			MultipartFile excelFile=multipartRequest.getFile("file_data");
			staffInforService.importExcel(excelFile);
			return "success";
	}
	
	@RequestMapping(value = "writeExcel")
	public void writeExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			staffInforService.writeExcel(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@ModelAttribute
	public void getObject(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("staffInfor", staffInforService.get(id));
		}
	}
	
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		staffInforService.delete(id);
		return "success";
	}
	
}
