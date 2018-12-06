package com.shiroSpringboot.config.shiro;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class ShiroConfig {

	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean(); 
		factoryBean.setSecurityManager(securityManager());
		//定义shiro拦截器
		Map<String,String> filterChainDefinitionMap = new HashMap<String,String>();
		filterChainDefinitionMap.put("/static/**", "anon");//静态资源下的不认证
		filterChainDefinitionMap.put("/templates/**", "anon");//静态资源下的不认证
		filterChainDefinitionMap.put("/userLogin", "anon");
		//filterChainDefinitionMap.put("/logout", "logout");//退出
		filterChainDefinitionMap.put("/**","authc");//需要认证的路径放在最下面
		factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		factoryBean.setLoginUrl("/login");//登录页面
		factoryBean.setSuccessUrl("/index");//登录成功后的主页面
		factoryBean.setUnauthorizedUrl("/403");//未授权页面
		
		return factoryBean;
	}
	
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		defaultWebSecurityManager.setRealm(shiroRealm());//管理自定义认证器
		defaultWebSecurityManager.setSessionManager(sessionManager());//session管理器
		defaultWebSecurityManager.setCacheManager(ehCacheManager());//cache管理器
		return defaultWebSecurityManager;
		
	}
	/**
	 * shiro 用户数注入
	 * @return
	 */
	@Bean
	public ShiroRealm shiroRealm() {
		ShiroRealm shiroRealm = new ShiroRealm();
		shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		//开启缓存
		shiroRealm.setCachingEnabled(true);
		//开启认证缓存
		shiroRealm.setAuthenticationCachingEnabled(true);
		shiroRealm.setAuthenticationCacheName("authenticationCache");
		//开启授权缓存
		shiroRealm.setAuthorizationCachingEnabled(true);
		shiroRealm.setAuthorizationCacheName("authorizationCache");
		//修改改了权限要调用清除缓存的方法
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
		hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
		return hashedCredentialsMatcher;
	}
	
    /**
     * DefaultAdvisorAutoProxyCreator 和 AuthorizationAttributeSourceAdvisor 用于开启 shiro 注解的使用
     * 如 @RequiresAuthentication， @RequiresUser， @RequiresPermissions 等
     * @return DefaultAdvisorAutoProxyCreator
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

	/**
	 * 注册session管理器
	 * @return
	 */
	@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager defaultSessionManager = new DefaultWebSessionManager();
		//删除自动在路径上添加sessionId
		defaultSessionManager.setSessionIdUrlRewritingEnabled(false);
		//毫秒为单位
		defaultSessionManager.setGlobalSessionTimeout(30*60*1000);
		defaultSessionManager.setSessionDAO(redisSessionDao());
		//是否开启会话验证器，默认是开启的
		defaultSessionManager.setSessionValidationSchedulerEnabled(true);
		return defaultSessionManager;
		
	}
	
	@Bean
	public RedisSessionDao redisSessionDao() {
		RedisSessionDao redisSessionDao =new RedisSessionDao();
		return redisSessionDao;
	}
	
	@Bean
	public EhCacheManager ehCacheManager() {
		EhCacheManager cacheManager = new EhCacheManager();
		cacheManager.setCacheManagerConfigFile("classpath:config/shiro-ehcache.xml");
		return cacheManager;
	}
	/**
	 * 记住我管理
	 * @return
	 */
	@Bean
	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		 //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位
		cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
		return cookieRememberMeManager;
	}
	
	@Bean
	public SimpleCookie rememberMeCookie() {
		// 这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		// <!-- 记住我cookie生效时间3天 ,单位秒;-->
		simpleCookie.setMaxAge(3600*24*3);
		return simpleCookie;
	}

    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        // shiro 生命周期处理器
        return new LifecycleBeanPostProcessor();
    }
}
