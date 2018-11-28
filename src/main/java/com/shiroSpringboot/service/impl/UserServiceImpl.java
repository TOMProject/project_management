package com.shiroSpringboot.service.impl;

import org.springframework.stereotype.Service;

import com.shiroSpringboot.entity.User;
import com.shiroSpringboot.service.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<User,Integer> implements UserService{

}
