package com.filestorage.core.service;

import com.filestorage.adapter.dto.request.FileAccessSaveRequest;
import com.filestorage.adapter.dto.request.FileAccessGetRequest;
import com.filestorage.adapter.dto.response.FileAccessGetResponse;
import com.filestorage.grpc.GrpcFileAccessSaveRequest;

public interface FileAccessManager {
    void createFileAccess(FileAccessSaveRequest request);
    void createFileAccess(GrpcFileAccessSaveRequest request);
    void updateFileAccess(FileAccessSaveRequest request);
    FileAccessGetResponse getFileAccess(FileAccessGetRequest request);
}
