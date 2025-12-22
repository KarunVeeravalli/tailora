package com.tailora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tailora.model.Metadata;

public interface MetadataRepo extends JpaRepository<Metadata, Long>{

}
