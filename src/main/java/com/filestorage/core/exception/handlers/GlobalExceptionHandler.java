package com.filestorage.core.exception.handlers;

import com.filestorage.adapter.dto.response.ErrorResponse;
import com.filestorage.core.exception.FileStorageException;
import com.filestorage.core.exception.FileUploadException;
import com.filestorage.core.service.ErrorLogService;
import com.filestorage.core.service.FileUploadStatusService;
import com.filestorage.domain.ErrorLog;
import com.filestorage.domain.FileUploadStatus;
import com.filestorage.domain.enums.UploadStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ErrorLogService errorLogService;
    private final FileUploadStatusService fileUploadStatusService;

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorResponse> handleFileStorageException(FileStorageException ex, HttpServletRequest request) {
        String stacktrace = Arrays.toString(ex.getStackTrace());
        if (ex.shouldLogToDatabase()) {
            if (ex instanceof FileUploadException) {
                fileUploadStatusService.create(FileUploadStatus
                        .builder()
                                .status(UploadStatus.ERROR)
                                .stacktrace(stacktrace)
                        .build());
            }

            saveErrorLog(stacktrace, request);

        }

        return ResponseEntity
                .status(ex.getErrorType().getStatusCode())
                .body(ErrorResponse.fromException(ex, request));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleFileStorageException(Exception e, HttpServletRequest request) {
        String stacktrace = Arrays.toString(e.getStackTrace());
        saveErrorLog(stacktrace, request);
        return ResponseEntity
                .status(500)
                .body(ErrorResponse.fromException(e, request));
    }

    private void saveErrorLog(String stacktrace, HttpServletRequest request) {
        errorLogService.create(ErrorLog
                .builder()
                .requestEndpoint(request.getRequestURI())
                .stackTrace(stacktrace)
                .build());
    }
}
