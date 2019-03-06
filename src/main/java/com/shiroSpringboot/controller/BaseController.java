package com.shiroSpringboot.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shiroSpringboot.common.Contant;
import com.shiroSpringboot.vo.AjaxResponse;


@Controller
@CrossOrigin
public class BaseController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected HttpServletResponse response;
	protected HttpSession session;
	protected HttpServletRequest request;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
		
	}

    @ResponseBody
    @RequestMapping(value = "/fileImport", method = RequestMethod.POST)
    public AjaxResponse<Object> fileImportHandler(@RequestParam MultipartFile file) throws IOException, EncryptedDocumentException, InvalidFormatException {
        AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>(Contant.RESULT_ERROR, "文件导入失败");
        if (!file.isEmpty()) {
            InputStream in = null;
            OutputStream out = null;
            File dir = new File("tmpFiles");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File serverFile = new File(dir.getAbsolutePath() + File.separator + System.currentTimeMillis());
            in = file.getInputStream();
            out = new FileOutputStream(serverFile);
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = in.read(b)) > 0) {
                out.write(b, 0, len);
            }
            out.close();
            in.close();
            logger.info("Server File Location=" + serverFile.getAbsolutePath());
            parseFile(serverFile, ajaxResponse);
            return ajaxResponse;
        } else {
            ajaxResponse.setMessge("文件为空！");
            return ajaxResponse;
        }
    }

    public boolean parseFile(File file, AjaxResponse ajaxResponse) throws EncryptedDocumentException, FileNotFoundException, InvalidFormatException, IOException {
        ajaxResponse.setCode(Contant.RESULT_SUCCESS);
        ajaxResponse.setMessge("上传成功");
        return true;
    }


}
