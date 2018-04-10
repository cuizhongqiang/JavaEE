package com.cbmie.genMac.selfRun.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.genMac.selfRun.entity.SelfSales;
import com.cbmie.genMac.selfRun.service.SelfSalesService;

public class SelfSalesEndListener implements ExecutionListener {
	
	private static final long serialVersionUID = -3749719423876966373L;

	@Autowired
	SelfSalesService selfSalesService;
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String deleteReason = null;
		if (execution instanceof ExecutionEntity) {
			ExecutionEntity executionEntity = (ExecutionEntity) execution;
			deleteReason = executionEntity.getDeleteReason();
		}
		SelfSales selfSales = selfSalesService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			selfSales.setState("作废");
		} else if (null != deleteReason) {
			selfSales.setState("草稿");
			selfSales.setProcessInstanceId(null);
		} else {
			selfSales.setState("生效");
		}
		selfSalesService.save(selfSales);
	}

}
