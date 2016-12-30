package com.maxrumo.mapper;

import com.maxrumo.entity.UserRole;
import com.maxrumo.entity.UserRoleExample;
import java.util.List;

public interface UserRoleMapper {
    int countByExample(UserRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    List<UserRole> selectByExample(UserRoleExample example);

    UserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);
}