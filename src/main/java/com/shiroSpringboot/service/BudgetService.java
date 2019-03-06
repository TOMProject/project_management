package com.shiroSpringboot.service;

import org.apache.poi.ss.usermodel.Row;

import com.shiroSpringboot.entity.Budget;

public interface BudgetService extends BaseService<Budget, Integer>{
	/**
	 * 导入budget文件
	 * @param row
	 */
	void uploadBudgetExcelFile(Row row);
}
