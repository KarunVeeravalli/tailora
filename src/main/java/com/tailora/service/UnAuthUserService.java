package com.tailora.service;

import com.tailora.exception.UnAuthUserException;
import com.tailora.exception.UserProfileException;
import com.tailora.model.UnAuthUser;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UnAuthUserService {
	
	public UnAuthUser saveUser(UnAuthUser user, HttpServletRequest request, HttpServletResponse response) throws UnAuthUserException, UserProfileException;
	
	public String deleteUser(String email, HttpServletRequest request, HttpServletResponse response) throws UnAuthUserException, UserProfileException;
	
	public UnAuthUser getUser(String email, HttpServletRequest request, HttpServletResponse response) throws UnAuthUserException;
	
	public UnAuthUser updateUser(UnAuthUser user, HttpServletRequest request, HttpServletResponse response) throws UnAuthUserException;

}
