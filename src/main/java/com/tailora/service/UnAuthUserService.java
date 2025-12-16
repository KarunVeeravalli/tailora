package com.tailora.service;

import com.tailora.dto.request.UserInputDto;
import com.tailora.exception.UnAuthUserException;

public interface UnAuthUserService {
	
	public String addUnAuthUser(UserInputDto dto) throws UnAuthUserException;
	
	public String editUnAuthUser(UserInputDto dto) throws UnAuthUserException;
	
	public String deleteUnAuthUser(UserInputDto dto) throws UnAuthUserException;
	
	public String getUnAuthUser(UserInputDto dto) throws UnAuthUserException;

}
