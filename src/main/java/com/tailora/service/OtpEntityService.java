package com.tailora.service;

import com.tailora.dto.request.OtpEntityDto;
import com.tailora.exception.OtpEntityException;
import com.tailora.model.OtpEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface OtpEntityService {
	
	public void saveOtp(OtpEntityDto dto, HttpServletRequest request, HttpServletResponse response) throws OtpEntityException;
	
	public OtpEntity getLastOtp(String email, HttpServletRequest request, HttpServletResponse response) throws OtpEntityException;
	
	public String checkOtp(OtpEntityDto dto , HttpServletRequest request, HttpServletResponse response) throws OtpEntityException;
}
