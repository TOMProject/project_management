package com.shiroSpringboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shiroSpringboot.entity.Role;
import com.shiroSpringboot.mapper.RoleMapper;
import com.shiroSpringboot.service.RoleService;
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Integer> implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public List<Role> selectRolesByUsername(String username) {
		return roleMapper.selectRolesByUsername(username);
	}

	

}
