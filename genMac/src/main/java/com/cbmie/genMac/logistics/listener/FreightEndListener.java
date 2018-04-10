package com.cbmie.genMac.logistics.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.genMac.logistics.entity.Freight;
import com.cbmie.genMac.logistics.service.FreightService;

public class FreightEndListener implements ExecutionListener {
	
	@Autowired
	FreightService freightService;

	private static final long serialVersionUID = 4439817514082863143L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String deleteReason = null;
		if (execution instanceof ExecutionEntity) {
			ExecutionEntity executionEntity = (ExecutionEntity) execution;
			deleteReason = executionEntity.getDeleteReason();
		}
		Freight freight = freightService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			freight.setState("作废");
		} else if (null != deleteReason) {
			freight.setState("草稿");
			freight.setProcessInstanceId(null);
		} else {
			freight.setState("生效");
		}
		freightService.save(freight);
	}

}
