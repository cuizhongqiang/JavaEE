package com.cbmie.genMac.financial.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.genMac.financial.entity.ImportBilling;
import com.cbmie.genMac.financial.service.ImportBillingService;

public class ImportBillingEndListener implements ExecutionListener {
	
	private static final long serialVersionUID = -3749719423876966373L;

	@Autowired
	ImportBillingService importBillingService;
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String deleteReason = null;
		if (execution instanceof ExecutionEntity) {
			ExecutionEntity executionEntity = (ExecutionEntity) execution;
			deleteReason = executionEntity.getDeleteReason();
		}
		ImportBilling importBilling = importBillingService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			importBilling.setState("作废");
		} else if (null != deleteReason) {
			importBilling.setState("草稿");
			importBilling.setProcessInstanceId(null);
		} else {
			importBilling.setState("生效");
		}
		importBillingService.save(importBilling);
	}

}
