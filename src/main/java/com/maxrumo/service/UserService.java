package com.maxrumo.service;

import com.maxrumo.entity.User;
import com.maxrumo.entity.UserExample;
import com.maxrumo.mapper.RoleMapper;
import com.maxrumo.mapper.UserMapper;
import com.maxrumo.util.CommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

	@Autowired
	UserMapper userMapper;
	@Autowired
	RoleMapper roleMapper;

	public User login(String username, String password) {
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		criteria.andPasswordEqualTo(password);
		List<User> list = userMapper.selectByExample(example);
		return CommonUtil.getFirst(list);
	}

	@Transactional
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
		user.setRoleId(3);
		userMapper.insert(user);


		return map;
	}

	public User getById(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	public User findByUsername(String username) {
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<User> result = userMapper.selectByExample(example);
		return CommonUtil.getFirst(result);
	}

	public boolean isUsernameExist(String username) {
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<User> result = userMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(result)){
			return true;
		}
		return false;
	}

	public boolean isEmailExist(String email) {
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		criteria.andEmailEqualTo(email);
		List<User> result = userMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(result)){
			return true;
		}
		return false;
	}

	public boolean isNicknameExist(String nickname) {
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		criteria.andNicknameEqualTo(nickname);
		List<User> result = userMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(result)){
			return true;
		}
		return false;
	}

	public User findById(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	public void editPassword(String opassword, String password) {
//		User user = CommonUtil.getCurrentUser();
//		User user = (User)SecurityUtils.getSubject().getSession().getAttribute(Constant.SESSION_USER);
		int userId = CommonUtil.getCurrentUserId();
		User user = userMapper.selectByPrimaryKey(userId);
		String md5 = CommonUtil.MD5(opassword);
		if(user == null || !user.getPassword().equals(md5)){
			throw new RuntimeException("原密码错误");
		}
		user.setPassword(CommonUtil.MD5(password));
		userMapper.updateByPrimaryKey(user);
	}

	public List<User> findAll() {
		UserExample example = new UserExample();
		example.createCriteria().andRoleIdNotEqualTo(1);
		return userMapper.selectByExample(example);
	}

	public void deleteUser(int id) {
		userMapper.deleteByPrimaryKey(id);
	}

	public void registerTeacher(User user) {
		user.setPassword(CommonUtil.MD5(user.getPassword()));
		user.setRoleId(2);
		userMapper.insert(user);
	}
}
