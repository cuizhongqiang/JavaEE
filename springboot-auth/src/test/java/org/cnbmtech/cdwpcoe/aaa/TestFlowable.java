package org.cnbmtech.cdwpcoe.aaa;

import static org.junit.Assert.*;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.test.Deployment;
import org.flowable.engine.test.FlowableRule;
import org.flowable.task.api.Task;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:flowable.cfg.xml")
public class TestFlowable {
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	@Rule
	public FlowableRule flowableSpringRule;

	@Test
	@Deployment
	public void simpleProcessTest() {
		runtimeService.startProcessInstanceByKey("oneTaskProcess");
		Task task = taskService.createTaskQuery().singleResult();
		assertEquals("My Task", task.getName());

		taskService.complete(task.getId());
		assertEquals(0, runtimeService.createProcessInstanceQuery().count());

	}

}
