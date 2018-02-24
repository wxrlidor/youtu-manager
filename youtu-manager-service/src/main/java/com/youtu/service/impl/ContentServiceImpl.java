package com.youtu.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youtu.common.pojo.YouTuResult;
import com.youtu.mapper.TbContentMapper;
import com.youtu.pojo.TbContent;
import com.youtu.service.ContentService;

/**内容管理
 *@author:张晓芬
 *@date:2018年2月22日  下午10:33:18
**/
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;
	@Override
	public YouTuResult insertContent(TbContent content) {
		//补全pojo内容
				content.setCreated(new Date());
				content.setUpdated(new Date());
				contentMapper.insert(content);
				
				return YouTuResult.ok();
	}

}
