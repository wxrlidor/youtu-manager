package com.youtu.controller;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youtu.mapper.TbItemMapper;
import com.youtu.pojo.TbItem;
import com.youtu.pojo.TbItemExample;

/**
 *@author:张晓芬
 *@date:2018年2月13日  下午10:17:49
**/
public class testPageHelper {
	//加上@Test注解，就可以直接运行当前的普通方法，而不需要定义main方法。
	@Test
	public void testPageHelper() {
	//创建(初始化）一个spring容器
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
			//从spring容器中获得Mapper的代理对象
			TbItemMapper mapper = applicationContext.getBean(TbItemMapper.class);
			//执行查询，并分页
			TbItemExample example = new TbItemExample();
			//分页处理
			PageHelper.startPage(3, 10); //（page，rows）要显示第几页--每页显示的记录数
			List<TbItem> list = mapper.selectByExample(example);
			//取商品列表
			for (TbItem tbItem : list) {
				System.out.println(tbItem.getTitle());
			}
			//取分页信息
			PageInfo<TbItem> pageInfo = new PageInfo<>(list);
			long total = pageInfo.getTotal();
			System.out.println("共有商品："+ total);
			
		}

}
