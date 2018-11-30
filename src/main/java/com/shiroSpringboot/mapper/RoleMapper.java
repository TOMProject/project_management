package com.shiroSpringboot.mapper;

import java.util.List;

import com.shiroSpringboot.entity.Role;

public interface RoleMapper extends BaseMapper<Role, Integer>{
	/**
	 * 根据用户查询所有的角色
	 * @param username
	 * @return
	 */
	List<Role> selectRolesByUsername(String username);

}