package com.youtu.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youtu.common.pojo.EasyUIDateGridResult;
import com.youtu.mapper.TbOrderMapper;
import com.youtu.pojo.TbOrder;
import com.youtu.pojo.TbOrderExample;
import com.youtu.pojo.TbOrderExample.Criteria;
import com.youtu.service.OrderService;

/**
 * 订单管理service
 * 
 * @author:王贤锐
 * @date:2018年2月26日 下午10:10:41
 **/
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private TbOrderMapper orderMapper;

	/**
	 * 根据查询条件取得订单信息列表，返回easyUi支持的数据结果
	 */
	@Override
	public EasyUIDateGridResult getOrderList(int page, int rows, String orderId, Long userId) {
		// 使用分页查询订单列表
		TbOrderExample example = new TbOrderExample();
		Criteria criteria = example.createCriteria(); 
		// 判断订单id和买家id过滤条件是否为空
		if (!StringUtils.isBlank(orderId)) {
			// 添加订单id查询,字符串两边加上%代表模糊查询
			criteria.andOrderIdEqualTo(orderId);
		}
		if (userId != null) {
			// 添加买家id过滤条件
			criteria.andUserIdEqualTo(userId);
		}
		//分页查询
		PageHelper.startPage(page, rows);
		List<TbOrder> list = orderMapper.selectByExample(example);
		// 取出总数
		PageInfo<TbOrder> pageInfo = new PageInfo<>(list);
		long sum = pageInfo.getTotal();
		// 将查询到的信息保存到返回的pojo对象中
		EasyUIDateGridResult result = new EasyUIDateGridResult();
		result.setRows(list);
		result.setTotal(sum);
		return result;
	}

}
