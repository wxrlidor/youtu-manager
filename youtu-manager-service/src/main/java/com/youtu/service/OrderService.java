package com.youtu.service;
/**
 *@author:王贤锐
 *@date:2018年2月26日  下午10:08:56
**/

import com.youtu.common.pojo.EasyUIDateGridResult;

public interface OrderService {
	EasyUIDateGridResult getOrderList(int page,int rows,String orderId,Long userId);
}
