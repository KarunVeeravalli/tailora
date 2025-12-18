package com.tailora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tailora.model.TempPassword;

import lombok.NonNull;

public interface TempPasswordRepo extends JpaRepository<TempPassword, Long>{

	TempPassword findByEmail(@NonNull String email);

}
