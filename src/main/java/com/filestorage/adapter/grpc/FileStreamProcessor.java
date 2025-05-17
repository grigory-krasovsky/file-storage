package com.filestorage.adapter.grpc;

import com.filestorage.grpc.GrpcFileAccessSaveRequest;
@FunctionalInterface
public interface FileStreamProcessor {
    void processFile(GrpcFileAccessSaveRequest request) throws Exception;
}