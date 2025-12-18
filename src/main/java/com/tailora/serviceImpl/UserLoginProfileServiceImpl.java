package com.tailora.serviceImpl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tailora.dto.request.EmailDto;
import com.tailora.dto.request.LoginRequest;
import com.tailora.dto.request.OtpEntityDto;
import com.tailora.dto.request.PasswordDto;
import com.tailora.dto.request.RequestDto;
import com.tailora.dto.request.SignupRequest;
import com.tailora.enums.Status;
import com.tailora.enums.URole;
import com.tailora.exception.EmailException;
import com.tailora.exception.OtpEntityException;
import com.tailora.exception.UnAuthUserException;
import com.tailora.exception.UserLoginProfileException;
import com.tailora.exception.UserProfileException;
import com.tailora.model.OtpEntity;
import com.tailora.model.Role;
import com.tailora.model.TempPassword;
import com.tailora.model.UnAuthUser;
import com.tailora.model.UserLoginProfile;
import com.tailora.model.UserProfile;
import com.tailora.repository.OtpEntityRepo;
import com.tailora.repository.RoleRepo;
import com.tailora.repository.TempPasswordRepo;
import com.tailora.repository.UserLoginProfileRepo;
import com.tailora.security.service.JwtUtils;
import com.tailora.security.service.TokenBlackList;
import com.tailora.service.EmailService;
import com.tailora.service.OtpEntityService;
import com.tailora.service.UnAuthUserService;
import com.tailora.service.UserLoginProfileService;
import com.tailora.service.UserProfileService;
import com.tailora.util.Helper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserLoginProfileServiceImpl implements UserLoginProfileService{
	
	@Autowired
	private UserLoginProfileRepo repo;
	
	@Autowired
	private UnAuthUserService unAuthUserService;
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private OtpEntityService otpEntityService;

	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private Helper helper;
	
	@Autowired
	private TempPasswordRepo tempPasswordRepo;
	
	@Autowired
	private OtpEntityRepo otpEntityRepo;
	
	@Autowired
	private TokenBlackList tokenBlackList;
	
	public static final Logger logger = LogManager.getLogger(UserLoginProfileServiceImpl.class);
	
	@Override
	public String register(SignupRequest signupRequest, HttpServletRequest request, HttpServletResponse response)
			throws UserLoginProfileException, UnAuthUserException, UserProfileException, EmailException {
		logger.info("<------ UserLoginProfileServiceImpl : register (BEGIN) with request => {} ------>",signupRequest);
		if(null!=repo.findByEmail(signupRequest.getEmail())) {
			throw new UserLoginProfileException("User Already exists with email---> "+signupRequest.getEmail());
		}
		if(null!=repo.findByUsername(signupRequest.getUsername())) {
			throw new UserLoginProfileException("User Already exists with User name---> "+signupRequest.getUsername());
		}
		if(null!=repo.findByMobileNumber(signupRequest.getMobilenumber())) {
			throw new UserLoginProfileException("User Already exists with Mobile Number---> "+signupRequest.getMobilenumber());
		}
		UnAuthUser user = unAuthUserService.getUser(signupRequest.getEmail(), request, response);
		EmailDto dto = new EmailDto();
		dto.setToEmail(signupRequest.getEmail());
		
		if(user!=null) {
			dto.setSubject("User ALready Added, Please re-try with this OTP");
			emailService.sendOtp(dto, request, response);
			logger.info("<------ UserLoginProfileServiceImpl : register (END) ----->");
			return "User Details Already Added please proceed with otp";
//			throw new UnAuthUserException("User Already registred please complete the registration by otp");
		}
		UnAuthUser unAuthUser = new UnAuthUser();
		unAuthUser.setEmail(signupRequest.getEmail());
		unAuthUser.setMobileNumber(signupRequest.getMobilenumber());
		unAuthUser.setPassword(encoder.encode(signupRequest.getPassword()));
		unAuthUser.setUsername(signupRequest.getUsername());
		unAuthUserService.saveUser(unAuthUser, request, response);
		dto.setSubject("One Time Password for your Regsistration");
		emailService.sendOtp(dto, request, response);
		logger.info("<------ UserLoginProfileServiceImpl : register (END) ----->");
		return "OTP sent Successfully";
	}

	@Override
	public Status verifyOtpForRegister(OtpEntityDto dto, HttpServletRequest request, HttpServletResponse response)
			throws UserLoginProfileException, UnAuthUserException, UserProfileException, OtpEntityException {
		UnAuthUser user = unAuthUserService.getUser(dto.getEmail(), request, response);
		logger.info("<------ UserLoginProfileServiceImpl : verifyOtpForRegister (BEGIN) with request => {} ------>",dto);
		if(user!=null) {
			OtpEntity entity = new OtpEntity();
			entity.setEmail(dto.getEmail());
			entity.setOtp(dto.getOtp());
			try {
				Status stat = otpEntityService.checkOtp(entity, request, response);
				if(stat.equals(Status.FAILED)) {
					throw new OtpEntityException("Invalid OTP , please try again");
				}
				UserLoginProfile profile = new UserLoginProfile();
				profile.setEmail(user.getEmail());
				profile.setMobileNumber(user.getMobileNumber());
				profile.setPassword(user.getPassword());
				profile.setUsername(user.getUsername());
				profile.setIsActive(true);
				Role role = roleRepo.findByName(URole.ROLE_USER);
				Set<Role> roles = new HashSet<>();
				roles.add(role);
				profile.setRoles(roles);
				repo.save(profile);
				
				UserProfile userProfile = new UserProfile();
				userProfile.setEmail(user.getEmail());
				userProfile.setMobileNumber(user.getMobileNumber());
//				userProfile.setPassword(user.getPassword());
				userProfile.setUsername(user.getUsername());
				userProfile.setRoles(roles);
				userProfile.setIsActive(true);
				userProfileService.addUser(userProfile, request, response);
				
				unAuthUserService.deleteUser(dto.getEmail(), request, response);
				
				EmailDto emailDto = new EmailDto();
				emailDto.setToEmail(dto.getEmail());
				emailDto.setBody("Hi [User's Name],\n"
						+ "\n"
						+ "Thanks for registering with [YourAppName]!\n"
						+ "\n"
						+ "Your account has been successfully created. You're now part of a growing community of [your app's purpose – e.g., learners, creators, explorers, etc.].\n"
						+ "\n"
						+ "Here’s what you can do next:\n"
						+ "- Log in and explore your dashboard.\n"
						+ "- Complete your profile to get personalized recommendations.\n"
						+ "- Start using [key feature].\n"
						+ "\n"
						+ "If you have any questions or feedback, we’re just a click away.\n"
						+ "\n"
						+ "Welcome aboard!  \n"
						+ "The [YourAppName] Team\n"
						+ "");
				emailDto.setSubject("Registration Successfull");
				emailService.sendEmail(emailDto, request, response);
				logger.info("<------ UserLoginProfileServiceImpl : verifyOtpForRegister (END) with response => {} ----->",Status.SUCCESS);
				return Status.SUCCESS;
			} catch (Exception e) {
				logger.info("<------ UserLoginProfileServiceImpl : verifyOtpForRegister (FAILED) ----->");
				throw new OtpEntityException("Otp Was invalid please try again after sometime");
			}
			
		}
		logger.info("<------ UserLoginProfileServiceImpl : verifyOtpForRegister (FAILED) ----->");
		throw new UnAuthUserException("User doesn't Registered with email ----> "+dto.getEmail());
	}

	@Override
	public String changePassword(PasswordDto dto, HttpServletRequest request, HttpServletResponse response)
			throws UserLoginProfileException, UserProfileException, OtpEntityException, EmailException {
		logger.info("<------ UserLoginProfileServiceImpl : changePassword (BEGIN) with request => {} ------>",dto);
		if(!helper.isUserExistsByEmail(dto.getEmail())) {
			throw new UserLoginProfileException("User Doesn't exists with email ----> "+dto.getEmail());
		}
		UserLoginProfile user = repo.findByEmail(dto.getEmail());
		if(encoder.matches(dto.getOldPassword(), user.getPassword())) {
			TempPassword tempPassword = new TempPassword(dto.getEmail(), encoder.encode(dto.getPassword()));
			tempPasswordRepo.save(tempPassword);
			EmailDto  emailDto = new EmailDto();
			emailDto.setSubject("OTP FOR PASSWORD CHANGE");
			emailService.sendOtp(emailDto, request, response);
			logger.info("<------ UserLoginProfileServiceImpl : changePassword (END) with response => {} ----->","OTP_SENT_SUCCESSFULLY");
			return "OTP SENT SUCCESSFULLY";
		}
		logger.info("<------ UserLoginProfileServiceImpl : changePassword (FAILED) ----->");
		throw new UserLoginProfileException("Password is invalid please try again");
	}

	@Override
	public String login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response)
			throws UserLoginProfileException, UserProfileException, EmailException {
		logger.info("<------ UserLoginProfileServiceImpl : login (BEGIN) with request => {} ------>",loginRequest);
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		EmailDto emailDto = new EmailDto();
		emailDto.setToEmail(loginRequest.getEmail());
		String body = "Dear Customer,\n" + "\n" + "\n" + "\n" + "\n" + "\n" + " "
				+ "There's been a new sign-in to your XXXX Application account associated with the email address "
				+ "\n" + loginRequest.getEmail() + ",  on " + LocalDateTime.now() + ".\n" + "\n"
				+ "If this wasn't you, you need to change your E-com Application account password to protect your account."
				+ "\n" + "\n" + "http://localhost:8080/api/auth/changePassword to change password"

				+ "In case you have any queries / clarifications, please call us at our Customer Service number :\n"
				+ "\n" + "8340018900\n" + "8340018900\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n"
				+ " Thank You For Using our E-com Application :) ";
		emailDto.setBody(body);
		emailDto.setSubject("Login Successfull");
		emailService.sendEmail(emailDto, request, response);
		logger.info("<------ UserLoginProfileServiceImpl : login (END) with response => {} -----> ",jwt);
		return jwt;
	}

	@Override
	public List<String> getAllUserNames(HttpServletRequest request, HttpServletResponse response)
			throws UserLoginProfileException {
		logger.info("<------ UserLoginProfileServiceImpl : getAllUserNames (BEGIN) ------>");
		List<UserLoginProfile> users = repo.findAll();
		List<String> names = users.stream().map(UserLoginProfile::getUsername).collect(Collectors.toList());
		logger.info("<------ UserLoginProfileServiceImpl : getAllUserNames (END) with response => {} ----->",names);
		return names ;
	}

	@Override
	public Status verifyOtpForPasswordUpdate(OtpEntityDto dto, HttpServletRequest request, HttpServletResponse response)
			throws UserLoginProfileException {
		logger.info("<------ UserLoginProfileServiceImpl : verifyOtpForPasswordUpdate (BEGIN) with request => {} ------>",dto);
		if(!helper.isUserExistsByEmail(dto.getEmail())) {
			logger.info("<------ UserLoginProfileServiceImpl : verifyOtpForPasswordUpdate (FAILED) ------>");
			throw new UserLoginProfileException("User Doesn't exists with email ----> "+dto.getEmail());
		}
		OtpEntity otp = otpEntityRepo.getotpForPasswordChange(dto.getEmail());
		TempPassword pass = tempPasswordRepo.findByEmail(dto.getEmail());
		if (otp==null) {
			logger.info("<------ UserLoginProfileServiceImpl : verifyOtpForPasswordUpdate (FAILED) ------>");
			throw new UserLoginProfileException("No OTP found , please try again");
		}
		else if(pass==null) {
			logger.info("<------ UserLoginProfileServiceImpl : verifyOtpForPasswordUpdate (FAILED) ------>");
			throw new UserLoginProfileException("No Record found , please try again");
		}
		else if(otp.getOtp().equals(dto.getOtp())) {
			UserLoginProfile user = repo.findByEmail(dto.getEmail());
			user.setPassword(pass.getPassword());
			logger.info("<------ UserLoginProfileServiceImpl : verifyOtpForPasswordUpdate (END) with response => {} ----->","SUCCESS");
			return Status.SUCCESS;
		}
		logger.info("<------ UserLoginProfileServiceImpl : verifyOtpForPasswordUpdate (FAILED) ------>");
		throw new UserLoginProfileException("OTP is invalid");
	}

	@Override
	public Status logout(HttpServletRequest request, HttpServletResponse response)
			throws UserLoginProfileException, UserProfileException {
		logger.info("<------ UserLoginProfileServiceImpl : logout (BEGIN) ------>");
		 String jwt = helper.parseJwt(request);
	      tokenBlackList.addToBlacklist(jwt);
	      logger.info("<------ UserLoginProfileServiceImpl : logout (END) ------>");
	      return Status.LOGGED_OUT_SUCCESSFULLY;
	}

	@Override
	@Transactional
	public Status deleteUserByEmail(RequestDto dto, HttpServletRequest request, HttpServletResponse response)
			throws UserLoginProfileException, UserProfileException {
		//this is for temp
		logger.info("<------ UserLoginProfileServiceImpl : deleteUserByEmail (BEGIN) with request => {} ------>",dto);
		repo.deleteByEmail(dto.getEmail());
		logger.info("<------ UserLoginProfileServiceImpl : deleteUserByEmail (END) ------>");
		return Status.SUCCESS;
	}
	

}
