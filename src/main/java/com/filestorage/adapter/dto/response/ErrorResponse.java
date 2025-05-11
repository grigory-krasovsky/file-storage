package com.filestorage.adapter.dto.response;

import com.filestorage.core.exception.FileStorageException;
import com.filestorage.core.exception.enums.ErrorType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record ErrorResponse(
        OffsetDateTime timestamp,
        ErrorType type,
        String message,
        String path) {
    public static ErrorResponse fromException(FileStorageException ex) {
        return ErrorResponse
                .builder()
                .timestamp(OffsetDateTime.now())
                .type(ex.getErrorType())
                .message(ex.getMessage())
                .build();
    }

    public static ErrorResponse fromException(String message) {
        return ErrorResponse
                .builder()
                .timestamp(OffsetDateTime.now())
                .type(ErrorType.SYSTEM_ERROR)
                .message(message)
                .build();
    }
}