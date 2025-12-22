package com.tailora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tailora.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long>{

}
