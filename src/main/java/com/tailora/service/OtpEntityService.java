package com.tailora.service;

import com.tailora.enums.Status;
import com.tailora.exception.OtpEntityException;
import com.tailora.model.OtpEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface OtpEntityService {
	
	public void saveOtp(OtpEntity dto, HttpServletRequest request, HttpServletResponse response) throws OtpEntityException;
	
	public OtpEntity getLastOtp(String email, HttpServletRequest request, HttpServletResponse response) throws OtpEntityException;
	
	public Status checkOtp(OtpEntity dto , HttpServletRequest request, HttpServletResponse response) throws OtpEntityException;
}
