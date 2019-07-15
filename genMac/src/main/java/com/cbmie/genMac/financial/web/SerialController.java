package com.cbmie.genMac.financial.web;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.genMac.financial.entity.Serial;
import com.cbmie.genMac.financial.service.SerialService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;


/**
 * 水单controller
 */
@Controller
@RequestMapping("financial/serial")
public class SerialController extends BaseController {


	@Autowired
	private SerialService serialService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "financial/serialList";
	}

	/**
	 * 获取水单json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> goodsList(HttpServletRequest request) {
		Page<Serial> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = serialService.search(page, filters);
		List<Serial> serialList= page.getResult();
		List<Serial> newList = new ArrayList<Serial>();
		if(serialList.size()>0){
			Iterator<Serial> it = serialList.iterator();
			while(it.hasNext()){
				Serial serialObj = (Serial) it.next();
				if(serialObj.getSplitStatus()!=null&&serialObj.getSplitStatus().equals("parent")){
					it.remove();
				}else{
					newList.add(serialObj);
				}
			}
		}
		page.setResult(newList);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取list
	 */
	@RequestMapping(value="filter",method = RequestMethod.GET)
	@ResponseBody
	public List<Serial> filter(HttpServletRequest request) {
		return serialService.search(PropertyFilter.buildFromHttpRequest(request));
	}

	/**
	 * 添加水单跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("serial", new Serial());
		model.addAttribute("action", "create");
		return "financial/serialForm";
	}

	/**
	 * 添加水单
	 * 
	 * @param serial
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Serial serial, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		serial.setCreaterNo(currentUser.getLoginName());
		serial.setCreaterName(currentUser.getName());
		serial.setCreateDate(new Date());
		serial.setCreaterDept(currentUser.getOrganization().getOrgName());
		serialService.save(serial);
		return "success";
	}

	/**
	 * 修改水单跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("serial", serialService.get(id));
		model.addAttribute("action", "update");
		return "financial/serialForm";
	}

	/**
	 * 修改水单
	 * 
	 * @param serial
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody Serial serial, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		serial.setUpdaterNo(currentUser.getLoginName());
		serial.setUpdaterName(currentUser.getName());
		serial.setUpdateDate(new Date());
		serialService.update(serial);
		return "success";
	}
	
	/**
	 * 认领水单跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "claim/{id}", method = RequestMethod.GET)
	public String claim(@PathVariable("id") Long id, Model model) {
		model.addAttribute("serial", serialService.get(id));
		return "financial/serialClaim";
	}

	/**
	 * 认领水单
	 * 
	 * @param serial
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "claim", method = RequestMethod.POST)
	@ResponseBody
	public String claim(@Valid @ModelAttribute @RequestBody Serial serial, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		serial.setUpdaterNo(currentUser.getLoginName());
		serial.setUpdaterName(currentUser.getName());
		serial.setUpdateDate(new Date());
		serialService.update(serial);
		return "success";
	}
	
	
	/**
	 * 取消认领水单
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "cancelClaim/{id}")
	@ResponseBody
	public String cancelClaim(@PathVariable("id") Long id) {
		Serial serial = serialService.get(id);
		serial.setContractNo(null);
		serial.setComments("");
		serial.setSerialCategory(null);
		serialService.update(serial);
		return "success";
	}
	
	
	/**
	 * 删除水单
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		serialService.delete(id);
		return "success";
	}
	
	/**
	 * 拆分水单跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "split/{id}", method = RequestMethod.GET)
	public String split(@PathVariable("id") Long id, Model model) {
		model.addAttribute("serial", serialService.get(id));
		return "financial/serialSplit";
	}

	/**
	 * 拆分水单
	 * 
	 * @param serial
	 * @param model
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "split", method = RequestMethod.POST)
	@ResponseBody
	public String split(@Valid @ModelAttribute @RequestBody Serial serial, Model mode,@RequestParam("perAmoutJson") String perAmoutJson) throws IllegalAccessException, InvocationTargetException {
		String[] numArray = perAmoutJson.split(",");
		User currentUser = UserUtil.getCurrentUser();
		List<Serial> serialList = new ArrayList<Serial>();
		//获取拆分后的水单list
		for(int i = 0; i < numArray.length; i++){
			Serial serialNew = new Serial();
			BeanUtils.copyProperties(serialNew, serial);
			serialNew.setSplitStatus(String.valueOf(serial.getId()));
			serialNew.setSerialNumber(serial.getSerialNumber() + "_" + i);
			serialNew.setId(null);
			serialNew.setFundSource("内部拆分");
			serialNew.setMoney(Double.valueOf(numArray[i]));
			serialNew.setCreaterNo(currentUser.getLoginName());
			serialNew.setCreaterName(currentUser.getName());
			serialNew.setCreateDate(new Date());
			serialNew.setCreaterDept(currentUser.getOrganization().getOrgName());
			serialList.add(serialNew);
		}
		//插入拆分后的水单
		for(int i=0;i<serialList.size();i++){
			serialService.save(serialList.get(i));
		}
		//更新被拆分水单状态
		serial.setSplitStatus("parent");
		serialService.update(serial);
		return "success";
	}
	
	/**
	 * 取消拆分除水单
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "cancelSplit/{id}")
	@ResponseBody
	public String cancelSplit(@PathVariable("id") Long id) {
		String parentId = serialService.get(id).getSplitStatus();
		List<Serial> serialList  = serialService.findByParentId(parentId);
		//判断是否存在删除的水单
		if(serialList.size()>1){
			Serial serialSource = serialService.get(Long.valueOf(serialList.get(0).getSplitStatus()));
			Serial serial = null;
			int totalMoney = 0;
			for(int i=0;i<serialList.size();i++){
				serial = new Serial();
				serial = serialList.get(i);
				if(serial.getContractNo()!=null){
					return "存在已经认领的水单,不能撤销！";
				}
				totalMoney += Double.valueOf(serial.getMoney()).intValue();
			}
			if(totalMoney!= Double.valueOf(serialSource.getMoney()).intValue()){
				return "存在删除的拆分后的水单！不能取消拆分!";
			}
			//删除被拆分后的水单
			for(int i=0;i<serialList.size();i++){
				serialService.delete(serialList.get(i).getId());
			}
			//恢复原水单状态
			serialSource.setSplitStatus(null);
			serialService.update(serialSource);
			return "success";
		}else{
			return "存在删除的拆分后的水单！不能取消拆分!";
		}
	}

	/**
	 * 查看水单明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("serial", serialService.get(id));
		return "financial/serialDetail";
	}
	
	@ModelAttribute
	public void getSerial(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("serial", serialService.get(id));
		}
	}

	/**
	 * 验证流水号唯一性
	 */
	@RequestMapping(value = "uniqueNo/{id}/{no}", method = RequestMethod.GET)
	@ResponseBody
	public boolean uniqueNo(@PathVariable("id") Long id, @PathVariable("no") String no) {
		return serialService.findByNo(id, no) != null;
	}
	
	/**
	 * 水单总金额
	 * @param type 水单类型
	 * @param contractNo 合同号
	 * @return
	 */
	@RequestMapping(value = "sum/{type}/{contractNo}", method = RequestMethod.GET)
	@ResponseBody
	public Double sum(@PathVariable("type") String type, @PathVariable("contractNo") String contractNo) {
		return serialService.sum(contractNo, type);
	}
}
