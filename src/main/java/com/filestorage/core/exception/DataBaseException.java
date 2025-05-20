package com.filestorage.core.exception;

import com.filestorage.core.exception.enums.ErrorType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DataBaseException extends FileStorageException {

    public DataBaseException(ErrorType errorType, String message) {
        super(errorType, message);
    }

    public static String ENTITY_IS_ABSENT_MESSAGE(UUID id, Class<?> clazz) {
        return String.format("Service: %s. Unable to find entity with id %s", clazz.getName(), id);
    }

    public static String FILE_METADATA_IS_ABSENT_MESSAGE(UUID id) {
        return String.format("Unable to find file metadata with location id %s", id);
    }

    public static String FILE_STATUS_IS_ABSENT(UUID id) {
        return String.format("Unable to find file status with location id %s", id);
    }

    public static String NO_SUCCESS_STATUS(UUID id) {
        return String.format("The file with location id %s has not been uploaded", id);
    }

    public static String NO_FILE_NAME(UUID id) {
        return String.format("File name for metadata with id %s is absent", id);
    }

    public static String NOT_IMPLEMENTED() {
        return "Method not implemented yet";
    }
    public static String NO_CONTENT_TYPE(UUID id) {
        return String.format("Content type for metadata with id %s is absent", id);
    }
}
