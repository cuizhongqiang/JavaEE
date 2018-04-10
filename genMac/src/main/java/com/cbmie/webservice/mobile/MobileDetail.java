package com.cbmie.webservice.mobile;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.genMac.credit.entity.ChangeCredit;
import com.cbmie.genMac.credit.entity.OpenCredit;
import com.cbmie.genMac.credit.service.ChangeCreditService;
import com.cbmie.genMac.credit.service.OpenCreditService;
import com.cbmie.genMac.financial.entity.Acceptance;
import com.cbmie.genMac.financial.entity.Expense;
import com.cbmie.genMac.financial.entity.ImportBilling;
import com.cbmie.genMac.financial.entity.PayTaxes;
import com.cbmie.genMac.financial.service.AcceptanceService;
import com.cbmie.genMac.financial.service.ExpenseService;
import com.cbmie.genMac.financial.service.ImportBillingService;
import com.cbmie.genMac.financial.service.PayTaxesService;
import com.cbmie.genMac.agent.entity.AgentImport;
import com.cbmie.genMac.agent.service.AgentImportService;
import com.cbmie.genMac.logistics.entity.Freight;
import com.cbmie.genMac.logistics.entity.LogisticsCheck;
import com.cbmie.genMac.logistics.entity.SendGoods;
import com.cbmie.genMac.logistics.service.FreightService;
import com.cbmie.genMac.logistics.service.LogisticsCheckService;
import com.cbmie.genMac.logistics.service.SendGoodsService;
import com.cbmie.genMac.stock.entity.EnterpriseStockCheck;
import com.cbmie.genMac.stock.service.EnterpriseStockCheckService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 移动端WebService
 */
@WebService
public class MobileDetail {

	@Autowired
	private AgentImportService agentImportService;
	
	@Autowired
	private OpenCreditService openCreditService;
	
	@Autowired
	private ChangeCreditService changeCreditService;
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private SendGoodsService sendGoodsService;
	
	@Autowired
	private FreightService freightService;

	@Autowired
	private PayTaxesService payTaxesService;
	
	@Autowired
	private AcceptanceService acceptanceService;
	
	@Autowired
	private ImportBillingService importBillingService;
	
	@Autowired
	private LogisticsCheckService logisticsCheckService;
	
	@Autowired
	private EnterpriseStockCheckService enterpriseStockCheckService;
	
	/**
	 * 代理进口
	 */
	@WebMethod
	public String agentImport(@WebParam(name = "id") Long id) throws Exception {
		AgentImport agentImport = agentImportService.getNoLoad(id);
		return new ObjectMapper().writeValueAsString(agentImport);
	}

	/**
	 * 开证
	 */
	@WebMethod
	public String openCredit(@WebParam(name = "id") Long id) throws JsonProcessingException {
		OpenCredit openCredit = openCreditService.getNoLoad(id);
		return new ObjectMapper().writeValueAsString(openCredit);
	}
	
	/**
	 * 改证
	 */
	@WebMethod
	public String changeCredit(@WebParam(name = "id") Long id) throws JsonProcessingException {
		ChangeCredit changeCredit = changeCreditService.getNoLoad(id);
		return new ObjectMapper().writeValueAsString(changeCredit);
	}
	
	/**
	 * 费用支付
	 */
	@WebMethod
	public String expense(@WebParam(name = "id") Long id) throws JsonProcessingException {
		Expense expense = expenseService.getNoLoad(id);
		return new ObjectMapper().writeValueAsString(expense);
	}
	
	/**
	 * 放贷
	 */
	@WebMethod
	public String sendGoods(@WebParam(name = "id") Long id) throws JsonProcessingException {
		SendGoods sendGoods = sendGoodsService.getNoLoad(id);
		return new ObjectMapper().writeValueAsString(sendGoods);
	}
	
	/**
	 * 确认贷代
	 */
	@WebMethod
	public String freightInfo(@WebParam(name = "id") Long id) throws JsonProcessingException {
		Freight freight = freightService.getNoLoad(id);
		return new ObjectMapper().writeValueAsString(freight);
	}
	
	/**
	 * 交税
	 */
	@WebMethod
	public String payTaxes(@WebParam(name = "id") Long id) throws JsonProcessingException {
		PayTaxes payTaxes = payTaxesService.getNoLoad(id);
		return new ObjectMapper().writeValueAsString(payTaxes);
	}
	
	/**
	 * 付汇、押汇
	 */
	@WebMethod
	public String acceptance(@WebParam(name = "id") Long id) throws JsonProcessingException {
		Acceptance acceptance = acceptanceService.getNoLoad(id);
		return new ObjectMapper().writeValueAsString(acceptance);
	}
	
	/**
	 * 物流企业考察信息
	 */
	@WebMethod
	public String logisticsCheck(@WebParam(name = "id") Long id) throws JsonProcessingException {
		LogisticsCheck logisticsCheck = logisticsCheckService.getNoLoad(id);
		return new ObjectMapper().writeValueAsString(logisticsCheck);
	}
	
	/**
	 * 进口结算联系单
	 */
	@WebMethod
	public String importBilling(@WebParam(name = "id") Long id) throws JsonProcessingException {
		ImportBilling importBilling = importBillingService.getNoLoad(id);
		return new ObjectMapper().writeValueAsString(importBilling);
	}
	
	/**
	 * 仓储企业考察信息
	 */
	@WebMethod
	public String enterpriseStockCheck(@WebParam(name = "id") Long id) throws JsonProcessingException {
		EnterpriseStockCheck enterpriseStockCheck = enterpriseStockCheckService.getNoLoad(id);
		return new ObjectMapper().writeValueAsString(enterpriseStockCheck);
	}
}
