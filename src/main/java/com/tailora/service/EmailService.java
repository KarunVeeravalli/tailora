package com.tailora.service;

import com.tailora.dto.request.EmailDto;
import com.tailora.exception.EmailException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface EmailService {
	
	public String sendOtp(EmailDto email, HttpServletRequest request, HttpServletResponse response) throws  EmailException;
	
	public String sendEmail(EmailDto email, HttpServletRequest request, HttpServletResponse response)throws  EmailException;

}
