package com.filestorage.core.exception;

import com.filestorage.core.exception.enums.ErrorType;

import java.util.UUID;

public class GrpcException extends FileStorageException {

    private UUID fileLocationId;
    public GrpcException(ErrorType errorType, String message, UUID id) {
        super(errorType, message);
        this.fileLocationId = id;
    }

    public static String GRPC_OPERATION_FAILED(UUID fileLocationId) {
        return String.format("Unable to transfer file with id %s via gRPC", fileLocationId);
    }
}
