package com.filestorage.domain.repository;

import com.filestorage.domain.entity.FileLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileLocationRepository extends JpaRepository<FileLocation, UUID> {
    List<FileLocation> findByParent(UUID uuid);
    Page<FileLocation> findAllByParentIsNull(Pageable pageable);
}
