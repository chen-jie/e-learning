package com.maxrumo.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maxrumo.entity.Permission;
import com.maxrumo.entity.PermissionExample;
import com.maxrumo.mapper.PermissionMapper;
import com.maxrumo.service.PermissionService;
import com.maxrumo.util.CommonUtil;
import com.maxrumo.vo.TreeNode;

@Service
public class PermissionServiceImpl implements PermissionService{

	@Autowired
	private PermissionMapper permissionMapper;
	@Override
	public List<Permission> findByUsername(String username) {
		return permissionMapper.findByUsername(username);
	}
	@Override
	public List<TreeNode> getPermissionTree() {
		List<Permission> list = permissionMapper.findAllTree();
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return CommonUtil.convetToTree(list);
	}
	@Override
	public Permission findById(Integer id) {
		PermissionExample example = new PermissionExample();
		example.createCriteria().andIdEqualTo(id);
		List<Permission> list = permissionMapper.selectByExample(example);
		return CommonUtil.getFirst(list);
	}
	@Transactional
	@Override
	public void insert(Permission permission) {
		//目前code都与url相同
		permission.setCode(permission.getUrl());
		permissionMapper.insertSelective(permission);
	}
	@Transactional
	@Override
	public void deletePemAndChildren(int id) {
		//查询子权限
		List<Permission> list = permissionMapper.findByParentId(id);
		//迭代删除子权限，效率低，懒得优化
		if(CollectionUtils.isNotEmpty(list)){
			for(Permission perm : list){
				permissionMapper.deleteByPrimaryKey(perm.getId());
			}
		}
		//删除自己
		permissionMapper.deleteByPrimaryKey(id);
	}
	@Transactional
	@Override
	public void update(Permission permission) {
		permissionMapper.updateByPrimaryKey(permission);
	}

	
}
