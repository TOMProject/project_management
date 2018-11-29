package com.shiroSpringboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shiroSpringboot.entity.User;
import com.shiroSpringboot.mapper.UserMapper;
import com.shiroSpringboot.service.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<User,Integer> implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User selectUserByuserName(String username) {
		return userMapper.selectUserByuserName(username);
	}

}
