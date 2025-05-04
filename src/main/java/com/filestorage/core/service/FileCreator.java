package com.filestorage.core.service;

import com.filestorage.adapter.dto.request.FileCreateRequest;
import com.filestorage.adapter.dto.response.FileLocationResponse;

public interface FileCreator {
    FileLocationResponse create(FileCreateRequest request);
}
