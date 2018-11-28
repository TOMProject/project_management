package com.shiroSpringboot;

import java.time.LocalDate;
import java.time.LocalTime;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * springboot 启动类
 * @author 
 *
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.shiroSpringboot.mapper")
public class Application{
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		log.info("shiroSpringboot-》run success at {} {}",LocalDate.now(),LocalTime.now());
	}

}
