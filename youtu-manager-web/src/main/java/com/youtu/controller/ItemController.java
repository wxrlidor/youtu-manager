package com.youtu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.tools.internal.xjc.generator.bean.ImplStructureStrategy.Result;
import com.youtu.common.pojo.EasyUIDateGridResult;
import com.youtu.pojo.TbItem;
import com.youtu.service.ItemService;

/**
 * @author:张晓芬
 * @date:2018年2月12日 下午11:55:26
 **/
@Controller // 加入controller注解 键值对 key-value
public class ItemController {

	@Autowired // 注入Service
	private ItemService itemService;

	@RequestMapping("/item/{itemId}") // 配置url和方法的映射关系
	@ResponseBody // 会返回json数据，并插入包
	// 返回TbItem对象-tbItem，
	public TbItem getItemById(@PathVariable Long itemId) { // @Path---从路径中取数据
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}

	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDateGridResult getItemList(Integer page, Integer rows) {
		itemService.getItemList(page, rows);
		EasyUIDateGridResult result = itemService.getItemList(page, rows);
		return result;
	}
}
