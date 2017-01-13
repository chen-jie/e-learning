package com.maxrumo.mapper;

import com.maxrumo.entity.Permission;
import com.maxrumo.entity.PermissionExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionMapper {
    int countByExample(PermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    List<Permission> selectByExample(PermissionExample example);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

	List<Permission> findByUsername(String username);
	
	/** 
	 * @description 查询所有权限及子权限组成的树
	 * @author cj
	 * @date 2017年1月12日 下午2:39:02
	 * @param username
	 * @return
	 */
	List<Permission> findAllTree();
	
	List<Permission> findByParentId(Integer parentId);
}