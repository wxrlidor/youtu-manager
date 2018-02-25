package com.youtu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youtu.common.pojo.EasyUIDateGridResult;
import com.youtu.common.pojo.YouTuResult;
import com.youtu.mapper.TbContentMapper;
import com.youtu.pojo.TbContent;
import com.youtu.pojo.TbContentExample;
import com.youtu.service.ContentService;

/**
 * 内容管理
 * 
 * @author:张晓芬
 * @date:2018年2月22日 下午10:33:18
 **/
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;

	@Override
	public YouTuResult insertContent(TbContent content) {
		// 补全pojo内容
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);

		return YouTuResult.ok();
	}

	/**
	 * 根据内容分类id获取内容列表
	 */
	public EasyUIDateGridResult getContentListByCategoryId(long categoryId, int page, int rows) {
		// 根据categoryId查询出数据
		TbContentExample example = new TbContentExample();
		com.youtu.pojo.TbContentExample.Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		// 分页查询
		PageHelper.startPage(page, rows); 
		//使用withBLOBS方法，表示取数据时取出大文本数据
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		// 取出总数
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		long sum = pageInfo.getTotal();
		EasyUIDateGridResult result = new EasyUIDateGridResult();
		result.setRows(list);
		result.setTotal(sum);
		return result;
	}

	/**
	 * 更新内容数据
	 */

	public YouTuResult updateContent(TbContent tbContent) {
		// 补全pojo
		tbContent.setUpdated(new Date());
		tbContent.setCreated(new Date());
		// 更新数据，把大文本数据也更新进去
		contentMapper.updateByPrimaryKeyWithBLOBs(tbContent);
		return YouTuResult.ok();
	}

	/**
	 * 一次性删除多个内容数据
	 */
	public YouTuResult deleteContentsByIds(String ids) {
		// 这里使用split方法分割成字符串数组
		String[] idArray = ids.split(",");
		List<Long> list = new ArrayList<>();
		// 遍历字符串数组，拷贝到list中去，因为criteria的查询条件只支持list参数
		for (int i = 0; i < idArray.length; i++) {
			// 这里需要用Long.parseLong(String)方法把字符串变量转换Long变量
			list.add(Long.parseLong(idArray[i]));
		}
		TbContentExample example = new TbContentExample();
		com.youtu.pojo.TbContentExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(list);
		// 根据id,删除多个数据
		int num = contentMapper.deleteByExample(example);
		if (num > 0) {
			return YouTuResult.ok();
		}
		return YouTuResult.build(500, "删除出错");

	}
}
