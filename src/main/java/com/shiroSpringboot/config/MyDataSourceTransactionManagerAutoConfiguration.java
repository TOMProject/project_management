package com.shiroSpringboot.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;


@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class MyDataSourceTransactionManagerAutoConfiguration extends DataSourceTransactionManagerAutoConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(MyDataSourceTransactionManagerAutoConfiguration.class);
	
	/**
	 * 自定义事务
	 * MyBatis自动参与到spring事务管理中
	 * 
	 * @return
	 */
	@Bean(name = "transactionManager")
	public DataSourceTransactionManager transactionManagers(DataSource dataSource) {
		logger.info("-------------------- transactionManager init ---------------------");
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "tInterceptor")
	public TransactionInterceptor transactionInterceptor(PlatformTransactionManager platformTransactionManager) {
		logger.info("-------------------- transactionInterceptor init ---------------------");
		TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
		transactionInterceptor.setTransactionManager(platformTransactionManager);
		Properties properties = new Properties();
		properties.setProperty("find*", "PROPAGATION_REQUIRED,-Throwable,readOnly");
		properties.setProperty("get*", "PROPAGATION_REQUIRED,-Throwable,readOnly");
		properties.setProperty("select*", "PROPAGATION_REQUIRED,-Throwable,readOnly");
		properties.setProperty("*", "PROPAGATION_REQUIRED,-Throwable");
		transactionInterceptor.setTransactionAttributes(properties);
		return transactionInterceptor;
	}

	/** 切面拦截规则 参数会自动从容器中注入 */
	@Bean
	public AspectJExpressionPointcutAdvisor pointcutAdvisor(TransactionInterceptor tInterceptor) {
		logger.info("-------------------- AspectJExpressionPointcutAdvisor init ---------------------");
		AspectJExpressionPointcutAdvisor pointcutAdvisor = new AspectJExpressionPointcutAdvisor();
		pointcutAdvisor.setAdvice(tInterceptor);
		pointcutAdvisor.setExpression("execution(* com.shiroSpringboot.*.service.*.*(..))");
		return pointcutAdvisor;
	}
}
