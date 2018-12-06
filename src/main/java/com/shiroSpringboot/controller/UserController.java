package com.shiroSpringboot.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.shiroSpringboot.vo.resultvo.UserVo;

@Controller
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private UserService userSer;

	@RequestMapping(value="/listPaging",method=RequestMethod.POST)
	@RequiresPermissions("user:listPaging")
	@ResponseBody
	public AjaxResponse<Object> selectUserSelectivePaging(@RequestBody UserVo userVo){
//		byte[] obj = redisCache.getBety("springbootkey");
//		List<User> userList = new ArrayList<>();
//		if(obj != null) {
//			 userList = JSONArray.parseArray(new String(obj), User.class);
//		}else {
//			userList = userSer.selectListSelectivePaging(userVo);
//		}

		List<User> 	userList = userSer.selectListSelectivePaging(userVo);
		List<Object> users = new ArrayList<>();
		users.addAll(userList);
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<Object>(Contant.RESULT_SUCCESS,"获取用户数据成功！");
		ajaxResponse.setData(users);
		ajaxResponse.setPageCount(userVo.getPageCount());
		return ajaxResponse;
	
	}
	
}
