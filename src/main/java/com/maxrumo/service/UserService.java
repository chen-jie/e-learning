package com.maxrumo.service;

import java.util.Map;

import com.maxrumo.entity.User;

public interface UserService {

	public User login(String username,String password);
	
	public Map<String,Object> register(User user);
	
	public User getById(Integer id);
	
	public User findByUsername(String username); 
	
	public boolean isUsernameExist(String username);
	
	public boolean isEmailExist(String email);
	
	public boolean isNicknameExist(String nickname);
}
