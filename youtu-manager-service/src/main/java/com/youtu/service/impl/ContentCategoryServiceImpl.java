/**
 * 
 */
package com.youtu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.youtu.common.pojo.EUTreeNode;
import com.youtu.common.pojo.YouTuResult;
import com.youtu.common.utils.HttpClientUtil;
import com.youtu.mapper.TbContentCategoryMapper;
import com.youtu.pojo.TbContentCategory;
import com.youtu.pojo.TbContentCategoryExample;
import com.youtu.pojo.TbContentCategoryExample.Criteria;
import com.youtu.service.ContentCategoryService;

/**
 * 内容分类管理
 * 
 * @author:张晓芬
 * @date:2018年2月21日 下午7:40:40
 **/
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;

	@Override
	public List<EUTreeNode> getCategoryList(Long parentId) {
		//// 根据parentid查询节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EUTreeNode> resultList = new ArrayList<>();
		// for循环遍历list，最终生成List<EUTreeNode>
		for (TbContentCategory tbContentCategory : list) {
			//// 创建一个节点
			EUTreeNode node = new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			// 如果是Parent，就closed，否则open
			node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			// 把节点添加到列表
			resultList.add(node);
		}
		return resultList;
	}

	public YouTuResult insertContentCategory(Long parentId, String name) {
		// 创建一个pojo
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		// '状态。可选值:1(正常),2(删除)',
		contentCategory.setStatus(1);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		// 添加记录
		contentCategoryMapper.insert(contentCategory);
		// 查看父节点的isParent列是否为true，如果不是true改成true
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		// 判断是否为true
		if (!parentCat.getIsParent()) {
			parentCat.setIsParent(true);
			// 更新父节点
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		// 返回结果
		return YouTuResult.ok(contentCategory);
	}

	/**
	 * 删除结点
	 */
	@Override
	public YouTuResult deleteCategory(Long id) {
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
		long parentId = category.getParentId();
		if (category.getIsParent()) {
			// 如果是父节点，就不能删除
			YouTuResult result = new YouTuResult();
			result.setStatus(111);
			return result;
		}
		// 根据id删除结点
		contentCategoryMapper.deleteByPrimaryKey(id);
		// 判断父节点下面是否还有子节点，没有的话就把父节点的isparent改为false
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			// 不存在子节点，更新父节点
			TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
			parentCat.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}

		// redis同步缓存
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return YouTuResult.ok();
	}

	/**
	 * 更新结点
	 */
	@Override
	public YouTuResult updateCateory(Long id, String name) {
		// 根据id查询出pojo对象
		TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		tbContentCategory.setName(name);
		// 更新结点名称
		contentCategoryMapper.updateByPrimaryKey(tbContentCategory);
		return YouTuResult.ok();
	}
}
