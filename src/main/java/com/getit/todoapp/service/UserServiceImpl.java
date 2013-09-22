package com.getit.todoapp.service;

import java.util.List;

import com.getit.todoapp.domain.Userinfo;

public class UserServiceImpl implements UserService {
	
	public List<Userinfo> findByUserName(String userName){
		
		return userRepository.findByUserName(userName);
	}
}
