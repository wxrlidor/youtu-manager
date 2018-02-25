package com.youtu.service;
import com.youtu.common.pojo.EasyUIDateGridResult;
import com.youtu.common.pojo.YouTuResult;
import com.youtu.pojo.TbContent;


/**
 *@author:张晓芬
 *@date:2018年2月22日  下午9:46:35
**/
public interface ContentService {
	YouTuResult insertContent(TbContent content);

	EasyUIDateGridResult getContentListByCategoryId(long categoryId, int page, int rows);

	YouTuResult updateContent(TbContent tbContent);

	YouTuResult deleteContentsByIds(String ids);

}
