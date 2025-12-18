package com.tailora.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest extends CommonRequest{
	
	@NotBlank
	private String email;

	@NotBlank
	private String password;

}
