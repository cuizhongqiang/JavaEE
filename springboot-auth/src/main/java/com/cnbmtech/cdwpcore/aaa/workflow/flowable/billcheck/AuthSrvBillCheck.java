package com.cnbmtech.cdwpcore.aaa.workflow.flowable.billcheck;

import java.util.Map;

import javax.validation.Valid;

import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cnbmtech.cdwpcore.aaa.msg.WebMessage;
import com.cnbmtech.cdwpcore.aaa.utils.BeanConverter;
import com.cnbmtech.cdwpcore.aaa.workflow.flowable.common.WorkflowConstants;

@RestController
@RequestMapping(value = "/workflow/execute/BillCheck",produces = MediaType.TEXT_PLAIN_VALUE)
public class AuthSrvBillCheck implements WorkflowConstants {
	private final static String processDefinitionKey = "BillCheck";
	// @Autowired
	// RealmRepository rr;

	@RequestMapping(value = { "/startProcess" }, method = RequestMethod.GET)
	public String startProcess(@Valid final FormBillCheckStart form) {
		final WebMessage rtn = new WebMessage();
		final Map<String, Object> map = BeanConverter.map(form);
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey,map);
		//rr.save(processDefinitionKey, form.getStarter());
		rtn.setStatus("ok");
		rtn.setContent(processInstance);
		return BeanConverter.json(rtn);
	}
}