package com.shiroSpringboot.common.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import com.shiroSpringboot.vo.AjaxResponse;


public class DownloadFileUtils {

	/**
	 * 下载文件
	 * @param fileName 下载的文件名称
	 * @param file 文件
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public static AjaxResponse<Object> downloadAsExcelFile(String fileName, File file,HttpServletResponse response) throws IOException {
		
		fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
	    // 清空response
        response.reset();
        // 设置response
		response.addHeader("content-disposition", "attachment; filename=" + fileName);
		response.setContentLength((int) file.length());
		
		try (OutputStream os = new BufferedOutputStream(response.getOutputStream())) {
			os.write(FileUtils.readFileToByteArray(file));
			os.flush();
			
		} finally {
			//FileUtils.deleteQuietly(file);
		}
		return new AjaxResponse<Object>();
	}
	
	
}
