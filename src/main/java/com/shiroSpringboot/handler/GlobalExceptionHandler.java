package com.shiroSpringboot.handler;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.ExpiredSessionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shiroSpringboot.common.Contant;
import com.shiroSpringboot.vo.AjaxResponse;


@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AuthorizationException.class)
    public AjaxResponse<Object> handleAuthorizationException(HttpServletRequest request) {
        if (isAjaxRequest(request)) {
            return new AjaxResponse<Object>(Contant.RESULT_ERROR,"暂无权限，请联系管理员！");
        } else {
        	return new AjaxResponse<Object>(Contant.RESULT_ERROR,"暂无权限，请联系管理员！跳转页面");
        }
    }

    @ExceptionHandler(value = ExpiredSessionException.class)
    public String handleExpiredSessionException() {
        return "/login";
    }

  

    private static boolean isAjaxRequest(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(request.getHeader("X-Requested-With")));
    }

	
	
}
