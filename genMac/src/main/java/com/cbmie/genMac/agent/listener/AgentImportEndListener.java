package com.cbmie.genMac.agent.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.genMac.agent.entity.AgentImport;
import com.cbmie.genMac.agent.service.AgentImportService;

public class AgentImportEndListener implements ExecutionListener {
	
	private static final long serialVersionUID = -3749719423876966373L;

	@Autowired
	AgentImportService agentImportService;
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String deleteReason = null;
		if (execution instanceof ExecutionEntity) {
			ExecutionEntity executionEntity = (ExecutionEntity) execution;
			deleteReason = executionEntity.getDeleteReason();
		}
		AgentImport agentImport = agentImportService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			agentImport.setState("作废");
		} else if (null != deleteReason) {
			agentImport.setState("草稿");
			agentImport.setProcessInstanceId(null);
		} else {
			agentImport.setState("生效");
		}
		agentImportService.save(agentImport);
	}

}
