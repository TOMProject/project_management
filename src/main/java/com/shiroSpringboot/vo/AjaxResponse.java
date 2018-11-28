package com.shiroSpringboot.vo;

public class AjaxResponse<T> {
	
	private String code;
	
	private String messge;
	
	private T data;

	public AjaxResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AjaxResponse(T data) {
		setData(data);
	}
	
	
	
	public AjaxResponse(String code,String messge) {
		this.code=code;
		this.messge=messge;


	}

	public AjaxResponse(String code,String messge,T data) {
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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
	
	
	

	
	
}
