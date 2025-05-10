package com.filestorage.core.service;

import com.filestorage.adapter.dto.request.FileAccessCreateRequest;
import com.filestorage.domain.entity.FileLocation;

public interface FileAccessManager {
    void createFileAccess(FileAccessCreateRequest request);
}
