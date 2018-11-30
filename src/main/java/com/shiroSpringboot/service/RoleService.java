package com.shiroSpringboot.service;

import java.util.List;

import com.shiroSpringboot.entity.Role;

public interface RoleService extends BaseService<Role, Integer>{
	/**
	 * 根据用户查询所有的角色
	 * @param username
	 * @return
	 */
	List<Role> selectRolesByUsername(String username);

}
