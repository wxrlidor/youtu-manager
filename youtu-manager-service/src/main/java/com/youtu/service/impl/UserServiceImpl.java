package com.youtu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youtu.common.pojo.EasyUIDateGridResult;
import com.youtu.common.pojo.YouTuResult;
import com.youtu.mapper.TbUserMapper;
import com.youtu.pojo.TbUser;
import com.youtu.pojo.TbUserExample;
import com.youtu.pojo.TbUserExample.Criteria;
import com.youtu.service.UserService;

/**
 * @author:王贤锐
 * @date:2018年2月24日 下午2:04:17
 **/
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private TbUserMapper userMapper;

	/**
	 * 根据查询条件，查询出用户列表信息
	 */
	@Override
	public EasyUIDateGridResult getUserList(int page, int rows, Long id, String username, String phone) {
		// 使用分页查询
		TbUserExample userExample = new TbUserExample();
		Criteria criteria = userExample.createCriteria();
		// 先判断过滤条件是否为空
		if (id != null) {
			// 添加id过滤条件
			criteria.andIdEqualTo(id);
		}
		if (StringUtils.isNoneBlank(username)) {
			// 添加用户名查询条件,模糊查询
			criteria.andUsernameLike("%" + username + "%");
		}
		if (StringUtils.isNoneBlank(phone)) {
			// 添加手机号查询条件
			criteria.andPhoneLike("%" + phone + "%");
		}
		PageHelper.startPage(page, rows);
		List<TbUser> list = userMapper.selectByExample(userExample);
		// 取出总数
		PageInfo<TbUser> pageInfo = new PageInfo<>(list);
		long sum = pageInfo.getTotal();
		// 将查询到的信息保存到返回的pojo对象中
		EasyUIDateGridResult result = new EasyUIDateGridResult();
		result.setRows(list);
		result.setTotal(sum);
		return result;
	}
	/**
	 * 根据ids一次性修改用户状态,用户状态，1-启用，2-注销
	 */
	@Override
	public YouTuResult modifyUserStatusByIds(String ids, byte status) {
		YouTuResult youTuResult = new YouTuResult(null);
		// 传进来的ids格式为"12323,3434,3232",有多个id时以","隔开
		// 这里使用split方法分割成字符串数组
		String[] idArray = ids.split(",");
		List<Long> list = new ArrayList<>();
		// 遍历字符串数组，拷贝到list中去，因为criteria的查询条件只支持list参数
		for (int i = 0; i < idArray.length; i++) {
			// 这里需要用Long.parseLong(String)方法把字符串变量转换Long变量
			list.add(Long.parseLong(idArray[i]));
		}
		// 创建查询条件
		TbUserExample userExample = new TbUserExample();
		Criteria criteria = userExample.createCriteria();
		// 这里相当于添加查询语句 id in ('123232','232323','121212'),可以根据多个id查询出多个结果
		criteria.andIdIn(list);
		List<TbUser> userList = userMapper.selectByExample(userExample);
		// 遍历查询出来的结果，修改状态
		for (TbUser tbUser : userList) {
			tbUser.setStatus(status);
			// 同时设置更新时间
			tbUser.setUpdated(new Date());
			// 然后再进行保存操作,更新方法会返回值,成功时返回受影响的行数
			//mapper文件里面需要修改update方法，去掉创建时间的修改
			int result = userMapper.updateByPrimaryKey(tbUser);
			if (result < 1) {// 说明更新失败,返回状态设置为非200
				youTuResult.setStatus(100);
			}
		}
		return youTuResult;
	}

}
