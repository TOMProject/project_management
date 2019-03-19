package com.shiroSpringboot.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.shiroSpringboot.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActivityTest {

	@Autowired
	ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 查询流程定义
	 */
	@Test
	public void findProcessDefinition() {
		List<ProcessDefinition> list = defaultProcessEngine.getRepositoryService()// 与流程定义和部署相关的service
				.createProcessDefinitionQuery()// 创建一个流程定义查询
				/** 指定查询条件 如where 查询 */
				// .deploymentId(deploymentId)//使用部署对象id查询
				// .processDefinitionId(processDefinitionId)//使用流程定义id查询？
				// .processDefinitionKey(processDefinitionKey)//使用流定义key查询
				// .processDefinitionNameLike(processDefinitionNameLike)//使用流程定义名称模糊查询
				/** 排序 */
				.orderByProcessDefinitionVersion().asc()// 按照版本的升序排列
				// .orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列
				/** 返回的结果集 */
				.list();// 返回一个集合列表，封装流程对象
		// .singleResult()//返回单个的对象
		// .count()//总的条数
		// .listPage(firstResult, maxResults)//返回分页

		if (list != null && list.size() > 0) {
			for (ProcessDefinition p : list) {
				System.out.println("流程定义id：" + p.getId());// 流程定义的key+版本+随机生成数
				System.out.println("流程定义的名称：" + p.getName());// 对应Helloworld.bpmn的name
				System.out.println("流程定义的Key" + p.getKey());// 对应helloworl.bpmn的id属性
				System.out.println("流程定义的版本：" + p.getVersion());// 当流程定义的key相同的情况下版本自动升级默认是1
				System.out.println("资源文件PBMN名称：" + p.getResourceName());
				System.out.println("资源文件png名称：" + p.getDiagramResourceName());
				System.out.println("部署对象iD:" + p.getDeploymentId());

				System.out.println("#################");

			}
		}

	}

	/**
	 * 删除流程定义
	 * 
	 */

	@Test
	public void deleteProcessDefinition() {
		String deploymentId = "1";
		// 通过流程部署id删除，这样只能删除名义启动的的流程
		// defaultProcessEngine.getRepositoryService()
		// .deleteDeployment(deploymentId);
		// 级联删除，不敢流程是否启动都删除
		defaultProcessEngine.getRepositoryService().deleteDeployment(deploymentId,true);
		System.out.println("删除成功");
	}

	/**
	 * 查看流程定义的图片
	 */
	@Test
	public void findPngProcessDefinintion() {
		// 从数据库中查询出来写到磁盘中

		String deploymentId = "7501";
		List<String> name = defaultProcessEngine.getRepositoryService().getDeploymentResourceNames(deploymentId);
		String resourceName = "";
		if (name != null && name.size() > 0) {
			for (String n : name) {
				if (n.indexOf(".png") >= 0) {
					resourceName = n;
				}
			}
		}

		InputStream in = defaultProcessEngine.getRepositoryService().getResourceAsStream(deploymentId, resourceName);

		File file = new File("D:/" + resourceName);
		try {
			FileUtils.copyInputStreamToFile(in, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 查看最新版本的流程
	 */
	@Test
	public void findLastProcess() {
		// 通过查询所以的流程定义，通过version升序排列，然后保存到map中通过map中的 key不重复的特点存取。
		List<ProcessDefinition> list = defaultProcessEngine.getRepositoryService().createProcessDefinitionQuery()
				.orderByProcessDefinitionVersion().asc().list();

		Map<String, ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (ProcessDefinition pd : list) {
				map.put(pd.getKey(), pd);
			}

		}
		// map 转list
		List<ProcessDefinition> li = new ArrayList<ProcessDefinition>(map.values());
		if (CollectionUtils.isNotEmpty(li)) {
			for (ProcessDefinition p : li) {
				System.out.println("流程定义id：" + p.getId());// 流程定义的key+版本+随机生成数
				System.out.println("流程定义的名称：" + p.getName());// 对应Helloworld.bpmn的name
				System.out.println("流程定义的Key" + p.getKey());// 对应helloworl.bpmn的id属性
				System.out.println("流程定义的版本：" + p.getVersion());// 当流程定义的key相同的情况下版本自动升级默认是1
				System.out.println("资源文件PBMN名称：" + p.getResourceName());
				System.out.println("资源文件png名称：" + p.getDiagramResourceName());
				System.out.println("部署对象iD:" + p.getDeploymentId());
				System.out.println("#################");

			}

		}

	}
	/**
	 * 使用流程定义的key删除流程定义.
	 */	
	@Test
	public void deleteProcessByKey() {
		//通过key查询出所以的版本
		String processDefinitionKey ="helloworld";
		List<ProcessDefinition> list = defaultProcessEngine.getRepositoryService()
							.createProcessDefinitionQuery()
							.processDefinitionKey(processDefinitionKey)
							.list();
		
		//遍历删除
		if(CollectionUtils.isNotEmpty(list)) {
			for (ProcessDefinition pd : list) {
				defaultProcessEngine.getRepositoryService()
									.deleteDeployment(pd.getDeploymentId(), true);
			}
			
			
			
			
		}
		
	}
	
	
}
