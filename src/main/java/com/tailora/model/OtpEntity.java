package com.tailora.model;

import com.tailora.dto.request.OtpEntityDto;
import com.tailora.util.CommonClass;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpEntity extends CommonClass {
	
	private String email;
	
	private String otp;
	
	private String description;
	
}
