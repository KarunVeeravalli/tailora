package com.tailora.dto.request;

import lombok.Data;

@Data
public class UserInputDto extends CommonRequest {
	
	private String username;
	private String email;
	private String password;
	private String mobileNumber;
}
