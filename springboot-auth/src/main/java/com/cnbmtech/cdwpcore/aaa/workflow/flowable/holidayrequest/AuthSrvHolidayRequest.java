/**
 * @Description: TODO
 * @author zhengangwu
 */
package com.cnbmtech.cdwpcore.aaa.workflow.flowable.holidayrequest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.cnbmtech.cdwpcore.aaa.module.account.AuthUtils;
import com.cnbmtech.cdwpcore.aaa.module.account.dao.AuthUserRepository;
import com.cnbmtech.cdwpcore.aaa.module.account.model.AuthUser;
import com.cnbmtech.cdwpcore.aaa.msg.WebMessage;
import com.cnbmtech.cdwpcore.aaa.utils.BeanConverter;
import com.cnbmtech.cdwpcore.aaa.workflow.flowable.advance.FormApprove;
import com.cnbmtech.cdwpcore.aaa.workflow.flowable.common.WorkflowUtils;

@RestController
@RequestMapping(value = "/workflow/execute/HolidayRequest", method = RequestMethod.GET)
public class AuthSrvHolidayRequest {
	@Autowired
	AuthUserRepository repo;
	final String processDefinitionKey = HolidayRequest.processDefinitionKey;

	@RequestMapping(value = { "/" }, method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public String hello() {
		return definitionId();
	}

	@RequestMapping(value = { "/definitionIds" }, method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public String definitionId() {
		return BeanConverter.json(WorkflowUtils.processDefinitionId(processDefinitionKey));
	}

	@RequestMapping(value = { "/submitForm" }, method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public String submitForm(@Valid final FormHolidayRequest form) {
		final String username = AuthUtils.username();
		if(StringUtils.isEmpty(username)){
			return BeanConverter.json(new WebMessage());
		}
		form.employee = username;
		return BeanConverter.json(HolidayRequest.submitForm(form));
	}

	@RequestMapping(value = { "/listTasks" }, method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public String listTasks() {
		final String username = AuthUtils.username();
		System.out.println("username=" + username);
		if(StringUtils.isEmpty(username)){
			return BeanConverter.json(new WebMessage());
		}
		final AuthUser  user = repo.findByUsername(username);
		System.out.println("user=" + BeanConverter.json(user));
		if(user == null){
			return BeanConverter.json(new WebMessage());
		}
		final String group = JSON.parseObject(user.getWorkflowrole()).getString("group");
		if(StringUtils.isEmpty(group)){
			return BeanConverter.json(new WebMessage());
		}
		return BeanConverter.json(HolidayRequest.listTasks(group));
	}

	@RequestMapping(value = { "/listHistory" }, method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public String listHistory() {
		final String username = AuthUtils.username();
		System.out.println("username=" + username);
		if(StringUtils.isEmpty(username)){
			return BeanConverter.json(new WebMessage());
		}
		return BeanConverter.json(HolidayRequest.listHistory(username));
	}

	@RequestMapping(value = { "/approveForm" }, method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public String approveForm(@Valid final FormApprove form) {
		final String username = AuthUtils.username();
		if(StringUtils.isEmpty(username)){
			return BeanConverter.json(new WebMessage());
		}
		return BeanConverter.json(HolidayRequest.approveForm(username,form));
	}

}