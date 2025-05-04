package com.filestorage.core.exception;

import com.filestorage.core.exception.enums.ErrorType;
import lombok.experimental.SuperBuilder;


public class FileUploadException extends FileStorageException {

    public FileUploadException(ErrorType errorType) {
        super(errorType);
    }

    @Override
    public boolean shouldLogToDatabase() {
        return true;
    }
}