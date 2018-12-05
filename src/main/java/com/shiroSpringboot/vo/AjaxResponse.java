package com.shiroSpringboot.vo;

import java.util.ArrayList;
import java.util.List;

public class AjaxResponse<T> {
	
	private String code;
	
	private String messge;
	
	private Integer pageCount;
	
	private List<T> data = new ArrayList<>() ;

	public AjaxResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AjaxResponse(List<T> data) {
		setData(data);
	}
	
	
	
	public AjaxResponse(String code,String messge) {
		this.code=code;
		this.messge=messge;


	}

	public AjaxResponse(String code,String messge,List<T> data) {
		this.code=code;
		this.messge=messge;
		this.data=data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessge() {
		return messge;
	}

	public void setMessge(String messge) {
		this.messge = messge;
	}



	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	
	
	
	
	

	
	
}
