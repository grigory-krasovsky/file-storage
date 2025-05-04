package com.filestorage.core.exception;

import com.filestorage.core.exception.enums.ErrorType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class FileStorageException extends RuntimeException {
    private final ErrorType errorType;

    protected FileStorageException(ErrorType errorType) {
        this.errorType = errorType;
    }

    public abstract boolean shouldLogToDatabase();
}
