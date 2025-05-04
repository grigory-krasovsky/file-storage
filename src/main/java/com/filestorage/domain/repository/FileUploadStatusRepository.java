package com.filestorage.domain.repository;

import com.filestorage.domain.FileUploadStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileUploadStatusRepository extends JpaRepository<FileUploadStatus, UUID> {
}
