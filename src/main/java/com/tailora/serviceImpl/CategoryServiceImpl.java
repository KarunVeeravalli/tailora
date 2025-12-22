package com.tailora.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tailora.exception.CategoryException;
import com.tailora.model.Category;
import com.tailora.repository.CategoryRepo;
import com.tailora.service.CategoryService;
import com.tailora.util.Helper;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private Helper helper;
	
	@Autowired
	private CategoryRepo repo;

	@Override
	public List<Category> getMainCategories() throws CategoryException {
		
		return repo.findAllMainCategories();
	}

	@Override
	public Category findByPrmimaryCategoryId(Long id) throws CategoryException {
		return repo.findById(id).get();
	}

	@Override
	public Category findMainCategoryBySub(Long id) throws CategoryException {
		// TODO Auto-generated method stub
		return repo.findMainCategoryBySub(id);
	}

	@Override
	public Category createCategory(Category category) throws CategoryException {
		
		return null;
	}

	@Override
	public String deleteMainCategory(Long id) throws CategoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteSubCategory(Long id) throws CategoryException {
		// TODO Auto-generated method stub
		return null;
	}

}
