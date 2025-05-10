package com.filestorage.core.exception;

import com.filestorage.core.exception.enums.ErrorType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class FileStorageException extends RuntimeException {
    private final ErrorType errorType;
    private final String message;

    protected FileStorageException(ErrorType errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }

}
