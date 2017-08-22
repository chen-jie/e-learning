package com.maxrumo.service;

import com.maxrumo.entity.Role;
import com.maxrumo.entity.RoleExample;
import com.maxrumo.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;
	public List<Role> findRoleByUsername(String username) {
		return roleMapper.findRoleByUsername(username);
	}

	public List<Role> findAll() {
		return roleMapper.selectByExample(new RoleExample());
	}

	public Role findById(int id) {
		return roleMapper.selectByPrimaryKey(id);
	}
}
