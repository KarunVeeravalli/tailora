package com.tailora.model;

import com.tailora.util.CommonClass;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "metadata")
public class Metadata extends CommonClass{
	
	@Column(nullable = false)
    private Integer length = 0;
    
    @Column(nullable = false)
    private Integer width = 0;
    
//    @Column(nullable = false, precision = 10, scale = 2)
//    private Double weight = 0.0;
    
    @OneToOne(mappedBy = "metadata")
    private Product product;
}
