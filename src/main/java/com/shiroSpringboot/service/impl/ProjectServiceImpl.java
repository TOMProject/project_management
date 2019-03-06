package com.shiroSpringboot.service.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.shiroSpringboot.common.utils.StringUtils;
import com.shiroSpringboot.entity.Project;
import com.shiroSpringboot.mapper.ProjectMapper;
import com.shiroSpringboot.service.ProjectService;

@Service
public class ProjectServiceImpl extends BaseServiceImpl<Project, Integer> implements ProjectService {

	private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

	@Autowired
	private ProjectMapper projectMapper;

	@Override
	public void uploadProjectExcelFile(Row row){
		// 创建一列
		Cell newCell = row.createCell(3);

		Project project = new Project();
		try {
			// 项目名称
			if (!StringUtils.isNull(row.getCell(0))) {
				project.setProjectName(row.getCell(0).toString().trim());
			}
			// 项目编号
			if (!StringUtils.isNull(row.getCell(1))) {
				String parentCode = String.valueOf((int)row.getCell(1).getNumericCellValue()).trim();
				project.setProjectCode(parentCode);
			}
			// 父级项目id，通过表格里面的父级编号查询获取,//存在父级编号说明是子节点数据，需要判断parent_id是否存在
			if (!StringUtils.isNull(row.getCell(2))) {
				String parentCode = String.valueOf((int)row.getCell(2).getNumericCellValue()).trim();
				Project pro = projectMapper.selectProjectByProjectCode(parentCode);
				if (pro != null) {
					project.setParentId(pro.getId());
					//pro不等于null也说明当前行是pro对象的子节点，将pro对象中的isLeaf修改该为1（有子节点）
					if((int)pro.getIsLeaf() == 0) {
						Project up_project = new Project();
						up_project.setId(pro.getId());
						up_project.setIsLeaf((byte)1);
						projectMapper.updateByPrimaryKeySelective(up_project);
					}
				} else {// 抛出异常
					throw new RuntimeException("本行的父级编号不存在！不能写入数据。");
				}
			}else {//如没有填写就是父级项目
				project.setIsLeaf((byte)1);
			}
		
			projectMapper.insertSelective(project);
			newCell.setCellValue("本行数据写入成功");
		}catch(Exception e) {
			// 如果出现异常就会在该行的后面添加异常记录
			final Throwable cause = e.getCause();
			if( cause instanceof MySQLIntegrityConstraintViolationException ) {
				newCell.setCellValue("异常信息:项目编号不能重复");
			}else {
			newCell.setCellValue("异常信息" + e.getMessage());
			}
			e.printStackTrace();
			logger.error("导入项目excel数据异常--》项目名称[{}],项目编号[{}],异常信息：{}",row.getCell(0).toString(),row.getCell(1).toString(),e.getMessage());
			
		}

	}

}
