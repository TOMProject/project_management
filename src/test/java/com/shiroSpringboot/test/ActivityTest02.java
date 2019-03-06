package com.shiroSpringboot.test;



import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.shiroSpringboot.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActivityTest02 {

	@Autowired
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	/**
	 * 部署流程定义
	 */
	@Test
	public void deploymentProcess() {
		Deployment deploy = processEngine.getRepositoryService()
					.createDeployment()
					.name("部署路程")
					.addClasspathResource("diagram/helloworld.bpmn")
					.addClasspathResource("diagram/helloworld.png")
					.deploy();
		

		System.out.println(deploy.getId());
		System.out.println(deploy.getName());
		System.out.println(deploy.getTenantId());
	}
	
	/**
	 * 启动流程
	 * 
	 */
	@Test
	public void startProcess() {
		//流程定义的key
		String processDefinitionKey = "helloworld";
		ProcessInstance pi = processEngine.getRuntimeService()
					.startProcessInstanceByKey(processDefinitionKey);
					
		System.out.println(pi.getId());//流程实例id
		System.out.println(pi.getProcessDefinitionId());//流程定义id
		
	}
	
	/**
	 * 查询流程实例
	 */
	@Test
	public void findProcess() {
		String assignee ="王五";
		List<Task> list = processEngine.getTaskService()//与正在执行相关的service
					.createTaskQuery()//创建任务查询对象
					.taskAssignee(assignee)//指定个人任务查询，办理人
//					.taskCandidateUser(candidateUser)//组任务的办理人查询？
//					.processDefinitionId(processDefinitionId)使用流程定义id查询
//					.processInstanceId(processInstanceId)//使用路程实例id查询
//					.executionId(executionId)执行任务的id查询
					/**排序*/
					.orderByTaskCreateTime().asc()//创建时间升序排列
					/**返回的结果**/
//					.singleResult()//单个结果
//					.count()//数量
//					.listPage(firstResult, maxResults)
					.list();
		if(list!= null && list.size()>0) {
			for (Task task : list) {
				
				System.out.println("任务id:"+task.getId());
				System.out.println("任务名称:"+task.getName());
				System.out.println("任务创建时间:"+task.getCreateTime());
				System.out.println("任务办理人:"+task.getAssignee());
				System.out.println("流程实例id:"+task.getProcessInstanceId());
				System.out.println("流程对象id:"+task.getExecutionId());
				
				System.out.println("################");
			}
			
		}
					
	}	
	
	/**
	 * 任务完成
	 */
	@Test
	public void complateProcess() {
		
		String complateId = "17502";
		processEngine.getTaskService().complete(complateId);
		
		System.out.println("任务完成++"+complateId);
	}
	
}

