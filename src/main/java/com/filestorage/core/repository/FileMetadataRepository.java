package com.filestorage.core.repository;

import com.filestorage.domain.FileLocation;
import com.filestorage.domain.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadata, UUID> {
}
