package com.filestorage.core.service.validator;

import com.filestorage.domain.FileLocation;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class FileLocationValidator implements EntityValidator<FileLocation> {

    @Override
    public void correct(FileLocation entity) {
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(OffsetDateTime.now());
        }
    }
}
