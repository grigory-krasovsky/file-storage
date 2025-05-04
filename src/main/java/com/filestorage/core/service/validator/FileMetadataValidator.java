package com.filestorage.core.service.validator;

import com.filestorage.domain.FileMetadata;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class FileMetadataValidator implements EntityValidator<FileMetadata> {

    @Override
    public void correct(FileMetadata entity) {
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(OffsetDateTime.now());
        }
    }
}
