package com.youtu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/** 页面跳转controller
 *@author:张晓芬
 *@date:2018年2月13日  下午7:33:21
**/
@Controller
	//功能：打开首页
	public class PageController {
	@RequestMapping("/")        //直接访问根路径，打开index首页
	public String showIndex(){
		return  "index";
	}
	//展示其他页面
	@RequestMapping("/{page}")
	public String showpage(@PathVariable String page) {
		return page;
	}

}
