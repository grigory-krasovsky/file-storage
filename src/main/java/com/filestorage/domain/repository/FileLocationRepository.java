package com.filestorage.domain.repository;

import com.filestorage.domain.entity.FileLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileLocationRepository extends JpaRepository<FileLocation, UUID> {

}
