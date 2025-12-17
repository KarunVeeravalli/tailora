package com.tailora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tailora.model.Email;

public interface EmailRepo extends JpaRepository<Email, Long>{

}
