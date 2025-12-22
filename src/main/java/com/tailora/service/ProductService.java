package com.tailora.service;

import java.util.List;

import com.tailora.dto.request.ProductDto;
import com.tailora.enums.Status;
import com.tailora.exception.ProductException;
import com.tailora.model.Product;

public interface ProductService {
	
	public Product createProduct(ProductDto dto) throws ProductException;
	
	public Product getProduct(Long id) throws ProductException;
	
	public Product updateProduct(ProductDto dto) throws ProductException;
	
	public List<Product> getAllProducts() throws ProductException;
	
	public List<Product> getAllProductsByBrand(Long id) throws ProductException;
	
	public List<Product> getAllProductsByCategory(Long id) throws ProductException;
	
	public Status deleteProduct(Long id) throws ProductException;
}
