package com.shiroSpringboot.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean(); 
		factoryBean.setSecurityManager(securityManager());
		//定义shiro拦截器
//		Map<String,String> filterChainDefinitionMap = new HashMap<String,String>();
//		filterChainDefinitionMap.put("/static/**", "anon");//静态资源下的不认证
//		filterChainDefinitionMap.put("/logout", "logout");//退出
//		filterChainDefinitionMap.put("/**","authc");//需要认证的路径
//		factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//		
//		factoryBean.setLoginUrl("/login");
//		factoryBean.setSuccessUrl("/index");
//		factoryBean.setUnauthorizedUrl("/403");//未授权页面
		
		return factoryBean;
	}
	
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		defaultWebSecurityManager.setRealm(shiroRealm());//管理自定义认证器
		return defaultWebSecurityManager;
		
	}
	/**
	 * 注入认证器
	 * @return
	 */
	@Bean
	public ShiroRealm shiroRealm() {
		ShiroRealm shiroRealm = new ShiroRealm();
		shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return shiroRealm;
	}
	/**
	 * 配置shiro的加密方式
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashIterations(1024);//加密次数
		hashedCredentialsMatcher.setHashAlgorithmName("MD5");
		//true 使用hex编码加密，false 使用base64位加密
		hashedCredentialsMatcher.setStoredCredentialsHexEncoded(false);
		return hashedCredentialsMatcher;
		
	}
	
//	 @Bean(name = "credentialsMatcher")
//	    public CredentialsMatcher credentialsMatcher(CacheManager cacheManager) {
//	        RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher(cacheManager);
//	        //加密方式
//	        credentialsMatcher.setHashAlgorithmName(properties.getAlgorithmName());
//	        //加密迭代次数
//	        credentialsMatcher.setHashIterations(properties.getIteration());
//	        //true加密用的hex编码，false用的base64编码
//	        credentialsMatcher.setStoredCredentialsHexEncoded(properties.getHexEncoded());
//	        //重新尝试的次数（自己定义的）
//	        credentialsMatcher.setRetryMax(properties.getRetryMax());
//	        return credentialsMatcher;
//
//	 }
}
