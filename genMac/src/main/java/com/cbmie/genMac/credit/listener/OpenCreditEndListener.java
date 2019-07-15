package com.cbmie.genMac.credit.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.genMac.credit.entity.OpenCredit;
import com.cbmie.genMac.credit.service.OpenCreditService;

public class OpenCreditEndListener implements ExecutionListener {
	
	private static final long serialVersionUID = -3749719423876966373L;
	
	@Autowired
	OpenCreditService openCreditService;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String deleteReason = null;
		if (execution instanceof ExecutionEntity) {
			ExecutionEntity executionEntity = (ExecutionEntity) execution;
			deleteReason = executionEntity.getDeleteReason();
		}
		OpenCredit openCredit = openCreditService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			openCredit.setState("作废");
		} else if (null != deleteReason) {
			openCredit.setState("草稿");
			openCredit.setProcessInstanceId(null);
		} else {
			openCredit.setState("生效");
		}
		openCreditService.save(openCredit);
	}

}
