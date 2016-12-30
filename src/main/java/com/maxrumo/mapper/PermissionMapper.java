package com.maxrumo.mapper;

import com.maxrumo.entity.Permission;
import com.maxrumo.entity.PermissionExample;
import java.util.List;

public interface PermissionMapper {
    int countByExample(PermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    List<Permission> selectByExample(PermissionExample example);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}