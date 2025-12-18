package com.tailora.serviceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tailora.exception.UnAuthUserException;
import com.tailora.exception.UserProfileException;
import com.tailora.model.UnAuthUser;
import com.tailora.repository.UnAuthUserRepo;
import com.tailora.service.UnAuthUserService;
import com.tailora.util.Helper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UnAuthUserServiceImpl implements UnAuthUserService {
	
	@Autowired
	private UnAuthUserRepo repo;
	
	@Autowired
	private Helper helper;
	
	
	@Override
	public UnAuthUser saveUser(UnAuthUser user, HttpServletRequest request, HttpServletResponse response)
			throws UnAuthUserException, UserProfileException {
		return repo.save(user);
	}

	@Override
	public String deleteUser(String email, HttpServletRequest request, HttpServletResponse response)
			throws UnAuthUserException, UserProfileException {
			UnAuthUser user = repo.findByEmail(email);
//			if(user==null) {
//				throw new UnAuthUserException("User not found, Please Register first to continue");
//			}
			repo.deleteById(user.getId());
		return "SUCCESS";
	}

	@Override
	public UnAuthUser getUser(String email, HttpServletRequest request, HttpServletResponse response)
			throws UnAuthUserException {
		UnAuthUser user = repo.findByEmail(email);
//		if(user==null) {
//			throw new UnAuthUserException("User not found, Please Register first to continue");
//		}
		return user;
	}

	@Override
	public UnAuthUser updateUser(UnAuthUser user, HttpServletRequest request, HttpServletResponse response)
			throws UnAuthUserException {
		UnAuthUser user1 = repo.findByEmail(user.getEmail());
//		if(user1==null) {
//			throw new UnAuthUserException("User not found, Please Register first to continue");
//		}
		BeanUtils.copyProperties(user, user1, helper.getNullPropertyNames(user));
		return repo.save(user1);
	}

}
