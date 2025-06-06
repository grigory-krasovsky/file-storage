package com.filestorage.domain.repository;

import com.filestorage.domain.entity.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ErrorLogRepository extends JpaRepository<ErrorLog, UUID> {
}
