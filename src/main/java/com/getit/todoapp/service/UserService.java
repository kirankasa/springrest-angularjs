package com.getit.todoapp.service;

import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;

import com.getit.todoapp.domain.Userinfo;

@RooService(domainTypes = { com.getit.todoapp.domain.Userinfo.class })
public interface UserService {

	List<Userinfo> findByUserName(String userName);
}
