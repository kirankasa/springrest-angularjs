package com.getit.todoapp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.getit.todoapp.domain.Userinfo;

public class CustomUserDeatilService implements UserDetailsService {

	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_ADMINISTRATOR = "ROLE_ADMIN";

	@Autowired
	private UserService userService;

	private final ThreadLocal<User> currentUser = new ThreadLocal<User>();

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		try {

			Collection<GrantedAuthority> userAuthorities = new ArrayList<GrantedAuthority>();
			userAuthorities.add(new SimpleGrantedAuthority(ROLE_USER));

			List<Userinfo> userinfos = userService.findByUserName(username);

			Userinfo userinfo = userinfos.get(0);

			User user = new User(userinfo.getUserName(),
					userinfo.getPassword(), true, true, true, true,
					userAuthorities);
			currentUser.set(user);
			return user;

		} catch (Exception e) {
			throw new UsernameNotFoundException("Username " + username
					+ " not found!");
		}

	}
}