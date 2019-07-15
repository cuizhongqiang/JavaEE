package com.cbmie.genMac.domesticTrade.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.genMac.domesticTrade.entity.DomesticPurchase;
import com.cbmie.genMac.domesticTrade.service.DomesticPurchaseService;

public class DomesticPurchaseServiceEndListener implements ExecutionListener {
	
	private static final long serialVersionUID = -3749719423876966373L;

	@Autowired
	DomesticPurchaseService domesticPurchaseService;
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String deleteReason = null;
		if (execution instanceof ExecutionEntity) {
			ExecutionEntity executionEntity = (ExecutionEntity) execution;
			deleteReason = executionEntity.getDeleteReason();
		}
		DomesticPurchase domesticPurchase = domesticPurchaseService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			domesticPurchase.setState("作废");
		} else if (null != deleteReason) {
			domesticPurchase.setState("草稿");
			domesticPurchase.setProcessInstanceId(null);
		} else {
			domesticPurchase.setState("生效");
		}
		domesticPurchaseService.save(domesticPurchase);
	}

}
