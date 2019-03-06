package com.shiroSpringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.shiroSpringboot.interceptor.LoginInterceptor;

@Configuration
public class MvcConfig implements WebMvcConfigurer	{
	/**
	 * 自定义拦截器
	 * @return
	 */
	@Bean
    public HandlerInterceptor getLoginInterceptor(){
        return new LoginInterceptor();
    }
	
	/**
	 * 添加拦截规则
	 */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(getLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/userLogin")//排除拦截的api
                .excludePathPatterns("/login")
                .excludePathPatterns("/logout")
                .excludePathPatterns("/static/**")
        		.excludePathPatterns("/templates/**");
       
    }
    /**
     * 配置静态资源文件
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/static/**","/templates/**")
                .addResourceLocations("classpath:/static/","classpath:/templates/");
    }

	
}
