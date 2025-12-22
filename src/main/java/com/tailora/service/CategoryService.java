package com.tailora.service;

import java.util.List;

import com.tailora.exception.CategoryException;
import com.tailora.model.Category;

public interface CategoryService {
	
	public List<Category> getMainCategories() throws CategoryException;
	
	public Category findByPrmimaryCategoryId(Long id) throws CategoryException;
	
	public Category findMainCategoryBySub(Long id) throws CategoryException;
	
	public Category createCategory(Category category) throws CategoryException;
	
	public String deleteMainCategory(Long id) throws CategoryException;
	
	public String deleteSubCategory(Long id) throws CategoryException;
}
