//service接口

package com.youtu.service;

import com.youtu.common.pojo.EasyUIDateGridResult;
import com.youtu.common.pojo.YouTuResult;
import com.youtu.pojo.TbItem;

/**
 * @author:张晓芬
 * @date:2018年2月11日 下午11:24:47
 **/
public interface ItemService {
	// 对了，有时候如果没有自动提示，你可以按alt+/ 进行提示
	TbItem getItemById(long itemId);

	EasyUIDateGridResult getItemList(int page, int rows, Long id, String title);

	YouTuResult createItem(TbItem tbItem, String desc, String itemParams) throws Exception;

	YouTuResult updateItem(TbItem tbItem, String desc, String itemParams, String itemParamId) throws Exception;

	YouTuResult getDescByItemId(long itemId);

	YouTuResult getParmaItemByItemId(long itemId);

	YouTuResult modifyItemsByIds(String ids, byte status);

}
