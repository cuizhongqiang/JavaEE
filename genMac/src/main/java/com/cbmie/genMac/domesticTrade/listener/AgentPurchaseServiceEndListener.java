package com.cbmie.genMac.domesticTrade.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.genMac.domesticTrade.entity.AgentPurchase;
import com.cbmie.genMac.domesticTrade.service.AgentPurchaseService;

public class AgentPurchaseServiceEndListener implements ExecutionListener {
	
	private static final long serialVersionUID = -6898451249973561595L;
	
	@Autowired
	AgentPurchaseService agentPurchaseService;
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String deleteReason = null;
		if (execution instanceof ExecutionEntity) {
			ExecutionEntity executionEntity = (ExecutionEntity) execution;
			deleteReason = executionEntity.getDeleteReason();
		}
		AgentPurchase agentPurchase = agentPurchaseService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			agentPurchase.setState("作废");
		} else if (null != deleteReason) {
			agentPurchase.setState("草稿");
			agentPurchase.setProcessInstanceId(null);
		} else {
			agentPurchase.setState("生效");
		}
		agentPurchaseService.save(agentPurchase);
	}

}
