package com.tailora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tailora.model.UnAuthUser;

public interface UnAuthUserRepo extends JpaRepository<UnAuthUser, Long>{

	@Query(value = "select * from UN_AUTH_USER where email = :email", nativeQuery = true)
	UnAuthUser findByEmail(String email);
	
	Boolean existsByEmail(String email);
}
