package com.shiroSpringboot.common.utils;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Sha384Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class ShiroUtil {
	/**
	 * 使用hex编码的md5 加盐加密
	 * @param username
	 * @param password
	 * @return
	 */
	public static String passwordMD5(String username,String password) {
		String hashAlgorithmName = "MD5";
        String credentials = password;//密码
        int hashIterations = 1024;
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);//盐值
        SimpleHash obj = new SimpleHash(hashAlgorithmName, credentials, credentialsSalt, hashIterations); 
        return obj.toHex();
	}
	/**
	 * 使用shiro base64位加密
	 * @param str
	 * @return
	 */
	public static String passwordEncBase64(String str) {
		 String sha384Hex = new Sha384Hash(str).toBase64();
	     return sha384Hex;
		
	}
	
	  /** 
     * base64解密 
     * @param str 
     * @return 
     */  
    public static String passwordDecBase64(String str){  
        return new Sha384Hash("Mug2tZdS/WCdxOOfCHXPcdQyVT5kqBimrBM04UEj9Dma+XaasHvZZckw/OGjWj8J").toString();  
    } 
	
	public static void main(String[] args) {
		System.out.println(passwordMD5("adminee", "123456"));
//		System.out.println(passwordEncBase64("5201314"));
		
	}
	


	
	
}
