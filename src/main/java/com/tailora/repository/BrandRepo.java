package com.tailora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tailora.model.Brand;

public interface BrandRepo extends JpaRepository<Brand, Long>{

}
