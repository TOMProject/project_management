package com.shiroSpringboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shiroSpringboot.entity.Menu;
import com.shiroSpringboot.mapper.MenuMapper;
import com.shiroSpringboot.service.MenuService;
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuService, Integer> implements MenuService {

	@Autowired
	private MenuMapper menuMapper;
	
	@Override
	public List<Menu> selectPermissionByUsername(String username) {
		
		return menuMapper.selectPermissionByUsername(username);
	}

}
