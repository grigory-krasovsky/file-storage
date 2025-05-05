package com.filestorage.core.service.validator;

import com.filestorage.domain.entity.FileMetadata;
import org.springframework.stereotype.Service;

@Service
public class FileMetadataValidator implements EntityValidator<FileMetadata> {

    @Override
    public void correct(FileMetadata entity) {
        correctCreatedAt(entity);
    }
}
