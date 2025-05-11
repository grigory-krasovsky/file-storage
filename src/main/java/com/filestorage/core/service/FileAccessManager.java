package com.filestorage.core.service;

import com.filestorage.adapter.dto.request.FileAccessCreateRequest;
import com.filestorage.adapter.dto.request.FileAccessGetRequest;
import com.filestorage.adapter.dto.response.FileAccessGetResponse;
import com.filestorage.domain.entity.FileLocation;
import org.springframework.core.io.Resource;

public interface FileAccessManager {
    void createFileAccess(FileAccessCreateRequest request);
    FileAccessGetResponse getFileAccess(FileAccessGetRequest request);
}
