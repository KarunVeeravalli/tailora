package com.tailora.serviceImpl;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tailora.dto.request.OtpEntityDto;
import com.tailora.enums.Status;
import com.tailora.exception.OtpEntityException;
import com.tailora.model.OtpEntity;
import com.tailora.repository.OtpEntityRepo;
import com.tailora.service.OtpEntityService;
import com.tailora.util.Helper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class OtpEntityServiceImpl implements OtpEntityService{
	
	@Autowired
	private Helper helper;
	
	@Autowired
	private OtpEntityRepo repo;
	
	private static final Logger logger = LogManager.getLogger(OtpEntityServiceImpl.class);

	@Override
	public void saveOtp(OtpEntity dto, HttpServletRequest request, HttpServletResponse response)
			throws OtpEntityException {
		logger.info("<------ OtpEntityServiceImpl : saveOtp (BEGIN) with request => {} ------>",dto);
		try {
			repo.save(dto);
			logger.info("<------ OtpEntityServiceImpl : saveOtp (END) ------>");
		} catch (Exception e) {
			logger.info("<------ OtpEntityServiceImpl : saveOtp (FAILED) ------>");
		}
	}

	@Override
	public OtpEntity getLastOtp(String email, HttpServletRequest request, HttpServletResponse response)
			throws OtpEntityException {
		OtpEntity otp = repo.getLastOtpByEmail(email);
		System.out.println(otp +" otp is");
		return otp;
	}

	@Override
	public Status checkOtp(OtpEntity dto, HttpServletRequest request, HttpServletResponse response)
			throws OtpEntityException {
		System.out.println(dto.getEmail());
		OtpEntity otp = repo.getLastOtpByEmail(dto.getEmail());
		System.out.println(otp);
		if(otp==null) {
			throw new OtpEntityException("There is no otp found with the email----> "+dto.getEmail());
		}else if(otp.getCreatedDateTime().plusMinutes(10).isBefore(LocalDateTime.now())) {
			throw new OtpEntityException("Otp Expired please try again");
		}else if(dto.getOtp().equals(otp.getOtp())) {
			return Status.SUCCESS;
		}
		return Status.FAILED;
	}

}
