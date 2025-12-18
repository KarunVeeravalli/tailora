package com.tailora.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tailora.model.UserLoginProfile;
import com.tailora.repository.UserLoginProfileRepo;

@Service
public class UserDetailsImplService implements UserDetailsService{
	
	@Autowired
	private UserLoginProfileRepo loginProfileRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserLoginProfile user = new UserLoginProfile();
		if(username.contains("@gmail.com")) {
			user = loginProfileRepo.findByEmail(username);
		}else {
			user = loginProfileRepo.findByUsername(username);
		}
		if(user.getIsActive().equals(false)) {
			throw new UsernameNotFoundException("User Profile got Deactivated Please contact support team for re Activating");
		}
		
		if(user==null) {
			throw new UsernameNotFoundException("User not found with the input ----> "+username);
		}

		return UserDetailsImpl.build(user);
	}

	
}
