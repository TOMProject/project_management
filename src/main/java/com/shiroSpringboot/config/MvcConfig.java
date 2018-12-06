package com.shiroSpringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
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
    /**
     * 启动项目页面跳转
     */
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//    	//addViewContorller是页面访问的路径，setviewName 是contorller对应的方法路径
//        registry.addViewController("/login").setViewName("login");
//        registry.addViewController("/").setViewName("login");
//    
//	}
    /**
     * 设置跨域
     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")//设置允许跨域的路径
//                .allowedOrigins("*")//设置允许跨域请求的域名
//                .allowCredentials(true)//是否允许证书 不再默认开启
//                .allowedMethods("GET", "POST", "PUT", "DELETE")//设置允许的方法
//                .maxAge(3600);//跨域允许时间
//    }
	
}
