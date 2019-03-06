package com.shiroSpringboot.autoConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({AuthorServer.class})
@EnableConfigurationProperties(AutoEnbleConfigProperties.class)
public class AuthorAutoConfiguration {

	
	@Autowired
	private AutoEnbleConfigProperties autoEnbleConfigProperties;
	
	@Bean
	@ConditionalOnMissingBean({AuthorServer.class})
	//@ConditionalOnMissingBean 作用spring优先使用自定义的bean,如果没有自定义的bean才会执行下面的方法
	public AuthorServer authorServer() {
		AuthorServer author = new AuthorServer();
		author.setAuthor(autoEnbleConfigProperties.getAuthor());
		return author;
	}
}
