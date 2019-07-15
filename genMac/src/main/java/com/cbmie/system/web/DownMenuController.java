/*package com.cbmie.system.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.web.BaseController;
import com.cbmie.genMac.baseinfo.entity.WoodAffiBankInfo;
import com.cbmie.genMac.baseinfo.entity.WoodAffiBaseInfo;
import com.cbmie.genMac.baseinfo.service.AffiBankInfoService;
import com.cbmie.genMac.baseinfo.service.AffiBaseInfoService;
import com.cbmie.system.entity.DictChild;
import com.cbmie.system.entity.DictMain;
import com.cbmie.system.service.DictChildService;
import com.cbmie.system.service.DictMainService;
import com.cbmie.system.utils.DictUtils;
import com.cbmie.woodNZ.baseInfo.entity.WoodCk;
import com.cbmie.woodNZ.baseInfo.entity.WoodGk;
import com.cbmie.woodNZ.baseInfo.service.CkService;
import com.cbmie.woodNZ.baseInfo.service.GkService;
import com.cbmie.woodNZ.logistics.entity.DownstreamBill;
import com.cbmie.woodNZ.logistics.service.DownstreamBillService;

*//**
 * 字典controller
 *//*
@Controller
@RequestMapping("system/downMenu")
public class DownMenuController extends BaseController{
	
	@Autowired
	private DictMainService dictMainService;
	
	@Autowired
	private DictChildService dictChildService;
	
	@Autowired
	private AffiBankInfoService affiBankInfoService;
	
	@Autowired
	private AffiBaseInfoService affiBaseInfoService;
	
	@Autowired
	private GkService gkService;
	
	@Autowired
	private CkService ckService;
	
	@Autowired
	private DownstreamBillService downstreamBillService;
	
	
	
	*//**********************字典下拉相关 start*********************************//*
	*//**
	 * 根据code获取字典列表
	 * @param code
	 * @return
	 *//*
	@RequestMapping(value = "getDictByCode/{code}")
	@ResponseBody
	public List<DictChild> getDictByCode(@PathVariable("code") String code) {
		DictMain dictMain = dictMainService.findByCode(code);
		List<DictChild> dictChild = dictMain.getDictChild();
		return dictChild;
	}
	
	*//**
	 *  根据code和id 返回具体某个字典查询字典name（单选：字典列表对应值的值显示）
	 * @param code 字典code
	 * @param childId 当前value
	 * @return
	 *//*
	@RequestMapping(value = "getDictName/{code}/{value}")
	@ResponseBody
	public String getDictByCode(@PathVariable("code") String code,@PathVariable("value") String childId) {
//		DictMain dictMain = dictMainService.findByCode(code);
//		List<DictChild> dictChild = dictMain.getDictChild();
//		return dictChildService.getDictName(dictChild.get(0).getPid(), childId).getName();
		return DictUtils.getDictLabel(childId, code, "");
	}
	
	*//**
	 * 根据多选的字符串和字典值返回 多个字符串对应的值(多选：字典列表对应值的值显示)
	 * @param types 多选对应的字符串
	 * @param dict 字典值
	 * @return
	 *//*
	@RequestMapping(value = "getAllName/{types}/{dict}", method = RequestMethod.GET)
	@ResponseBody
	public String  getAllName(@PathVariable("types") String types,@PathVariable("dict") String dict) {
		if(StringUtils.isNotBlank(dict)){
			return dictChildService.findAllTypeNames(types,dict);
		} else {
			return "";
		}
		
	}

	*//**********************字典下拉相关 end*********************************//*
	
	*//**********************单位信息下拉相关 start*********************************//*
	
	*//**
	 * 符合条件单位信息下拉
	 * 根据页面 filter 控制要显示的单位信息
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value="jsonBaseInfo",method = RequestMethod.GET)
	@ResponseBody
	public List<WoodAffiBaseInfo> affiBaseInfoList(HttpServletRequest request) {
		List<WoodAffiBaseInfo> returnList = new ArrayList<WoodAffiBaseInfo>();
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		if(filters.size()>0){
			for(PropertyFilter filter : filters){
				String matchValue = (String) filter.getMatchValue();
				String[] matchValues = matchValue.split("or");
				for(int i=0; i<matchValues.length;i++){
					List<WoodAffiBaseInfo> list = affiBaseInfoService.getByCustomerType(matchValues[i]);
					list = validateOurUnit(matchValues[i],list);//如果选择公司类别编码1，禁止带出编码为10，11，12的公司信息
					returnList.addAll(list);
				}
			}
			
		}else{
			returnList.addAll(affiBaseInfoService.getAll());
		}
		return removeDuplicate(returnList);
	}
	
	*//**
	 * 去除重复单位信息列表
	 * 根据页面 filter 控制要显示的单位信息
	 * @param request
	 * @return
	 *//*
	public List<WoodAffiBaseInfo>  removeDuplicate(List<WoodAffiBaseInfo> list) {
	      HashSet h = new HashSet(list);
	      list.clear();
	      list.addAll(h);
	      return list;
	}
	
	*//**
	 * 如果选择公司类别编码1，禁止带出编码为10，11，12的公司信息
	 * 根据页面 filter 控制要显示的单位信息
	 * @param request
	 * @return
	 *//*
	public List<WoodAffiBaseInfo> validateOurUnit(String  customerType,List<WoodAffiBaseInfo> returnList) {
		if(customerType.equals("1")){//如果选择公司类别编码1，禁止带出编码为10，11，12的公司信息
			List<WoodAffiBaseInfo> newResult = new ArrayList<WoodAffiBaseInfo>();
			for(WoodAffiBaseInfo info:returnList){
				if(!info.getCustomerType().equals("10") && !info.getCustomerType().equals("11") && !info.getCustomerType().equals("12"))
					newResult.add(info);
			}
			returnList = newResult;
		}
		return returnList;
	}
	
	*//**
	 * 显示id对应的关联单位名称
	 * @param dict 
	 * @return
	 *//*
	@RequestMapping(value = "getBaseInfoName/{dict}", method = RequestMethod.GET)
	@ResponseBody
	public String  getAffiBaseInfoById(@PathVariable("dict") String dict) {
		if(StringUtils.isNotBlank(dict)){
			return affiBaseInfoService.get(Long.valueOf(dict)).getCustomerName();
		} else {
			return "";
		}
	}

	
	*//**
	 * 符合条件单位银行信息下拉
	 * 根据页面 filter 控制要显示的单位信息
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value="jsonBankInfo",method = RequestMethod.GET)
	@ResponseBody
	public List<WoodAffiBankInfo> affiBankInfoList(HttpServletRequest request) {
		List<WoodAffiBankInfo> returnList = new ArrayList<WoodAffiBankInfo>();
		List<WoodAffiBaseInfo> BaseInfoList = new ArrayList<WoodAffiBaseInfo>();
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		if(filters.size()>0){
			for(PropertyFilter filter : filters){
				BaseInfoList.addAll(affiBaseInfoService.getByCustomerType((String)filter.getMatchValue()));
			}
			for(WoodAffiBaseInfo woodAffiBaseInfo : BaseInfoList){
				returnList.addAll(woodAffiBaseInfo.getAffiBankInfo());
			}
		}else{
			returnList.addAll(affiBankInfoService.getAll());
		}
		return returnList;
	}
	
	*//**
	 * 根据父id获取银行信息
	 * @param data
	 * @return
	 *//*
	@RequestMapping(value = "selectBank/{pid}")
	@ResponseBody
	public  List<WoodAffiBankInfo> selectBank(@PathVariable("pid") String pid) {
		List<WoodAffiBankInfo> affiBankInfoList = affiBankInfoService.getByParentId(pid);
		return affiBankInfoList;
	}
	
	*//**
	 * 显示id对应的关联单位银行名称
	 * @param dict 
	 * @return
	 *//*
	@RequestMapping(value = "getBankInfoName/{dict}", method = RequestMethod.GET)
	@ResponseBody
	public String  getAffiBankInfoById(@PathVariable("dict") String dict) {
		if(StringUtils.isNotBlank(dict)){
			return affiBankInfoService.get(Long.valueOf(dict)).getBankName();
		}else{
			return "";
		}
		
	}
	
	*//**********************单位信息下拉相关 end*********************************//*
	
	*//**********************港口信息下拉相关 start*********************************//*
	*//**
	 * 符合条件港口信息下拉
	 * 根据页面 filter 控制要显示的单位信息
	 * @param request（目前支持根据国家筛选）
	 * @return
	 *//*
	@RequestMapping(value="jsonGK",method = RequestMethod.GET)
	@ResponseBody
	public List<WoodGk> gkList(HttpServletRequest request) {
		List<WoodGk> returnList = new ArrayList<WoodGk>();
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		if(filters.size()>0){
			for(PropertyFilter filter : filters){
				returnList.addAll(gkService.getByGJtype((String)filter.getMatchValue()));
			}
		}else{
			returnList.addAll(gkService.getAll());
		}
		return returnList;
	}
	
	*//**
	 * 显示id对应的港口名称
	 * @param dict 
	 * @return
	 *//*
	@RequestMapping(value = "getPortName/{dict}", method = RequestMethod.GET)
	@ResponseBody
	public String  getGKById(@PathVariable("dict") String dict) {
		if(StringUtils.isNotBlank(dict)){
			return gkService.get(Long.valueOf(dict)).getGkm();
		}else{
			return "";
		}
		
	}
	*//**********************港口信息下拉相关 end*********************************//*
	
	*//**********************仓库信息下拉相关 start*********************************//*
	*//**
	 * 符合条件仓库信息下拉
	 * 根据页面 filter 控制要显示的单位信息
	 * @param request (目前只支持更具国家地区筛选)
	 * @return
	 *//*
	@RequestMapping(value="jsonCK",method = RequestMethod.GET)
	@ResponseBody
	public List<WoodCk> ckList(HttpServletRequest request) {
		List<WoodCk> returnList = new ArrayList<WoodCk>();
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		if(filters.size()>0){
			for(PropertyFilter filter : filters){
				returnList.addAll(ckService.getByCKNationality((String)filter.getMatchValue()));
			}
		}else{
			returnList.addAll(ckService.getAll());
		}
		return returnList;
	}
	
	*//**
	 * 显示id对应的仓库名称
	 * @param dict 
	 * @return
	 *//*
	@RequestMapping(value = "getWarehouseName/{dict}", method = RequestMethod.GET)
	@ResponseBody
	public String  getCKById(@PathVariable("dict") String dict) {
		if(StringUtils.isNotBlank(dict)){
			return ckService.get(Long.valueOf(dict)).getCompanyName();
		}else{
			return "";
		}
		
	}
	*//**********************仓库信息下拉相关 end*********************************//*
	
	*//**
	 * 水单信息中认领的发票号 下拉菜单
	 * @return
	 *//*
	@RequestMapping(value = "getInvoiceNo/{contractId}", method = RequestMethod.GET)
	@ResponseBody
	public List<DownstreamBill>  getInvoiceNo(@PathVariable("contractId") String contractId) {
		return downstreamBillService.getInvoiceNoByConId(contractId);
	}
	
	
}
*/