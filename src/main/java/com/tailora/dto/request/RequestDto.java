package com.tailora.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto extends CommonRequest{
	
	private String email;
	
	private String description;
}
