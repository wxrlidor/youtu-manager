package com.youtu.service;
/**
 *@author:张晓芬
 *@date:2018年2月21日  下午7:31:17
**/

import java.util.List;

import com.youtu.common.pojo.EUTreeNode;
import com.youtu.common.pojo.YouTuResult;

public interface ContentCategoryService {
	// 返回List<EUTreeNode>类型的方法，里面是长整型的参数parentId
	List<EUTreeNode> getCategoryList(Long parentId);

	YouTuResult insertContentCategory(Long parentId, String name);

	YouTuResult deleteCategory(Long id);

	YouTuResult updateCateory(Long id, String name);

}
