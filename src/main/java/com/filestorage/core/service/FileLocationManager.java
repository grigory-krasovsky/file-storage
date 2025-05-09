package com.filestorage.core.service;

import com.filestorage.adapter.dto.request.FileLocationCreateRequest;
import com.filestorage.adapter.dto.response.FileLocationGetResponse;

/**
 * Contains all business rules about location
 */
public interface FileLocationManager {
    FileLocationGetResponse createFileLocation(FileLocationCreateRequest request);
}
