package com.shiroSpringboot.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.shiroSpringboot.entity.User;
import com.shiroSpringboot.service.UserService;


@Configuration
public class ShiroRealm extends AuthorizingRealm{

	@Autowired
	private UserService userSer;
	
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 用户认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//获取用户名和密码
		String username =(String) token.getPrincipal();
		String password =(String) token.getCredentials();
		User user = userSer.selectUserByuserName(username);
		if(user == null) {
			throw  new UnknownAccountException("用户名获者密码错误！");
		}
		
		
		
		return null;
	}

}
