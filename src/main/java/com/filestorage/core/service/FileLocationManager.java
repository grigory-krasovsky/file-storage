package com.filestorage.core.service;

import com.filestorage.adapter.dto.request.FileAccessCreateRequest;
import com.filestorage.adapter.dto.request.FileLocationCreateRequest;
import com.filestorage.adapter.dto.response.FileLocationGetResponse;
import com.filestorage.domain.entity.FileLocation;

/**
 * Contains all business rules about location
 */
public interface FileLocationManager {
    FileLocationGetResponse createFileLocation(FileLocationCreateRequest request);
    FileLocation beforeCreate(FileAccessCreateRequest request);
    FileLocation afterCreate(FileAccessCreateRequest request);
}
