package com.getit.todoapp.service;

import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;

import com.getit.todoapp.domain.Userinfo;

@RooService(domainTypes = { com.getit.todoapp.domain.Userinfo.class })
public interface UserService {

	List<Userinfo> findByUserName(String userName);

	public abstract long countAllUserinfoes();


	public abstract void deleteUserinfo(Userinfo userinfo);


	public abstract Userinfo findUserinfo(Long id);


	public abstract List<Userinfo> findAllUserinfoes();


	public abstract List<Userinfo> findUserinfoEntries(int firstResult, int maxResults);


	public abstract void saveUserinfo(Userinfo userinfo);


	public abstract Userinfo updateUserinfo(Userinfo userinfo);

}
