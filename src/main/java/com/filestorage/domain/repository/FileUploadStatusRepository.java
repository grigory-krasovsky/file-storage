package com.filestorage.domain.repository;

import com.filestorage.domain.entity.FileLocation;
import com.filestorage.domain.entity.FileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileUploadStatusRepository extends JpaRepository<FileStatus, UUID> {
    List<FileStatus> findAllByFileLocation(FileLocation fileLocation);
}
