package com.tailora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tailora.model.UnAuthUser;

public interface UnAuthUserRepo extends JpaRepository<UnAuthUser, Long>{

}
