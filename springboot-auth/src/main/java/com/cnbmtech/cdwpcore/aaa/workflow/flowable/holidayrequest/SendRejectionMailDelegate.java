package com.cnbmtech.cdwpcore.aaa.workflow.flowable.holidayrequest;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class SendRejectionMailDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		System.out.println("SendRejectionMail: " + execution.getVariable("employee"));
	}

}
