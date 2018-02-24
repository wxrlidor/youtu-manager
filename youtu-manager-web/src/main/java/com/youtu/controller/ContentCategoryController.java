package com.youtu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youtu.common.pojo.EUTreeNode;
import com.youtu.common.pojo.YouTuResult;
import com.youtu.service.ContentCategoryService;

/**
 *@author:张晓芬
 *@date:2018年2月21日  下午10:53:07
**/
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	@RequestMapping("/list")
	@ResponseBody
	                                               //需要有默认值，parentId为0
	public List<EUTreeNode> getContentCatList(@RequestParam(value="id", defaultValue="0")Long parentId) {
		List<EUTreeNode> list = contentCategoryService.getCategoryList(parentId);
		return list;
	}
	@RequestMapping("/create")
	@ResponseBody
//	接收两个参数parentid、name。调用Service添加记录。返回YouTuResult
	public YouTuResult createContentCategory(Long parentId, String name) {
		YouTuResult result = contentCategoryService.insertContentCategory(parentId,name);		
		return result;
}
}
