package com.shiroSpringboot.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.shiroSpringboot.autoConfig.AuthorServer;
import com.shiroSpringboot.common.Contant;
import com.shiroSpringboot.common.utils.DownloadFileUtils;
import com.shiroSpringboot.common.utils.RowParseHelper;
import com.shiroSpringboot.service.BudgetService;
import com.shiroSpringboot.service.ProjectService;
import com.shiroSpringboot.vo.AjaxResponse;





@Controller
@RequestMapping(value="/project")
public class ProjectController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	private ProjectService projectSer;
	@Autowired
	private BudgetService bugetSer;
	@Value("${projectPath}")
	private String projectPath;
	
	
	
	/**
	 * 导入project文件
	 */
	@Override
	public boolean parseFile(File file, @SuppressWarnings("rawtypes") AjaxResponse ajaxResponse)
			throws EncryptedDocumentException, FileNotFoundException, InvalidFormatException, IOException {
		
		return projectfile(file, ajaxResponse);

	}
	public boolean projectfile(File file, @SuppressWarnings("rawtypes") AjaxResponse ajaxResponse)
			throws EncryptedDocumentException, FileNotFoundException, InvalidFormatException, IOException {
		
		try {
			int rowNum = 1;
			InputStream inp = new FileInputStream(file);
			Workbook wb = WorkbookFactory.create(inp);
			//获取excel表格中的sheet数，如果确定了这里的sheels 被numberofShellts替换。
			//int numberOfSheets = wb.getNumberOfSheets();
			int sheets = 2;
			for (int i = 0; i < sheets; i++) {
				Sheet proejectSheet = wb.getSheetAt(i);
				for (Row row : proejectSheet) {
					rowNum = row.getRowNum();
					if (rowNum >= 2) {
						if (!RowParseHelper.hasData(row, 3)) {
							// 解析当前行有没有数据
							break;
						}
						//先解析project
						if (i == 0) {
							projectSer.uploadProjectExcelFile(row);
						}
						if (i == 1) {
							bugetSer.uploadBudgetExcelFile(row);
						}
					}
				}
			}
			
			 File dir=new File(projectPath);
	         if(!dir.exists()) {
	             dir.mkdir();
	         }
			
			File f=new File(projectPath+File.separator+"project"+".xls");//改成配置	
	        OutputStream stream=null;
            stream = new FileOutputStream(f);
            wb.write(stream);//写入文件
            stream.close();
 
            ajaxResponse.setCode(Contant.RESULT_SUCCESS);
			ajaxResponse.setMessge("记录导入处理完成");

		} catch (Exception e) {
			ajaxResponse.setMessge("记录导入失败" );
			logger.error("记录导入出错-->", e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 下载project文件
	 * @return
	 */
	@ResponseBody
	@GetMapping(value = "/downloadProjectFile")
	public AjaxResponse<Object> downloadProjectFile(){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<>(Contant.RESULT_ERROR,"下载文件出错！");

        String  fileName= "项目.xls";
        try {
        	 File file = new File(projectPath+File.separator+"project.xls");
        	 if(!file.exists()) {
				 logger.info("系统找不到下载文件的路径");
				 ajaxResponse.setMessge("系统找不到文件路径！");
				 return ajaxResponse;
			 }
        	 DownloadFileUtils.downloadAsExcelFile(fileName,file,response);
             ajaxResponse.setCode(Contant.RESULT_SUCCESS);
             ajaxResponse.setMessge("下载成功！");
             return ajaxResponse;
		} catch (Exception e) {
			return ajaxResponse;
		}
	}

}
