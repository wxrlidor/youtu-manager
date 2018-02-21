package com.youtu.service;
/**
 *@author:王贤锐
 *@date:2018年1月11日  下午8:22:20
**/

import com.youtu.common.pojo.EasyUIDateGridResult;
import com.youtu.common.pojo.YouTuResult;
import com.youtu.pojo.TbItemParam;

public interface ItemParamService {
	EasyUIDateGridResult getItemParamList(int page,int rows,Long itemCatId);
	
	YouTuResult getItemParamByItemCatId(long itemCatId);
	
	YouTuResult insertItemParam(TbItemParam tbItemParam);
	YouTuResult deleteItemParamByIds(String ids);
	
	YouTuResult updateItemParam(TbItemParam tbItemParam);
}
