package com.youtu.service;

import com.youtu.common.pojo.EasyUIDateGridResult;
import com.youtu.common.pojo.YouTuResult;

/**
 *@author:王贤锐
 *@date:2018年2月24日  下午2:02:39
**/
public interface UserService {
	EasyUIDateGridResult getUserList(int page, int rows,Long id,String username,String phone);
	
	YouTuResult modifyUserStatusByIds(String ids,byte status);
}
