package com.tailora.serviceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tailora.constants.CommonConstants;
import com.tailora.dto.request.UserInputDto;
import com.tailora.exception.UnAuthUserException;
import com.tailora.model.UnAuthUser;
import com.tailora.repository.UnAuthUserRepo;
import com.tailora.service.UnAuthUserService;
import com.tailora.util.Helper;

@Service
public class UnAuthUserServiceImpl implements UnAuthUserService{
	
	@Autowired
	private Helper helper;
	
	@Autowired
	private UnAuthUserRepo repo;
	

	@Autowired
	private CommonConstants constants;
	
	public static final Logger logger = LogManager.getLogger(UnAuthUserServiceImpl.class);

	@Override
	public String addUnAuthUser(UserInputDto dto) throws UnAuthUserException {
		logger.info(helper.getRequestLogger(this.getClass(),"addUnAuthUser" , dto, dto.getRequestHeader().getTrackingId()));
		try {
			UnAuthUser user = helper.getUnAuthUserByDto(dto);
			user = repo.save(user);
			logger.info(helper.getResponseLogger(getClass(), "addUnAuthUser", user, dto.getRequestHeader().getTrackingId()));
			return CommonConstants.UNUCS;
		} catch (Exception e) {
			logger.info("REQ-ID: {} Exception Occured ---> {}",dto.getRequestHeader().getTrackingId(), e.getMessage());
		}
		return CommonConstants.UNUCF;
	}

	@Override
	public String editUnAuthUser(UserInputDto dto) throws UnAuthUserException {
		// TODO Auto-generated method stub
//		BeanUtils.copyProperties(user, user1, helper.getNullPropertyNames(user));
		return null;
	}

	@Override
	public String deleteUnAuthUser(UserInputDto dto) throws UnAuthUserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUnAuthUser(UserInputDto dto) throws UnAuthUserException {
		// TODO Auto-generated method stub
		return null;
	}

}
