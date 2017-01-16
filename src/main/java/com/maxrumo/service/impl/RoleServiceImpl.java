package com.maxrumo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxrumo.entity.Role;
import com.maxrumo.entity.RoleExample;
import com.maxrumo.mapper.RoleMapper;
import com.maxrumo.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	@Override
	public List<Role> findRoleByUsername(String username) {
		return roleMapper.findRoleByUsername(username);
	}
	
	@Override
	public List<Role> findAll() {
		return roleMapper.selectByExample(new RoleExample());
	}

}
