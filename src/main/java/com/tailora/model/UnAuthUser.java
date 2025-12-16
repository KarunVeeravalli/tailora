package com.tailora.model;

import com.tailora.util.CommonClass;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnAuthUser extends CommonClass{
	
	private String username;
	
	private String email;
	
	private String mobileNumber;
	
	private String password;
}
