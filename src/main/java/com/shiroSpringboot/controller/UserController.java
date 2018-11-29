package com.shiroSpringboot.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.shiroSpringboot.common.Contant;
import com.shiroSpringboot.config.RedisCache;
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
	@Autowired
	private RedisCache redisCache;
	
	
	@RequestMapping(value="/listPaging",method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse<ShowPage<UserVo>> selectUserSelectivePaging(@RequestBody UserVo userVo){
//		byte[] obj = redisCache.getBety("springbootkey");
//		List<User> userList = new ArrayList<>();
//		if(obj != null) {
//			 userList = JSONArray.parseArray(new String(obj), User.class);
//		}else {
//			userList = userSer.selectListSelectivePaging(userVo);
//		}
		Object list = redisCache.getList("springbootkey");
		List<User> userList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty((List)list)) {
			userList = (List<User>) list;
		}else {
			userList = userSer.selectListSelectivePaging(userVo);
			redisCache.setList("springbootkey",userList);
		}
		ShowPage<UserVo> page = new ShowPage<>(userVo, userList);
		AjaxResponse<ShowPage<UserVo>> ajaxResponse = new AjaxResponse<ShowPage<UserVo>>(Contant.RESULT_SUCCESS,"获取用户数据成功！");
		ajaxResponse.setData(page);
		
		return ajaxResponse;
		
		
	}
	
}
