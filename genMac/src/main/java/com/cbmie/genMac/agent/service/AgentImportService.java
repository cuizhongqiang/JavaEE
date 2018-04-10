package com.cbmie.genMac.agent.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.service.BaseService;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.genMac.baseinfo.entity.AffiBaseInfo;
import com.cbmie.genMac.baseinfo.service.AffiBaseInfoService;
import com.cbmie.genMac.credit.entity.OpenCredit;
import com.cbmie.genMac.credit.service.OpenCreditService;
import com.cbmie.genMac.financial.service.ExpenseService;
import com.cbmie.genMac.financial.service.SerialService;
import com.cbmie.genMac.agent.dao.AgentImportDao;
import com.cbmie.genMac.agent.entity.AgentImport;
import com.cbmie.genMac.utils.DocumentCode;
import com.cbmie.system.entity.User;

/**
 * 代理进口service
 */
@Service
@Transactional
public class AgentImportService extends BaseService<AgentImport, Long> {

	@Autowired
	private AgentImportDao agentImportDao;
	
	@Autowired
	private AffiBaseInfoService affiBaseInfoService;
	
	@Autowired
	private OpenCreditService openCreditService;
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private SerialService serialService;
	
	@Autowired
	private DocumentCode documentCode;

	@Override
	public HibernateDao<AgentImport, Long> getEntityDao() {
		return agentImportDao;
	}
	
	/**
	 * 把新开发的客户信息存储到关联单位(新增)
	 */
	public void saveAffiBaseInfo(AgentImport agentImport, User currentUser) {
		if (agentImport.getCustomerType() == 0) {//新开发客户
			AffiBaseInfo abi = new AffiBaseInfo();
			abi.setCreaterNo(currentUser.getLoginName());
			abi.setCreaterName(currentUser.getName());
			abi.setCreaterDept(currentUser.getOrganization().getOrgName());
			abi.setCreateDate(new Date());
			abi.setCustomerName(agentImport.getCustomerName());
			abi.setCustomerCode(affiBaseInfoService.generateCode(agentImport.getCustomerName()));
			abi.setCustomerType("1");//国内客户
			abi.setRegisterDate(agentImport.getRegisterDate());
			abi.setRegisterCapital(agentImport.getRegisterCapital());
			abi.setBusinessScope(agentImport.getGoodsType());
			abi.setStatus(0);
			affiBaseInfoService.save(abi);
			agentImport.setCustomerId(abi.getId());
		} else {//长单老客户
			
		}
	}
	
	/**
	 * 把新开发的客户信息存储到关联单位(修改)
	 */
	public void updateAffiBaseInfo(AgentImport agentImport, User currentUser) {
		if (agentImport.getCustomerType() == 0) {//新开发客户
			AffiBaseInfo abi = null;
			if(agentImport.getCustomerId() != null){
				abi = affiBaseInfoService.get(agentImport.getCustomerId());
				abi.setUpdaterNo(currentUser.getLoginName());
				abi.setUpdaterName(currentUser.getName());
				abi.setCreaterDept(currentUser.getOrganization().getOrgName());
				abi.setUpdateDate(new Date());
			} else {
				abi = new AffiBaseInfo();
				abi.setCreaterNo(currentUser.getLoginName());
				abi.setCreaterName(currentUser.getName());
				abi.setCreaterDept(currentUser.getOrganization().getOrgName());
				abi.setCreateDate(new Date());
			}
			abi.setCustomerName(agentImport.getCustomerName());
			abi.setCustomerCode(affiBaseInfoService.generateCode(agentImport.getCustomerName()));
			abi.setCustomerType("1");//国内客户
			abi.setRegisterDate(agentImport.getRegisterDate());
			abi.setRegisterCapital(agentImport.getRegisterCapital());
			abi.setBusinessScope(agentImport.getGoodsType());
			abi.setStatus(0);
			affiBaseInfoService.save(abi);
			agentImport.setCustomerId(abi.getId());
		} else {//长单老客户
			
		}
	}
	
