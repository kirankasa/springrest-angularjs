package com.getit.todoapp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.getit.todoapp.domain.Userinfo;
import com.getit.todoapp.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

	public List<Userinfo> findByUserName(String userName){
		
		return userRepository.findByUserName(userName);
	}

	public long countAllUserinfoes() {
        return userRepository.count();
    }

	public void deleteUserinfo(Userinfo userinfo) {
        userRepository.delete(userinfo);
    }

	public Userinfo findUserinfo(Long id) {
        return userRepository.findOne(id);
    }

	public List<Userinfo> findAllUserinfoes() {
        return userRepository.findAll();
    }

	public List<Userinfo> findUserinfoEntries(int firstResult, int maxResults) {
        return userRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveUserinfo(Userinfo userinfo) {
        userRepository.save(userinfo);
    }

	public Userinfo updateUserinfo(Userinfo userinfo) {
        return userRepository.save(userinfo);
    }
}
