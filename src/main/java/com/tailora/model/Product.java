package com.tailora.model;

import java.util.HashSet;
import java.util.Set;

import com.tailora.util.CommonClass;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends CommonClass{
	
	private String productName;
	
	private String description;
	
	@Column(nullable = false)
	private Boolean isAvailable = true;
	
	private String shortDescription;
	
	@OneToMany(mappedBy = "product")
	@OrderBy("sortOrder ASC")
	private Set<ProductImages> images = new HashSet<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="category_id", nullable = false)
	private Category category;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="subcategory_id", nullable = false)
	private Category subCategory;
	
//	@Column(nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
	private Brand brand;
	
	@OneToOne()
    @JoinColumn(name = "metadata_id")
	private Metadata metadata;
}
