package com.filestorage.core.service.validator;

import com.filestorage.core.exception.DataBaseException;
import com.filestorage.core.exception.enums.ErrorType;
import com.filestorage.domain.entity.FileMetadata;
import org.springframework.stereotype.Service;

import static com.filestorage.core.exception.DataBaseException.NO_FILE_NAME;

@Service
public class FileMetadataValidator implements EntityValidator<FileMetadata> {

    @Override
    public void correctForCreate(FileMetadata entity) {
        correctCreatedAt(entity);
    }

    @Override
    public void correctForUpdate(FileMetadata entity) {
        if (entity.getFileName() == null || entity.getFileName().isBlank()) {
            throw new DataBaseException(ErrorType.SYSTEM_ERROR, NO_FILE_NAME(entity.getId()));
        }
        if (entity.getContentType() == null || entity.getFileName().isBlank()) {
            throw new DataBaseException(ErrorType.SYSTEM_ERROR, NO_FILE_NAME(entity.getId()));
        }
    }
}
