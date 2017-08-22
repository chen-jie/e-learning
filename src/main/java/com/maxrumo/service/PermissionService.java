package com.maxrumo.service;

import com.maxrumo.entity.Permission;
import com.maxrumo.entity.PermissionExample;
import com.maxrumo.mapper.PermissionMapper;
import com.maxrumo.util.CommonUtil;
import com.maxrumo.vo.TreeNode;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class PermissionService {

	@Autowired
	private PermissionMapper permissionMapper;
	public List<TreeNode> getPermissionTree() {
		List<Permission> list = permissionMapper.findAllTree();
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return CommonUtil.convetToTree(list);
	}
	public Permission findById(Integer id) {
		PermissionExample example = new PermissionExample();
		example.createCriteria().andIdEqualTo(id);
		List<Permission> list = permissionMapper.selectByExample(example);
		return CommonUtil.getFirst(list);
	}
	@Transactional
	public void insert(Permission permission) {
		//目前code都与url相同
		permission.setCode(permission.getUrl());
		permissionMapper.insertSelective(permission);
	}
	@Transactional
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
	public void update(Permission permission) {
		permissionMapper.updateByPrimaryKey(permission);
	}

//
//	public List<Permission> findByRoleId(int roleId) {
//		return permissionMapper.findByRoleId(roleId);
//	}
}
