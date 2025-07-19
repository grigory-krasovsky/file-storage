package com.filestorage.core.service;

import com.filestorage.adapter.dto.request.FileAccessBatchDeleteRequest;
import com.filestorage.adapter.dto.request.FileAccessDeleteRequest;
import com.filestorage.adapter.dto.request.FileAccessSaveRequest;
import com.filestorage.adapter.dto.request.FileAccessGetRequest;
import com.filestorage.adapter.dto.response.FileAccessGetResponse;
import com.filestorage.grpc.GrpcFileAccessSaveRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileAccessManager {
    void createFileAccess(FileAccessSaveRequest request);
    void createFileAccess(GrpcFileAccessSaveRequest request);
    void updateFileAccess(FileAccessSaveRequest request);
    FileAccessGetResponse getFileAccess(FileAccessGetRequest request);
    Boolean deleteFileAccess(UUID id);
    List<UUID> deleteFileAccess(FileAccessBatchDeleteRequest request);
    Optional<byte[]> getPreview(UUID parentUuid);
}
