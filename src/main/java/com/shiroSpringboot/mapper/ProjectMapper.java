/*
 * @ClassName ProjectMapper
 * @Description 
 * @version 1.0
 * @Date 2019-01-18 14:05:15
 */
package com.shiroSpringboot.mapper;

import com.shiroSpringboot.entity.Project;

public interface ProjectMapper extends BaseMapper<Project, Integer>{
	/**
	 * 通过项目编号查找项目的id
	 * @param projectCode
	 * @return Project对象
	 */
	Project selectProjectByProjectCode(String projectCode);
}