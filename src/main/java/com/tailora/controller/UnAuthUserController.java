package com.tailora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tailora.dto.request.UserInputDto;
import com.tailora.exception.UnAuthUserException;
import com.tailora.service.UnAuthUserService;

@RestController("/unauth")
public class UnAuthUserController {
	
	@Autowired
	private UnAuthUserService service;
	
	@GetMapping("/test")
	public String addUser() throws UnAuthUserException {
		UserInputDto dto = new UserInputDto();
		service.addUnAuthUser(dto);
		return "working";
	}
}
