//service实现类

package com.youtu.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youtu.common.pojo.EasyUIDateGridResult;
import com.youtu.common.pojo.YouTuResult;
import com.youtu.common.utils.IDUtils;
import com.youtu.mapper.TbItemCatMapper;
import com.youtu.mapper.TbItemDescMapper;
import com.youtu.mapper.TbItemMapper;
import com.youtu.mapper.TbItemParamItemMapper;
import com.youtu.pojo.TbItem;
import com.youtu.pojo.TbItemCat;
import com.youtu.pojo.TbItemCatExample;
import com.youtu.pojo.TbItemDesc;
import com.youtu.pojo.TbItemExample;
import com.youtu.pojo.TbItemExample.Criteria;
import com.youtu.service.ItemService;

/**
 * 商品管理service
 * 
 * @author:张晓芬
 * @date:2018年2月12日 下午1:21:00
 **/
@Service
public class ItemServiceImpl implements ItemService {
	/**
	 * 这里写注释，写下这个service方法是干嘛用的，比如这个就是接收参数商品ID，根据商品ID查询出商品信息，最后返回商品信息 一般的格式是
	 * 1.接收XXX参数 2.方法里面做了什么 3.返回什么数据 1.接收参数商品ID 2.根据商品ID查询出商品信息 3.最后返回商品信息
	 */
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;

	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;

	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	/**
	 * 接收参数商品ID，根据商品ID查询出商品信息，最后返回商品信息 返回值的类型
	 */
	public TbItem getItemById(long itemId) {
		// 方法1如下，虽然简捷，但改变id需要在后台修改
		// TbItem item = itemMapper.selectByPrimaryKey(itemId);

		// 方法2：在浏览器网址便可修改id
		// 首先创建一个tbItem对应的example对象
		TbItemExample example = new TbItemExample();
		// 用example对象去新建一个查询条件对象criteria
		Criteria criteria = example.createCriteria(); // alt+ctrl+L
		criteria.andIdEqualTo(itemId);
		// 使用mapper对象的方法，根据example去执行查询
		List<TbItem> list = itemMapper.selectByExample(example);
		// 如果查询出来有结果，那么list的大小就要大于0
		// 如果查询不到该id对应的商品，那么list的大小就为0
		// 这时我们可以根据list的大小判断是否去得到对应id的商品
		if (list.size() > 0 && list != null) {
			// 判断的id符合条件，执行下面的语句，返回对应id的商品信息
			TbItem tbItem = list.get(0);
			return tbItem;
		}
		// id无效，返回信息为空
		return null;
	}

	// 验证@Override下面的方法名是否是你父类中所有的,如果没有则报错
	@Override
	// 方法：商品列表查询
	public EasyUIDateGridResult getItemList(int page, int rows,Long id,String title) {
		/*TbItemExample example = new TbItemExample();
		PageHelper.startPage(page, rows);
		// List对象通过泛型的形式先定义一个对象类型
		List<TbItem> list = itemMapper.selectByExample(example);
		// 创建一个返回值对象
		EasyUIDateGridResult result = new EasyUIDateGridResult();
		result.setRows(list);
		// 取记录总条数
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;*/
		// 使用分页查询商品列表
		TbItemExample tbItemExample = new TbItemExample();
		Criteria criteriaItem = tbItemExample.createCriteria(); // 这里过滤掉已经删除的
		criteriaItem.andStatusNotEqualTo((byte) 3);
		// 判断id和title过滤条件是否为空
		if (id != null) {
			// 添加id过滤条件
			criteriaItem.andIdEqualTo(id);
		}
		if (!StringUtils.isBlank(title)) {
			// 添加商品标题模糊查询,字符串两边加上%代表模糊查询
			title = "%" + title + "%";
			criteriaItem.andTitleLike(title);
		}
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(tbItemExample);
		for (TbItem tbItem : list) {
			// 根据catId去查询出商品分类的信息，取出名称在加入到原本的数据中，最后展示在页面上
			TbItemCatExample example = new TbItemCatExample();
			com.youtu.pojo.TbItemCatExample.Criteria criteria = example.createCriteria();
			criteria.andIdEqualTo(tbItem.getCid());
			List<TbItemCat> catlist = tbItemCatMapper.selectByExample(example);
			if (catlist != null && catlist.size() > 0) {
				tbItem.setCatName(catlist.get(0).getName());
			}
		}
		// 取出总数
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		long sum = pageInfo.getTotal();
		// 将查询到的信息保存到返回的pojo对象中
		EasyUIDateGridResult result = new EasyUIDateGridResult();
		result.setRows(list);
		result.setTotal(sum);
		return result;
	}
	/**
	 * 新增商品
	 * 
	 * @throws Exception
	 */
	@Override
	public YouTuResult createItem(TbItem tbItem, String desc, String itemParams) throws Exception {
		// 先补全tbItem
		tbItem.setId(IDUtils.genItemId());
		tbItem.setStatus((byte) 1);
		tbItem.setCreated(new Date());
		tbItem.setUpdated(new Date());
		// 保存到数据库中
		itemMapper.insert(tbItem);
		// 添加商品描述
		YouTuResult result = addDesc(tbItem.getId(), desc);
		if (result.getStatus() != 200) {
			// 这里抛出异常，spring就会知道出错，进行事务回滚
			throw new Exception();
		}
		// 保存规格参数
		//result = insertItemParamItem(tbItem.getId(), itemParams);
		if (result.getStatus() != 200) {
			// 这里抛出异常，spring就会知道出错，进行事务回滚
			throw new Exception();
		}
		// 返回结果
		return YouTuResult.ok();
	}

	/**
	 * 添加商品描述
	 * 
	 * @param tbitem
	 * @param desc
	 * @return
	 */
	private YouTuResult addDesc(long itemId, String desc) {
		// 创建pojo对象，补全信息
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setItemId(itemId);
		tbItemDesc.setUpdated(new Date());
		tbItemDescMapper.insert(tbItemDesc);
		return YouTuResult.ok();
	}
}
