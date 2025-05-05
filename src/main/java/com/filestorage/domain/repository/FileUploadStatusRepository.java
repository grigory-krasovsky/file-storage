package com.filestorage.domain.repository;

import com.filestorage.domain.FileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileUploadStatusRepository extends JpaRepository<FileStatus, UUID> {
}
