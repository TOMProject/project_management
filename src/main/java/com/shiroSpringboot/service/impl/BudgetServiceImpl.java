package com.shiroSpringboot.service.impl;

import java.math.BigDecimal;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.shiroSpringboot.common.utils.RowParseHelper;
import com.shiroSpringboot.common.utils.StringUtils;
import com.shiroSpringboot.entity.Budget;
import com.shiroSpringboot.entity.Project;
import com.shiroSpringboot.mapper.ProjectMapper;
import com.shiroSpringboot.service.BudgetService;

@Service
public class BudgetServiceImpl extends BaseServiceImpl<Budget, Integer> implements BudgetService {

	private static final Logger logger = LoggerFactory.getLogger(BudgetServiceImpl.class);
	@Autowired
	private ProjectMapper projectMapper;
	@Autowired
	private BudgetService budgetSer;

	@Override
	public void uploadBudgetExcelFile(Row row) {

		// 创建一列
		Cell newCell = row.createCell(5);
		try {
			String projectCode = null;
			String projectName = null;
			// 项目编号
			if (!StringUtils.isNull(row.getCell(0))) {
				projectCode = String.valueOf((int)row.getCell(0).getNumericCellValue()).trim();
			}
			// 项目名称
			if (!StringUtils.isNull(row.getCell(1))) {
				projectName = row.getCell(1).getStringCellValue().trim();
			}
			//创建budget对象
			Budget budget = new Budget();
			if (projectCode != null && projectName != null) {
				Project project = projectMapper.selectProjectByProjectCode(projectCode);
				if (project != null) {
					// 判断项目编号与项目名称是否匹配
					if (!project.getProjectName().equals(projectName)) {
						throw new RuntimeException("项目编号和项目名称不匹配！");
					}
					// 判断项目是不是末級
					if ((int) project.getIsLeaf() != 0) {
						throw new RuntimeException("该项目不是末级节点！");
					}
					budget.setProjectId(project.getId());
				}else {
					throw new RuntimeException("项目编号不存在！");
				}
			}
			
			// budget编号
			if (!StringUtils.isNull(row.getCell(2))) {
				String budgetCode = String.valueOf((int)row.getCell(2).getNumericCellValue()).trim();
				budget.setBudgetCode(budgetCode);
			}
			// budget名称
			if (!StringUtils.isNull(row.getCell(3))) {
				String budgetName = row.getCell(3).toString().trim();
				budget.setBudgetName(budgetName);
			}
			// 预算总和
			if (!StringUtils.isNull(row.getCell(4))) {
				//String budgetAmount = RowParseHelper.getCell(row, 4);
				String budgetAmount = String.valueOf(row.getCell(4).getNumericCellValue()).trim();
				budget.setBudgetAmount(new BigDecimal(budgetAmount));
			}

			budgetSer.insertSelective(budget);
			newCell.setCellValue("本行数据写入成功");
		} catch (Exception e) {
			// 如果出现异常就会在该行的后面添加异常记录
			final Throwable cause = e.getCause();
			if( cause instanceof MySQLIntegrityConstraintViolationException ) {
				newCell.setCellValue("异常信息:预算编号不能重复");
			}else {
				newCell.setCellValue("异常信息：" + e.getMessage());
			}
			e.printStackTrace();
			logger.error("导入预算excel数据异常--》项目编号[{}],项目名称[{}],异常信息：{}",row.getCell(0).toString(),row.getCell(1).toString(),e.getMessage());
		}

	}

}
