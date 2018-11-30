package com.shiroSpringboot.service;

import java.util.List;

import com.shiroSpringboot.entity.Menu;

public interface MenuService extends BaseService<MenuService, Integer>{
	/**
	 * 通过用户名查询用户的的所以权限
	 * @param username
	 * @return
	 */
	List<Menu> selectPermissionByUsername(String username);
	
}
