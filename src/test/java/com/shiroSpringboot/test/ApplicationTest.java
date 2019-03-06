package com.shiroSpringboot.test;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.shiroSpringboot.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

	@Autowired
	ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
	/**
	 * 部署流程定义
	 */
	@Test
	public void devlopprocess() {
		RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
		Deployment  deployment = repositoryService.createDeployment()
				.name("helloWorld")//部署的名称
				.addClasspathResource("diagram/helloworld.bpmn")//从classPath资源中加在，异常只能加载一个xml文件
				.addClasspathResource("diagram/helloworld.png")//从classPath资源中加在，异常只能加载一个xml文件
				.deploy();
		
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
		System.out.println(deployment.getTenantId());
		
		
	}

	/**
	 * 流程启动
	 */
	@Test
	public void startProcess() {
		//流程定义的key 	
		String processDefinitionKey ="helloworld";
		//使用流程定义的key启动流程，key对应的是bpmn文件中个的id属性
		ProcessInstance pi = defaultProcessEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey);
		System.out.println("流程启动id:"+pi.getId());
		System.out.println("流程启动名称:"+pi.getName());
	}
	/**
	 * 查找流程定义
	 */
	@Test
	public void fingProcess() {
		//ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
		String assignee ="李四";
	 TaskService taskService = defaultProcessEngine.getTaskService();//与正在完成任务相关的service
	 TaskQuery createTaskQuery = taskService.createTaskQuery();
	 TaskQuery taskAssignee = createTaskQuery.taskAssignee(assignee);

	 List<Task> list = taskAssignee.list();
		
		if(list!= null && list.size()>0) {
			for (Task task : list) {
				
				System.out.println("任务id:"+task.getId());
				System.out.println("任务名称:"+task.getName());
				System.out.println("任务创建时间:"+task.getCreateTime());
				System.out.println("任务办理人:"+task.getAssignee());
				System.out.println("流程实例id:"+task.getProcessInstanceId());
				System.out.println("流程对象id:"+task.getExecutionId());
			}
		}
	}
	
	/**
	 * 完成流程定义
	 */
	@Test
	public void complateProcess() {
		String comId = "2504";
		TaskService taskService = defaultProcessEngine.getTaskService();
		taskService.complete(comId);
		System.out.println("任务完成："+comId);
	}
	
}
