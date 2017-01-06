package com.maxrumo.service;

import java.util.List;

import com.maxrumo.entity.Permission;

public interface PermissionService {

	public List<Permission> findByUsername(String username);
}
