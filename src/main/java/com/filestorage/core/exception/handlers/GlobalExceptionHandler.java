package com.filestorage.core.exception.handlers;

import com.filestorage.adapter.dto.response.ErrorResponse;
import com.filestorage.core.exception.DataBaseException;
import com.filestorage.core.exception.FileAccessException;
import com.filestorage.core.exception.FileUploadException;
import com.filestorage.core.exception.enums.ErrorType;
import com.filestorage.core.service.ErrorLogService;
import com.filestorage.core.service.FileLocationService;
import com.filestorage.core.service.FileStatusService;
import com.filestorage.domain.entity.ErrorLog;
import com.filestorage.domain.entity.FileLocation;
import com.filestorage.domain.entity.FileStatus;
import com.filestorage.domain.enums.FileStatusType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.UUID;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ErrorLogService errorLogService;
    private final FileStatusService fileStatusService;
    private final FileLocationService fileLocationService;

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorResponse> handleFileStorageException(FileUploadException e, HttpServletRequest request) {
        String stacktrace = Arrays.toString(e.getStackTrace());

        UUID fileLocationId = e.getFileLocationId();
        FileLocation fileLocation = fileLocationService.findById(fileLocationId);
        fileStatusService.create(FileStatus
                .builder()
                .fileLocation(fileLocation)
                .status(FileStatusType.UPLOAD_ERROR)
                .stacktrace(stacktrace)
                .build());

        saveErrorLog(stacktrace, getClassName(e), getFullMessage(e), request);

        return ResponseEntity
                .status(e.getErrorType().getStatusCode())
                .body(ErrorResponse.fromException(e));
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<ErrorResponse> handleFileStorageException(DataBaseException e, HttpServletRequest request) {
        String stacktrace = Arrays.toString(e.getStackTrace());

        saveErrorLog(stacktrace, getClassName(e), getFullMessage(e), request);

        return ResponseEntity
                .status(e.getErrorType().getStatusCode())
                .body(ErrorResponse.fromException(e));
    }

    @ExceptionHandler(FileAccessException.class)
    public ResponseEntity<ErrorResponse> handleFileStorageException(FileAccessException e, HttpServletRequest request) {
        String stacktrace = Arrays.toString(e.getStackTrace());

        saveErrorLog(stacktrace, getClassName(e), getFullMessage(e), request);

        return ResponseEntity
                .status(e.getErrorType().getStatusCode())
                .body(ErrorResponse.fromException(e));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleFileStorageException(Exception e, HttpServletRequest request) {
        String stacktrace = Arrays.toString(e.getStackTrace());

        saveErrorLog(stacktrace, getClassName(e), getFullMessage(e), request);
        return ResponseEntity
                .status(500)
                .body(ErrorResponse.fromException(getFullMessage(e)));
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
                .findFirst().map(StackTraceElement::getClassName).orElse(String.valueOf(Arrays.stream(e.getStackTrace()).toList().stream().findFirst().orElse(null)));
    }

    private String getFullMessage(Exception e) {
        if (e.getCause() == null || e.getMessage().equals(e.getCause().getMessage())) {
            return String.format("%s", e.getMessage());
        }
        return String.format("%s. %s", e.getMessage(), e.getCause().getMessage());
    }
}
