package com.shiroSpringboot.service;

import org.apache.poi.ss.usermodel.Row;

import com.shiroSpringboot.entity.Project;

public interface ProjectService extends BaseService<Project, Integer>{

	/**
	 * 上传项目文件
	 * @param row
	 */
	void uploadProjectExcelFile(Row row);
}
