package com.tailora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tailora.model.UserLoginProfile;

public interface UserLoginProfileRepo extends JpaRepository<UserLoginProfile, Long>{

	UserLoginProfile findByEmail(String username);

	UserLoginProfile findByUsername(String username);

	UserLoginProfile findByMobileNumber(String number);

	void deleteByEmail(String email);

}
