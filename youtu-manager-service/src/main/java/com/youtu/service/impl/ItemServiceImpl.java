//service实现类

package com.youtu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youtu.common.pojo.EasyUIDateGridResult;
import com.youtu.mapper.TbItemMapper;
import com.youtu.pojo.TbItem;
import com.youtu.pojo.TbItemExample;
import com.youtu.pojo.TbItemExample.Criteria;
import com.youtu.service.ItemService;

/**商品管理service  
 *@author:张晓芬
 *@date:2018年2月12日  下午1:21:00
**/
@Service
public class ItemServiceImpl implements ItemService {
	/**
	 * 这里写注释，写下这个service方法是干嘛用的，比如这个就是接收参数商品ID，根据商品ID查询出商品信息，最后返回商品信息
	 * 一般的格式是  1.接收XXX参数 2.方法里面做了什么  3.返回什么数据
	 * 1.接收参数商品ID 2.根据商品ID查询出商品信息 3.最后返回商品信息
	 */
	@Autowired
	private TbItemMapper itemMapper;
	/** 
	 * 接收参数商品ID，根据商品ID查询出商品信息，最后返回商品信息  返回值的类型
	 */
	public TbItem getItemById(long itemId) {
		//方法1如下，虽然简捷，但改变id需要在后台修改
		//TbItem item = itemMapper.selectByPrimaryKey(itemId);
		
		//方法2：在浏览器网址便可修改id
		//首先创建一个tbItem对应的example对象
		TbItemExample example = new TbItemExample();
		//用example对象去新建一个查询条件对象criteria
		Criteria criteria = example.createCriteria(); //alt+ctrl+L
		criteria.andIdEqualTo(itemId);
		//使用mapper对象的方法，根据example去执行查询
		List<TbItem> list = itemMapper.selectByExample(example);
		//如果查询出来有结果，那么list的大小就要大于0
		//如果查询不到该id对应的商品，那么list的大小就为0
		//这时我们可以根据list的大小判断是否去得到对应id的商品
		if(list.size()>0 && list !=null ){
			//判断的id符合条件，执行下面的语句，返回对应id的商品信息
			TbItem tbItem = list.get(0);
			return tbItem;
		}
		//id无效，返回信息为空
		return null;       
	}
	//验证@Override下面的方法名是否是你父类中所有的,如果没有则报错
	@Override
	//方法：商品列表查询
	public EasyUIDateGridResult getItemList(int page, int rows) {
		TbItemExample example = new TbItemExample();
		PageHelper.startPage(page, rows);
		//List对象通过泛型的形式先定义一个对象类型
		List<TbItem> list = itemMapper.selectByExample(example);
		//创建一个返回值对象
				EasyUIDateGridResult result = new EasyUIDateGridResult();
				result.setRows(list);
				//取记录总条数
				PageInfo<TbItem> pageInfo = new PageInfo<>(list);
				result.setTotal(pageInfo.getTotal());
				return result;
	}

}
