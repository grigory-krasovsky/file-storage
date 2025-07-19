package com.filestorage.core.service;

import com.filestorage.adapter.dto.FileMetadataDTO;
import com.filestorage.adapter.dto.request.FileAccessSaveRequest;
import com.filestorage.adapter.dto.request.FileLocationCreateRequest;
import com.filestorage.adapter.dto.response.FileLocationGetResponse;
import com.filestorage.domain.entity.FileLocation;
import com.filestorage.grpc.GrpcFileAccessSaveRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Contains all business rules about location
 */
public interface FileLocationManager {
    FileLocationGetResponse createFileLocation(FileLocationCreateRequest request);
    FileLocation beforeCreate(FileAccessSaveRequest request);
    FileLocation beforeCreate(GrpcFileAccessSaveRequest request);
    FileLocation afterSave(FileAccessSaveRequest request);
    FileLocation afterSave(GrpcFileAccessSaveRequest request);
    FileMetadataDTO getFileLocationWithMetadata(UUID id);
    Page<FileLocationGetResponse> getAllFileLocationWithMetadataWithStatusesPageable(Pageable pageable);
    FileLocation beforeUpdate(FileAccessSaveRequest request);
    FileLocation beforeDelete(UUID id);
    FileLocation afterDelete(UUID id);
    Boolean deletable(UUID id);
}
