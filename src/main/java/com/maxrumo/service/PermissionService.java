package com.maxrumo.service;

import java.util.List;

import com.maxrumo.entity.Permission;
import com.maxrumo.vo.TreeNode;

public interface PermissionService {

	public List<Permission> findByUsername(String username);

	
	/** 
	 * @description 查询资源的树，给前台使用
	 * @author max
	 * @date 2017年1月13日 上午10:17:26
	 * @return
	 */
	public List<TreeNode> getPermissionTree();

	public Permission findById(Integer id);

	public void insert(Permission permission);

	
	/** 
	 * @description 删除资源及其子资源
	 * @author max
	 * @date 2017年1月13日 上午10:17:07
	 */
	public void deletePemAndChildren(int id);


	public void update(Permission permission);
}
