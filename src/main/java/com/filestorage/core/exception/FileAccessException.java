package com.filestorage.core.exception;

import com.filestorage.core.exception.enums.ErrorType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FileAccessException extends FileStorageException {

    private UUID fileLocationId;

    public FileAccessException(ErrorType errorType, String message) {
        super(errorType, message);
    }

    public static String NO_FILE(String filePath) {
        return String.format("File with path %s does not exist", filePath);
    }

    public static String UNABLE_TO_READ(String filePath) {
        return String.format("File with path %s is not readable", filePath);
    }

    public static String MALFORMED_PATH(String urlString) {
        return String.format("Path %s is malformed", urlString);
    }

    public static String UNABLE_TO_DELETE(String urlString) {
        return String.format("Unable to delete file %s", urlString);
    }

    public static String GENERAL_MESSAGE(String message) {
        return String.format("Something went wrong: %s", message);
    }
}