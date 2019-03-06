package com.shiroSpringboot.autoConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="custom")
public class AutoEnbleConfigProperties {

	public static final String DEFAULT_AUTHOR = "张三";
	
	public String author = DEFAULT_AUTHOR;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	
}
