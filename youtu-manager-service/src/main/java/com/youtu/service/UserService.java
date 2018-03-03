package com.youtu.service;

import com.youtu.common.pojo.EasyUIDateGridResult;
import com.youtu.common.pojo.YouTuResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *@author:王贤锐
 *@date:2018年2月24日  下午2:02:39
**/
public interface UserService {
	EasyUIDateGridResult getUserList(int page, int rows,Long id,String username,String phone);
	
	YouTuResult modifyUserStatusByIds(String ids,byte status);
	
	YouTuResult adminLogin(String username, String password,HttpServletRequest request,HttpServletResponse response);
}
