package com.tailora.model;

import com.tailora.util.CommonClass;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductImages extends CommonClass {

	private String url;
	@Column(nullable = false)
	private Boolean isMain = false;
	@Column(nullable = false)
	private Integer index = 0;
	private String alt;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    

}
