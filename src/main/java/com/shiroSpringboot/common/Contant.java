package com.shiroSpringboot.common;


/**
 * 恒定不变的参数类
 * @author
 *
 */
public class Contant {

	public static final String RESULT_SUCCESS = "0000";
	
	public static final String RESULT_ERROR = "0001";
	
	public final static String ERROR_REQUEST = "ajaxResponse";
	
	public final static String TEMPLETE_PATH = Contant.class.getClassLoader().getResource("").getPath() + "templete/";


}
