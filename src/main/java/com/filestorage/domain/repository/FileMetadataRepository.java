package com.filestorage.domain.repository;

import com.filestorage.domain.entity.FileLocation;
import com.filestorage.domain.entity.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadata, UUID> {
    Optional<FileMetadata> findByFileLocationAndRelevant(FileLocation fileLocation, Boolean relevant);
    List<FileMetadata> findByFileLocationInAndRelevant(List<FileLocation> fileLocations, Boolean relevant);
}
