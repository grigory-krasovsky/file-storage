package com.filestorage.core.service;

import com.filestorage.adapter.dto.FileMetadataDTO;
import com.filestorage.adapter.dto.request.FileAccessCreateRequest;
import com.filestorage.adapter.dto.request.FileLocationCreateRequest;
import com.filestorage.adapter.dto.response.FileLocationGetResponse;
import com.filestorage.domain.entity.FileLocation;

import java.util.UUID;

/**
 * Contains all business rules about location
 */
public interface FileLocationManager {
    FileLocationGetResponse createFileLocation(FileLocationCreateRequest request);
    FileLocation beforeCreate(FileAccessCreateRequest request);
    FileLocation afterCreate(FileAccessCreateRequest request);
    FileMetadataDTO getFileLocationWithMetadata(UUID id);
}
