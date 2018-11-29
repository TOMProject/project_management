package com.shiroSpringboot.mapper;

import com.shiroSpringboot.entity.User;

public interface UserMapper extends BaseMapper<User, Integer>{
	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 */
	User selectUserByuserName(String username);
}
