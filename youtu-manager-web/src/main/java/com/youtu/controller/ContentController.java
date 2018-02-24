package com.youtu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youtu.common.pojo.YouTuResult;
import com.youtu.pojo.TbContent;
import com.youtu.service.ContentService;

/**内容管理controller
 *@author:张晓芬
 *@date:2018年2月22日  下午10:58:26
**/
@Controller
@RequestMapping("/content")
public class ContentController {
	@Autowired
	public ContentService contentService;
	@RequestMapping("/save")
	@ResponseBody
	public YouTuResult insertContent(TbContent content){
		YouTuResult result = contentService.insertContent(content);
		return result;
	}
}
  