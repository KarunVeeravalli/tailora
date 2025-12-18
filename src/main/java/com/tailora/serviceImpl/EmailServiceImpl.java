package com.tailora.serviceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tailora.dto.request.EmailDto;
import com.tailora.exception.EmailException;
import com.tailora.exception.OtpEntityException;
import com.tailora.model.Email;
import com.tailora.model.OtpEntity;
import com.tailora.repository.EmailRepo;
import com.tailora.service.EmailService;
import com.tailora.service.OtpEntityService;
import com.tailora.util.Helper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class EmailServiceImpl implements EmailService {
	
	private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class);
	
	@Autowired
	private EmailRepo repo;
	
	@Autowired
	private Helper helper;
	
	@Autowired
	private OtpEntityService otpEntityService;
	
	@Autowired
	private JavaMailSender sender;
	
	@Override
	public String sendOtp(EmailDto email, HttpServletRequest request, HttpServletResponse response)
			throws EmailException {
		logger.info("<------ EmailServiceImpl : sendOtp (BEGIN) with request => {} ------>",email);
		String otp = helper.getOtp();
		String body = "Hello email,\n"
				+ "\n"
				+ "Thank you for choosing Our Company.\n"
				+ "\n"
				+ "To complete your registration, please use the One-Time Password (OTP) below:\n"
				+ "\n"
				+ "                 "+otp+"\n"
				+ "\n"
				+ "This OTP is valid for 10 minutes. Please do not share it with anyone.\n"
				+ "\n"
				+ "If you did not request this, please ignore this email.\n"
				+ "\n"
				+ "Best regards,  \n"
				+ "Our Company Team  \n"
				+ "\n"
				+ "-----------------------------  \n"
				+ "Contact us: kkarun701@gmail.com\n"
				+ "Website: [Your Website URL]  \n"
				+ "";
		Email mail = new Email();
		mail.setBody(body);
		mail.setFromEmail(helper.getMail());
		mail.setSubject(email.getSubject());
		mail.setToEmail(email.getToEmail());
		repo.save(mail);
		logger.info("<------ EmailServiceImpl : sendOtp otp => {} ------>",otp);
		OtpEntity otpEntity = new OtpEntity();
		otpEntity.setDescription(email.getSubject());
		otpEntity.setEmail(email.getToEmail());
		otpEntity.setOtp(otp);
//		otpEntity.setRequestHeader(email.getRequestHeader());
		try {
			otpEntityService.saveOtp(otpEntity, request, response);
		} catch (OtpEntityException e) {
			logger.info("<------ EmailServiceImpl : sendOtp (FAILED) ------>");
			e.printStackTrace();
		}
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(helper.getMail());
		message.setText(body);
		message.setSubject(mail.getSubject());
		message.setTo(email.getToEmail());
		sender.send(message);
		logger.info("<------ EmailServiceImpl : sendOtp (SUCCESS) ------>");
		return "SUCCESS";
	}

	@Override
	public String sendEmail(EmailDto email, HttpServletRequest request, HttpServletResponse response)
			throws  EmailException {
		logger.info("<------ EmailServiceImpl : sendOtp (BEGIN) with request => {} ------>",email);
		Email mail = new Email();
		mail.setBody(email.getBody());
		mail.setFromEmail(helper.getMail());
		mail.setSubject(email.getSubject());
		mail.setToEmail(email.getToEmail());
		repo.save(mail);	
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(helper.getMail());
		message.setText(email.getBody());
		message.setSubject(email.getSubject());
		message.setTo(email.getToEmail());
		sender.send(message);		
		logger.info("<------ EmailServiceImpl : sendOtp (SUCCESS) ------>");
		return "SUCCESS";
	}

}
