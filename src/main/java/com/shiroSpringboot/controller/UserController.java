package com.shiroSpringboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shiroSpringboot.common.Contant;
import com.shiroSpringboot.entity.User;
import com.shiroSpringboot.service.UserService;
import com.shiroSpringboot.vo.AjaxResponse;
import com.shiroSpringboot.vo.ShowPage;
import com.shiroSpringboot.vo.resultvo.UserVo;

@Controller
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private UserService userSer;
	
	@RequestMapping(value="/listPaging",method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse<ShowPage<UserVo>> selectUserSelectivePaging(@RequestBody UserVo userVo){
		List<User> userList = userSer.selectListSelectivePaging(userVo);
		ShowPage<UserVo> page = new ShowPage<>(userVo, userList);
		AjaxResponse<ShowPage<UserVo>> ajaxResponse = new AjaxResponse<ShowPage<UserVo>>(Contant.RESULT_SUCCESS,"获取用户数据成功！");
		ajaxResponse.setData(page);
		return ajaxResponse;
		
		
	}
	
}
