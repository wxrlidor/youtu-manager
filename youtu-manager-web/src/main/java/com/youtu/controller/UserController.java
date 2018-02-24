package com.youtu.controller;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youtu.common.pojo.EasyUIDateGridResult;
import com.youtu.common.pojo.YouTuResult;
import com.youtu.service.UserService;

/**
 * 用户信息controller
 *@author:王贤锐
 *@date:2018年2月24日  下午2:02:03
**/
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	/**
	 * 根据过滤条件查询用户信息
	 * @param page
	 * @param rows
	 * @param id
	 * @param username
	 * @param phone
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/user/list")
	@ResponseBody
	public EasyUIDateGridResult getItemList(Integer page, Integer rows,Long id,String username,String phone) throws UnsupportedEncodingException {
		//解决乱码问题，tomcat默认是iso8859-1的编码
		if(!StringUtils.isBlank(username)){
			username = new String(username.getBytes("iso8859-1"), "utf-8");
		}
		EasyUIDateGridResult result = userService.getUserList(page, rows, id, username, phone);					
		return result;
	}
	/**
	 * 一次性启用多个用户
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/user/instock")
	@ResponseBody
	public YouTuResult instockUserByIds(String ids){
		YouTuResult result = userService.modifyUserStatusByIds(ids, (byte) 1);
		return result;
	}
	/**
	 * 一次性注销多个用户
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/user/reshelf")
	@ResponseBody
	public YouTuResult reshelfUserByIds(String ids){
		YouTuResult result = userService.modifyUserStatusByIds(ids, (byte) 2);
		return result;
	}
}
