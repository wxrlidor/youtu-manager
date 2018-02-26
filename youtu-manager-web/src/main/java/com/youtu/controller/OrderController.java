package com.youtu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youtu.common.pojo.EasyUIDateGridResult;
import com.youtu.service.OrderService;

/**
 * 订单管理controller
 *@author:王贤锐
 *@date:2018年2月26日  下午10:18:50
**/
@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	/**
	 * 根据过滤条件，查询订单列表
	 * @param page
	 * @param rows
	 * @param orderId
	 * @param userId
	 * @return
	 */
	@RequestMapping("/order/list")
	@ResponseBody
	public EasyUIDateGridResult getOrderList(Integer page, Integer rows, String orderId, Long userId){
		EasyUIDateGridResult result = orderService.getOrderList(page, rows, orderId, userId);
		return result;
	}
}
