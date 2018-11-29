package com.shiroSpringboot.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
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
		UsernamePasswordToken userToken = (UsernamePasswordToken)token;
		String username = userToken.getUsername();
		User user = userSer.selectUserByuserName(username);
		if(user == null) {
			throw new UnknownAccountException("用户名不存在！");
		}
		//主体的用户
		String name = this.getName();
		ByteSource bytes = ByteSource.Util.bytes(username);//加盐		
		//用户名和密码进行验证
		//参数1，与领域关联的主题，就输入的用户名，参数2，验证给定主体的散列凭证，输入的密码
		//参数3，使用凭证所加的盐，这里加盐是用用注册时候使用的username作为的盐
		//参数4，登陆成功后记录的用户
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,user.getPassword(),bytes,name);
		return info;
	}

}
