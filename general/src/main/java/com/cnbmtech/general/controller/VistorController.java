package com.cnbmtech.general.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cnbmtech.base.controller.BaseController;
import com.cnbmtech.base.properties.ServerProperty;
import com.cnbmtech.base.service.AccessoryService;
import com.cnbmtech.base.utils.ChartGraphicsUtils;
import com.cnbmtech.base.utils.QrCodeCreateUtil;
import com.cnbmtech.general.entity.BriefInfor;
import com.cnbmtech.general.entity.StaffInfor;
import com.cnbmtech.general.service.BriefInforService;
import com.cnbmtech.general.service.StaffInforService;
import com.google.zxing.WriterException;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

@Controller
@RequestMapping(value = "vistor")
public class VistorController extends BaseController{
	
	
	@Autowired
	private StaffInforService staffInforService;
	
	@Autowired
	private BriefInforService briefInforService;
	
	@Autowired
	private ServerProperty serverProperty;
	
	@Autowired
	private AccessoryService accessoryService;
	
	
	@RequestMapping(value = "toVisitor/{id}")
    public String toUpdate( @PathVariable("id") Long id, Model model) {
		StaffInfor staffInfor = staffInforService.get(id);
		BriefInfor briefInfor = briefInforService.get(staffInfor.getRelationBrief());
		model.addAttribute("staffInfor", staffInfor);
		model.addAttribute("briefInfor", briefInfor);
		return "staffInfor/share";
	}
	
	/**
	 * 返回通讯录二维码
	 * @param id
	 * @return
	 * @throws WriterException
	 * @throws IOException
	 */
	@RequestMapping("toUserAdress/{id}")
    public ResponseEntity<byte[]> toUserAdress(@PathVariable("id") Long id)
            throws WriterException, IOException{
        return QrCodeCreateUtil.getResponseEntity(formateVCF(id), 150, 150, "png");
    }
    
    /**
	 * 返回个人介绍二维码
	 * @param id
	 * @return
	 * @throws WriterException
	 * @throws IOException
	 */
	@RequestMapping("toUserSummer/{id}")
    public ResponseEntity<byte[]> toUserSummer(@PathVariable("id") Long id)
            throws WriterException, IOException{
    	String url = "http://" + serverProperty.getUrl() + "/vistor/toVisitor/"+id;
        return QrCodeCreateUtil.getResponseEntity(url, 150, 150, "png");
    }
    
    /**
	 * 返回通讯录二维码
	 * @param id
	 * @return
	 * @throws WriterException
	 * @throws IOException
     * @throws BadHanyuPinyinOutputFormatCombination 
	 */
	@RequestMapping("toUserCard/{id}")
    public ResponseEntity<byte[]> toUserCard(@PathVariable("id") Long id,HttpServletRequest request)
            throws WriterException, IOException, BadHanyuPinyinOutputFormatCombination{
    	StaffInfor staffInfor = staffInforService.get(id);
    	String url = "http://" + serverProperty.getUrl() + "/vistor/toVisitor/"+id;
        return ChartGraphicsUtils.graphicsGeneration(staffInfor,request,url);
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
    public ResponseEntity<byte[]> avatarView(@PathVariable("accId") Long accId)
            throws WriterException, IOException, FileUploadException{
        return accessoryService.avatarView(accId);
    }
    
    public String formateVCF(Long id){
    	String returnValue ="";
    	StaffInfor staffInfor = staffInforService.get(id);
    	BriefInfor briefInfor = briefInforService.get(staffInfor.getRelationBrief());
    	returnValue = "BEGIN:VCARD\n" +
				"VERSION:3.0\n" +
				"N:"+staffInfor.getRealName()+"\n" +
				"EMAIL:"+staffInfor.getEmail()+"\n" +
				"TEL:"+staffInfor.getTelphone()+"\n"+
				"TEL;CELL:"+staffInfor.getMobile()+"\n" +
				"ADR:"+staffInfor.getAddress()+"\n" +
				"ORG:"+staffInfor.getCompName()+"\n" +
				"TITLE:"+staffInfor.getTitle()+"\n"+
				"URL:"+staffInfor.getWebsite()+"\n";
				if(briefInfor!=null){
					returnValue = returnValue+"NOTE:"+briefInfor.getTitle()+"\n";
				}
				returnValue = returnValue +"END:VCARD";
    	return returnValue;
    }
	    
	    
}
