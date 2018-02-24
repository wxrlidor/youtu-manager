/**
 * 
 */
package com.youtu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youtu.common.pojo.EUTreeNode;
import com.youtu.common.pojo.YouTuResult;
import com.youtu.mapper.TbContentCategoryMapper;
import com.youtu.pojo.TbContentCategory;
import com.youtu.pojo.TbContentCategoryExample;
import com.youtu.pojo.TbContentCategoryExample.Criteria;
import com.youtu.service.ContentCategoryService;

/**内容分类管理
 *@author:张晓芬
 *@date:2018年2月21日  下午7:40:40
**/
/**
 * @author 张晓芬
 * @date :2018年2月21日 下午7:40:40
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.youtu.service.ContentCategoryService#getCategoryList(long)
	 */
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

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

public YouTuResult insertContentCategory(Long parentId,String name){
	//创建一个pojo
			TbContentCategory contentCategory = new TbContentCategory();
			contentCategory.setName(name);
			contentCategory.setIsParent(false);
			//'状态。可选值:1(正常),2(删除)',
			contentCategory.setStatus(1);
			contentCategory.setParentId(parentId);
			contentCategory.setSortOrder(1);
			contentCategory.setCreated(new Date());
			contentCategory.setUpdated(new Date());
			//添加记录
			contentCategoryMapper.insert(contentCategory);
			//查看父节点的isParent列是否为true，如果不是true改成true
			TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
			//判断是否为true
			if(!parentCat.getIsParent()) {
				parentCat.setIsParent(true);
				//更新父节点
				contentCategoryMapper.updateByPrimaryKey(parentCat);
			}
			//返回结果
			return YouTuResult.ok(contentCategory);
}
}
