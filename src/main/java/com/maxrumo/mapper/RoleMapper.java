package com.maxrumo.mapper;

import com.maxrumo.entity.Role;
import com.maxrumo.entity.RoleExample;
import java.util.List;

public interface RoleMapper {
    int countByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}