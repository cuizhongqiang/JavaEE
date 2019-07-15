package com.cbmie.genMac.selfRun.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.genMac.selfRun.entity.SelfPurchase;
import com.cbmie.genMac.selfRun.service.SelfPurchaseService;

public class SelfPurchaseEndListener implements ExecutionListener {
	
	private static final long serialVersionUID = -3749719423876966373L;

	@Autowired
	SelfPurchaseService selfPurchaseService;
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String deleteReason = null;
		if (execution instanceof ExecutionEntity) {
			ExecutionEntity executionEntity = (ExecutionEntity) execution;
			deleteReason = executionEntity.getDeleteReason();
		}
		SelfPurchase selfPurchase = selfPurchaseService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			selfPurchase.setState("作废");
		} else if (null != deleteReason) {
			selfPurchase.setState("草稿");
			selfPurchase.setProcessInstanceId(null);
		} else {
			selfPurchase.setState("生效");
		}
		selfPurchaseService.save(selfPurchase);
	}

}
