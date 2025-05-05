package com.filestorage.core.exception.handlers;

import com.filestorage.adapter.dto.response.ErrorResponse;
import com.filestorage.core.exception.FileStorageException;
import com.filestorage.core.exception.FileUploadException;
import com.filestorage.core.service.ErrorLogService;
import com.filestorage.core.service.FileStatusService;
import com.filestorage.domain.entity.ErrorLog;
import com.filestorage.domain.entity.FileStatus;
import com.filestorage.domain.enums.FileStatusType;
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
    private final FileStatusService fileStatusService;

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorResponse> handleFileStorageException(FileStorageException ex, HttpServletRequest request) {
        String stacktrace = Arrays.toString(ex.getStackTrace());
        if (ex.shouldLogToDatabase()) {
            if (ex instanceof FileUploadException) {
                fileStatusService.create(FileStatus
                        .builder()
                        .status(FileStatusType.UPLOAD_ERROR)
                        .stacktrace(stacktrace)
                        .build());
            }

            saveErrorLog(stacktrace, getClassName(ex), ex.getMessage(), request);

        }

        return ResponseEntity
                .status(ex.getErrorType().getStatusCode())
                .body(ErrorResponse.fromException(ex, request));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleFileStorageException(Exception e, HttpServletRequest request) {
        String stacktrace = Arrays.toString(e.getStackTrace());
        String className = getClassName(e);
        saveErrorLog(stacktrace, className, e.getMessage(), request);
        return ResponseEntity
                .status(500)
                .body(ErrorResponse.fromException(e, request));
    }

    private void saveErrorLog(String stacktrace, String className, String details, HttpServletRequest request) {
        errorLogService.create(ErrorLog
                .builder()
                .requestEndpoint(request.getRequestURI())
                .serviceName(className)
                .stackTrace(stacktrace)
                .details(details)
                .build());
    }

    private String getClassName(Exception e) {
        return Arrays.stream(e.getStackTrace()).toList().stream().filter(element -> element.getClassName().contains("com.filestorage"))
                .findFirst().map(StackTraceElement::getClassName).orElse(null);
    }
}
