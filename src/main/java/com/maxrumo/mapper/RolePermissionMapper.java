package com.maxrumo.mapper;

import com.maxrumo.entity.RolePermission;
import com.maxrumo.entity.RolePermissionExample;
import java.util.List;

public interface RolePermissionMapper {
    int countByExample(RolePermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    List<RolePermission> selectByExample(RolePermissionExample example);

    RolePermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);
}