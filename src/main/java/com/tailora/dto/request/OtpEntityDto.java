package com.tailora.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpEntityDto extends CommonRequest{
	
	private String email;
	
	private String otp;
	
	private String description;
}
