package com.tailora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tailora.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Long>{

	@Query("SELECT c FROM Category c WHERE c.parentCategory IS NULL AND c.level = 0 AND c.isActive = true ORDER BY c.displayOrder")
	List<Category> findAllMainCategories();

	@Query("SELECT c.parentCategory FROM Category c WHERE c.id = :id")
	Category findMainCategoryBySub(@Param("id")Long id);

}
