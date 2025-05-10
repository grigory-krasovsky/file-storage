package com.filestorage.core.exception;

import com.filestorage.core.exception.enums.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FileUploadException extends FileStorageException {

    private UUID fileLocationId;

    public FileUploadException(UUID fileLocationId, ErrorType errorType, String message) {
        super(errorType, message);
        this.fileLocationId = fileLocationId;
    }

    public static String UNABLE_TO_CREATE_FILE(String filePath) {
        return String.format("Unable to create file with path %s", filePath);
    }

    public static String UNABLE_TO_CREATE_DIRECTORY(String path) {
        return String.format("Unable to create directory with path %s", path);
    }

    public static String DUPLICATE_ID_UPLOAD(UUID id) {
        return String.format("File has already been uploaded for file location id %s", id);
    }

    public static String MULTIPLE_UPLOADS(UUID id) {
        return String.format("File uploaded has already been started for file location id %s", id);
    }

    public static String UNKNOWN_STATUS_EXCEPTION(UUID id) {
        return String.format("File uploaded is in unknown state. File location id is %s", id);
    }

    public static String EMPTY_FILE(UUID uuid) {
        return String.format("File is empty. File location id: %s", uuid);
    }
}