	/**
	 * 目前敞口:开证金额-客户来款+对外支付
	 */
	public Double currentOpen(Long customerId) {
		Double openCreditSum = openCreditService.sumCustomerOpenCredit(customerId);
		Double payExpenseSum = expenseService.sumCustomerPayExpense(customerId);
		Double serialSum = serialService.sumCustomerSerial(customerId, null);
		return openCreditSum - serialSum + payExpenseSum;
	}
	
	/**
	 * 已超期金额:开证金额-货款
	 */
	public Double hasBeyond(Long customerId) {
		Double openCreditSum = openCreditService.sumCustomerOpenCredit(customerId);
		Double serialSum = serialService.sumCustomerSerial(customerId, "货款");
		return openCreditSum - serialSum;
		
	}
	
	/**
	 * 验证编码唯一性
	 */
	public AgentImport findByNo(Long id, String contractNo, String foreignContractNo){
		return agentImportDao.findByNo(id, contractNo, foreignContractNo);
	}
	
	/**
	 * 构造编号
	 * @param customerType 新/老客户
	 * @param customer 新-客户名称，老-客户编码
	 * @param documentType 文档类型-合同
	 * @return
	 */
	public String generateCode(String customerType, String customer, String documentType) {
		try {
			//年份+制作人+文档类型+文档签订日
			String str = documentCode.combination(documentType);
			//客户识别号
			if (customerType.equals("N")) {//新客户
				str += documentCode.customerNum(customer);
			} else if (customerType.equals("L")) {//老客户
				str += customer;
			}
			//顺序自编号
			str += documentCode.getOrderNum(AgentImport.class, "contractNo", str);
			return str;
		} catch (Exception e) {
			return "生成编码失败！";
		}
	}
	
	/**
	 * 满足可申请开证的
	 * @param type LC/TT
	 * @return
	 */
	public List<AgentImport> findOpenCredit(String type) {
		List<AgentImport> rtnList = new ArrayList<AgentImport>();
		List<AgentImport> list = agentImportDao.findBy("state", "生效");
		for (AgentImport agentImport : list) {
			//Double sum = serialService.sum(agentImport.getContractNo(), "保证金");
			//if (sum >= agentImport.getMargin()) {
				if (type.equals("LC")) {
					if (StringUtils.containsAny(agentImport.getPaymentMethod(), new char[]{'即','远'})) {
						rtnList.add(agentImport);
					}
				} else if (type.equals("TT")) {
					if (StringUtils.contains(agentImport.getPaymentMethod(), "TT")) {
						rtnList.add(agentImport);
					}
				}
			//}
		}
		return rtnList;
	}
	
	public List<AgentImport> findMarginInform() {
		return agentImportDao.findMarginInform();
	}
	
	/**
	 * 找出满足到单登记的进口合同
	 */
	public List<AgentImport> findInvoiceReg() {
		List<AgentImport> rtnList = new ArrayList<AgentImport>();
		List<AgentImport> list = agentImportDao.findBy("state", "生效");
		for (AgentImport agentImport : list) {
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			PropertyFilter one = new PropertyFilter("EQS_contractNo", agentImport.getContractNo());
			PropertyFilter two = new PropertyFilter("EQS_foreignContractNo", agentImport.getForeignContractNo());
			PropertyFilter three = new PropertyFilter("NES_state", "作废");
			filters.add(one);
			filters.add(two);
			filters.add(three);
			List<OpenCredit> openCreditList = openCreditService.search(filters);
			boolean flag = false;
			for (OpenCredit openCredit : openCreditList) {
				if (!openCredit.getState().equals("生效")) {
					flag = false;
					break;
				} else if (openCredit.getLcType() != 3 && StringUtils.isEmpty(openCredit.getLcNo())) {
					flag = false;
					break;
				} else {
					flag = true;
				}
			}
			if (flag) {
				rtnList.add(agentImport);
			}
		}
		return rtnList;
	}
	
}
