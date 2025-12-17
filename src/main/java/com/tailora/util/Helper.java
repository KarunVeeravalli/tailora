package com.tailora.util;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tailora.dto.request.UserInputDto;
import com.tailora.model.UnAuthUser;

@Component
public class Helper {
	
	 @Value("${tailora.app.sendermail}")
	  private String fromEmail;
	
	 public String getMail() {
		 return fromEmail;
	 }
	 
	public String getRequestLogger(Class<?> className,String method, Object request , String reqId) {
		return ("<------ REQ-ID: "+reqId+"  "+className.getName()+": "+method+" (BEGINS) with request => {"+ request+" } ------>");
	}
	
	public String getResponseLogger(Class<?> className,String method, Object response, String reqId ) {
		return ("<------ REQ-ID: "+reqId+"  "+className.getName()+": "+method+" (ENDS) with response => {"+ response+" } ------>");
	}
	
	public UnAuthUser getUnAuthUserByDto(UserInputDto dto) {
		UnAuthUser user = new UnAuthUser();
		user.setEmail(dto.getEmail());
		user.setMobileNumber(dto.getMobileNumber());
		user.setPassword(dto.getPassword());
		user.setUsername(dto.getUsername());
		user.setCreatedTime(LocalDateTime.now());
		return user;
	}
	
	public String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}

		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}
	
	public String getOtp() {
		Integer upper = 999999;
		Integer lower = 111111;
		Integer otp = (int) (Math.random() * (upper - lower)) + lower;
		return otp.toString();
	}

}
