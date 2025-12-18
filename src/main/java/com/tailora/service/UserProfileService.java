package com.tailora.service;

import com.tailora.enums.Status;
import com.tailora.exception.UserLoginProfileException;
import com.tailora.exception.UserProfileException;
import com.tailora.model.UserProfile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserProfileService {
	
	public Status addUser(UserProfile user, HttpServletRequest request, HttpServletResponse response) throws UserProfileException, UserLoginProfileException;
	
	public Status updateUser(UserProfile user, HttpServletRequest request, HttpServletResponse response) throws UserProfileException;
	
	public Status deleteUserByEmail(String email, HttpServletRequest request, HttpServletResponse response) throws UserProfileException;
	
	public UserProfile getUserByEmail(String email, HttpServletRequest request, HttpServletResponse response) throws UserProfileException;
}
