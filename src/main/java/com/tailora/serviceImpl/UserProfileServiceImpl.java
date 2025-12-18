package com.tailora.serviceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tailora.enums.Status;
import com.tailora.exception.UserLoginProfileException;
import com.tailora.exception.UserProfileException;
import com.tailora.model.UserProfile;
import com.tailora.repository.UserProfileRepo;
import com.tailora.service.UserProfileService;
import com.tailora.util.Helper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserProfileServiceImpl implements UserProfileService{
	
	@Autowired
	private UserProfileRepo repo;
	
	@Autowired
	private Helper helper;
	
	public static final Logger logger = LogManager.getLogger(UserProfileServiceImpl.class);

	@Override
	public Status addUser(UserProfile user, HttpServletRequest request, HttpServletResponse response)
			throws UserProfileException, UserLoginProfileException {
		logger.info("<------ UserProfileServiceImpl : addUser (BEGIN) ------>");
		repo.save(user);
		logger.info("<------ UserProfileServiceImpl : addUser (END) ------>");
		return Status.SUCCESS;
	}

	@Override
	public Status updateUser(UserProfile user, HttpServletRequest request, HttpServletResponse response)
			throws UserProfileException {
		logger.info("<------ UserProfileServiceImpl : updateUser (BEGIN) ------>");
		UserProfile user1 = repo.findByEmail(user.getEmail());
		BeanUtils.copyProperties(user, user1, helper.getNullPropertyNames(user));
		repo.save(user1);
		logger.info("<------ UserProfileServiceImpl : updateUser (END) ------>");
		return Status.SUCCESS;
	}

	@Override
	public Status deleteUserByEmail(String email, HttpServletRequest request, HttpServletResponse response)
			throws UserProfileException {
		logger.info("<------ UserProfileServiceImpl : deleteUserByEmail (BEGIN) ------>");
		repo.deleteByEmail(email);
		logger.info("<------ UserProfileServiceImpl : deleteUserByEmail (END) ------>");
		return Status.SUCCESS;
	}

	@Override
	public UserProfile getUserByEmail(String email, HttpServletRequest request, HttpServletResponse response)
			throws UserProfileException {
		logger.info("<------ UserProfileServiceImpl : getUserByEmail ------>");
		return repo.findByEmail(email);
	}


}
