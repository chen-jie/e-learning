package com.maxrumo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.maxrumo.entity.Role;
import com.maxrumo.entity.RoleExample;

@Mapper
public interface RoleMapper {
    int countByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    List<Role> findRoleByUsername(String username);
    
    void insertUserRole(@Param("userId")int userId,@Param("roleId")int roleId);
}