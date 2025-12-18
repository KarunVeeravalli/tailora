package com.tailora.service;

import java.util.List;

import com.tailora.dto.request.LoginRequest;
import com.tailora.dto.request.OtpEntityDto;
import com.tailora.dto.request.PasswordDto;
import com.tailora.dto.request.RequestDto;
import com.tailora.dto.request.SignupRequest;
import com.tailora.enums.Status;
import com.tailora.exception.EmailException;
import com.tailora.exception.OtpEntityException;
import com.tailora.exception.UnAuthUserException;
import com.tailora.exception.UserLoginProfileException;
import com.tailora.exception.UserProfileException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserLoginProfileService {

	
	public String register(SignupRequest signupRequest, HttpServletRequest request, HttpServletResponse response) throws UserLoginProfileException, UnAuthUserException, UserProfileException, EmailException;
	
	public Status verifyOtpForRegister(OtpEntityDto dto, HttpServletRequest request, HttpServletResponse response) throws  UserLoginProfileException, UnAuthUserException, UserProfileException, OtpEntityException;
	
	public String changePassword(PasswordDto dto, HttpServletRequest request, HttpServletResponse response) throws  UserLoginProfileException, UserProfileException, OtpEntityException, EmailException;
	
	public String login(LoginRequest loginRequest,HttpServletRequest request,HttpServletResponse response) throws UserLoginProfileException, UserProfileException, EmailException;
	
	public List<String> getAllUserNames( HttpServletRequest request, HttpServletResponse response) throws UserLoginProfileException;
	
	public Status verifyOtpForPasswordUpdate(OtpEntityDto dto, HttpServletRequest request, HttpServletResponse response) throws  UserLoginProfileException;
	
	public Status logout(HttpServletRequest request,HttpServletResponse response) throws UserLoginProfileException,UserProfileException;
	
	public Status deleteUserByEmail( RequestDto email,HttpServletRequest request,HttpServletResponse response) throws UserLoginProfileException,UserProfileException; 


}
