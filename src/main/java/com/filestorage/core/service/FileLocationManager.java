package com.filestorage.core.service;

import com.filestorage.adapter.dto.FileMetadataDTO;
import com.filestorage.adapter.dto.request.FileAccessSaveRequest;
import com.filestorage.adapter.dto.request.FileLocationCreateRequest;
import com.filestorage.adapter.dto.response.FileLocationGetResponse;
import com.filestorage.domain.entity.FileLocation;
import com.filestorage.grpc.GrpcFileAccessSaveRequest;

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
    FileLocation beforeUpdate(FileAccessSaveRequest request);
}
