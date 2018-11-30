package com.shiroSpringboot.service;

import com.shiroSpringboot.entity.User;

public interface UserService extends BaseService<User, Integer> {
	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 */
	User selectUserByuserName(String username);
	
	
	
}
