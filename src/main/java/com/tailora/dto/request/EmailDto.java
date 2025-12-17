package com.tailora.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto extends CommonRequest{
	
	private String toEmail;
	
	private String body;
	
	private String subject;
	
}
