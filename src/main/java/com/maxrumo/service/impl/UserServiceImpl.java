package com.maxrumo.service.impl;

import com.maxrumo.entity.User;
import com.maxrumo.entity.UserExample;
import com.maxrumo.entity.UserExample.Criteria;
import com.maxrumo.mapper.UserMapper;
import com.maxrumo.service.UserService;
import com.maxrumo.util.CommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;
	@Override
	public User login(String username, String password) {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		criteria.andPasswordEqualTo(password);
		List<User> list = userMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}

	@Override
	public Map<String,Object> register(User user) {
		Map<String,Object> map = new HashMap<String, Object>();
		
		String username = user.getUsername();
		String email = user.getEmail();
		String nickname = user.getNickname();
		
		//基本非空判断
		if(StringUtils.isBlank(username)){
			map.put("msg", "用户名不能为空");
			return map;
		}
		if(StringUtils.isBlank(user.getPassword())){
			map.put("msg", "密码不能为空");
			return map;
		}
		if(StringUtils.isBlank(email)){
			map.put("msg", "邮箱不能为空");
			return map;
		}
		if(StringUtils.isBlank(nickname)){
			map.put("msg", "昵称不能为空");
			return map;
		}
		//查询数据库
		if(this.isUsernameExist(username)){
			map.put("msg", "用户名已存在");
			return map;
		}
		if(this.isEmailExist(email)){
			map.put("msg", "邮箱已被使用");
			return map;
		}
		if(this.isNicknameExist(nickname)){
			map.put("msg", "昵称已被使用");
			return map;
		}
		user.setPassword(CommonUtil.MD5(user.getPassword()));
		userMapper.insert(user);
		return map;
	}

	@Override
	public User getById(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean isUsernameExist(String username) {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<User> result = userMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(result)){
			return true;
		}
		return false;
	}

	@Override
	public boolean isEmailExist(String email) {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmailEqualTo(email);
		List<User> result = userMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(result)){
			return true;
		}
		return false;
	}

	@Override
	public boolean isNicknameExist(String nickname) {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andNicknameEqualTo(nickname);
		List<User> result = userMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(result)){
			return true;
		}
		return false;
	}
	
}
