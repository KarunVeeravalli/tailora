package com.tailora.model;

import com.tailora.util.CommonClass;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Brand extends CommonClass{
	
	private String name;
	private String description;
	private String shortDescription;
	@Column(nullable = false)
	private Boolean isNew = false;
	@Column(nullable = false)
	private Boolean isTrending = false;
	
	@Column(nullable = false)
	private String logo;
	@Column(nullable = false)
	private String website;
	
}
