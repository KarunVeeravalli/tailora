package com.tailora.serviceImpl;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tailora.dto.request.OtpEntityDto;
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
	public void saveOtp(OtpEntityDto dto, HttpServletRequest request, HttpServletResponse response)
			throws OtpEntityException {
//		logger.info(helper.getRequestLogger(getClass(), "saveOtp", dto, dto.getRequestHeader().getTrackingId()));
		try {
			OtpEntity otp = new OtpEntity();
			otp.setEmail(dto.getEmail());
			otp.setOtp(dto.getOtp());
			otp.setOtp(dto.getDescription());
			repo.save(otp);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
//		logger.info(helper.getRequestLogger(getClass(), "saveOtp", dto, dto.getRequestHeader().getTrackingId()));
	}

	@Override
	public OtpEntity getLastOtp(String email, HttpServletRequest request, HttpServletResponse response)
			throws OtpEntityException {
		OtpEntity otp = repo.getLastOtpByEmail(email);
		return otp;
	}

	@Override
	public String checkOtp(OtpEntityDto dto, HttpServletRequest request, HttpServletResponse response)
			throws OtpEntityException {
		OtpEntity otp = repo.getLastOtpByEmail(dto.getEmail());
		if(otp==null) {
			throw new OtpEntityException("There is no otp found with the email----> "+dto.getEmail());
		}else if(otp.getCreatedTime().plusMinutes(10).isBefore(LocalDateTime.now())) {
			throw new OtpEntityException("Otp Expired please try again");
		}else if(dto.getOtp().equals(otp.getOtp())) {
			return "SUCCESS";
		}
		return "FAILED";
	}

}
