package com.tailora.service;

import com.tailora.dto.request.UserInputDto;
import com.tailora.exception.UnAuthUserException;
import com.tailora.model.UnAuthUser;

public interface UnAuthUserService {
	
	public String addUnAuthUser(UserInputDto dto) throws UnAuthUserException;
	
	public String editUnAuthUser(UnAuthUser dto) throws UnAuthUserException;
	
	public String deleteUnAuthUser(UserInputDto dto) throws UnAuthUserException;
	
	public UnAuthUser getUnAuthUser(UserInputDto dto) throws UnAuthUserException;

}
