package com.cbmie.genMac.financial.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.genMac.financial.entity.Acceptance;
import com.cbmie.genMac.financial.service.AcceptanceService;

public class AcceptanceEndListener implements ExecutionListener {
	
	private static final long serialVersionUID = -3749719423876966373L;

	@Autowired
	AcceptanceService acceptanceService;
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String deleteReason = null;
		if (execution instanceof ExecutionEntity) {
			ExecutionEntity executionEntity = (ExecutionEntity) execution;
			deleteReason = executionEntity.getDeleteReason();
		}
		Acceptance acceptance = acceptanceService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			acceptance.setState("作废");
		} else if (null != deleteReason) {
			acceptance.setState("草稿");
			acceptance.setProcessInstanceId(null);
		} else {
			acceptance.setState("生效");
		}
		acceptanceService.save(acceptance);
	}

}
