package com.youtu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youtu.common.pojo.EasyUIDateGridResult;
import com.youtu.common.pojo.YouTuResult;
import com.youtu.pojo.TbContent;
import com.youtu.service.ContentService;

/**
 * 内容管理controller
 * 
 * @author:张晓芬
 * @date:2018年2月22日 下午10:58:26
 **/
@Controller
public class ContentController {
	@Autowired
	public ContentService contentService;

	@RequestMapping("/content/save")
	@ResponseBody
	public YouTuResult insertContent(TbContent content) {
		YouTuResult result = contentService.insertContent(content);
		return result;
	}

	// 用这种注释的好处就是会自动根据方法的参数多少和返回值生成这么个格式的注释，你可以对每一个参数做详细的说明，但是也可以不说明，随便你的
	/**
	 * 根据categoryId取得内容列表
	 * @param page
	 *            当前页数
	 * @param rows
	 *            每页显示条数
	 * @param categoryId
	 *            内容分类id
	 * @return
	 */
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDateGridResult getContentListByCid(int page, int rows, long categoryId) {
		return contentService.getContentListByCategoryId(categoryId, page, rows);
	}

	/**
	 * 更新内容
	 * 
	 * @param tbContent
	 * @return
	 */
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public YouTuResult updateContent(TbContent tbContent) {
		return contentService.updateContent(tbContent);
	}

	/**
	 * 删除内容
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/content/delete")
	@ResponseBody
	public YouTuResult deleteContentsByIds(String ids) {
		return contentService.deleteContentsByIds(ids);
	}

}
