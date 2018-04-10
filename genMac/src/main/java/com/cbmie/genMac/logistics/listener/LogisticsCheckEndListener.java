package com.cbmie.genMac.logistics.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.genMac.logistics.entity.LogisticsCheck;
import com.cbmie.genMac.logistics.service.LogisticsCheckService;

public class LogisticsCheckEndListener implements ExecutionListener {
	
	@Autowired
	LogisticsCheckService  logisticsCheckService;

	private static final long serialVersionUID = 4439817514082863143L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String deleteReason = null;
		if (execution instanceof ExecutionEntity) {
			ExecutionEntity executionEntity = (ExecutionEntity) execution;
			deleteReason = executionEntity.getDeleteReason();
		}
		LogisticsCheck logisticsCheck =  logisticsCheckService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			logisticsCheck.setState("作废");
		} else if (null != deleteReason) {
			logisticsCheck.setState("草稿");
			logisticsCheck.setProcessInstanceId(null);
		} else {
			logisticsCheck.setState("生效");
		}
		logisticsCheckService.save(logisticsCheck);
	}

}
