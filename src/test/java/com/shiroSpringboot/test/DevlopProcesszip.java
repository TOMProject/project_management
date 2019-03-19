package com.shiroSpringboot.test;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.shiroSpringboot.Application;

/**
 * 通过zip 文件的方式部署流程
 * @author HTF
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DevlopProcesszip {
	
	@Autowired
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/*
	 * 使用zip部署流程
	 */
	@Test
	public void devlopProcessByZip() {
		//获取zip文件，
		InputStream in  = this.getClass().getClassLoader().getSystemResourceAsStream("diagram/test.zip");
		ZipInputStream zip = new ZipInputStream(in);
		
		
		RepositoryService repositoryService = processEngine.getRepositoryService();
		Deployment deploy = repositoryService.createDeployment().addZipInputStream(zip).name("test流程zip").deploy();
		System.out.println("部署流程id-->"+deploy.getId());
		System.out.println("部署流程name-->"+deploy.getName());
	}
	
	/**
	 * 查看流程定义
	 */
	@Test
	public void selectProcessDefinition() {
			 List<ProcessDefinition> list = processEngine.getRepositoryService().createProcessDefinitionQuery().list();
			 if(CollectionUtils.isNotEmpty(list)) {
				 for(ProcessDefinition pd : list) {
					 System.out.println("流程定义id-》》"+pd.getId());
					 System.out.println("流程定义name->>"+pd.getName());
					 System.out.println("流程定义key->>"+pd.getKey());
					 System.out.println("流程定义版本-->"+pd.getVersion());
					 System.out.println("部署对象id-->"+pd.getDeploymentId());
					 System.out.println("----------------------------");
				 }
			 }
	}
	
	/**
	 * 启动流程
	 */
	@Test
	public void startProcess() {		
		ProcessInstance startProcess = processEngine.getRuntimeService().startProcessInstanceByKey("testProcess");
		System.out.println("流程启动id-->"+startProcess.getId());
		System.out.println("流程启动名称-->"+startProcess.getName());
		
	}
	
	
	
	/**
	 * 查询流程运行任务到哪里了
	 */
	@Test
	public void selectProcessTask() {
		String assignee = "王五";
		TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery().taskAssignee(assignee);
		List<Task> list = taskQuery.list();
		if(CollectionUtils.isNotEmpty(list)) {
			for (Task task : list) {
				System.out.println("任务id-->"+task.getId());
				System.out.println("任务名称--》"+task.getName());
				System.out.println("任务创建时间:"+task.getCreateTime());
				System.out.println("任务经办人"+task.getAssignee());
				System.out.println("流程实例id->"+task.getProcessInstanceId());
				System.out.println("流程对象id->"+task.getExecutionId());
			}
		}else {
			System.out.println("流程没有启动");
		}
		
	}
	
	/**
	 * 完成某个任务流程
	 */
	@Test
	public void procellCompate() {
		String compateId = "30002";
		processEngine.getTaskService().complete(compateId);
		System.out.println("完成任务id"+compateId);
	}

	/**
	 * 删除流程定义
	 */
	@Test
	public void delteProcess() {
		String deploymentId = "20006";
		processEngine.getRepositoryService().deleteDeployment(deploymentId,true);
		System.out.println("删除了流程部署id是--》"+deploymentId);
	}
	
	
	
	
}
