package com.maxrumo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxrumo.entity.Permission;
import com.maxrumo.mapper.PermissionMapper;
import com.maxrumo.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService{

	@Autowired
	private PermissionMapper permissionMapper;
	@Override
	public List<Permission> findByUsername(String username) {
		return permissionMapper.findByUsername(username);
	}

}
