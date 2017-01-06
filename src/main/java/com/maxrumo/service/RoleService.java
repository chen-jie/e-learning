package com.maxrumo.service;

import java.util.List;

import com.maxrumo.entity.Role;

public interface RoleService {

	public List<Role> findRoleByUsername(String username);
}
