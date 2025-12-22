package com.tailora.model;

import java.util.HashSet;
import java.util.Set;

import com.tailora.util.CommonClass;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category extends CommonClass {

	@Column(nullable = false)
	private String name;
	
	private String description;
	
	@Column(nullable = false)
	private Integer level = 0; //0:main, 1:sub, 2:child
	
	@Column(nullable = false)
	private Boolean isActive = true;
	
	@Column(nullable = false)
	private Integer displayOrder = 0;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;
	
	@OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Category> subcategories = new HashSet<>();
}
