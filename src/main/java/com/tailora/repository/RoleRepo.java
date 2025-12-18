package com.tailora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tailora.enums.URole;
import com.tailora.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long>{

	Role findByName(URole roleSuperadmin);

}
