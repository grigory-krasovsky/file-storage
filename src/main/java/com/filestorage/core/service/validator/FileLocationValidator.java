package com.filestorage.core.service.validator;

import com.filestorage.domain.entity.FileLocation;
import org.springframework.stereotype.Service;

@Service
public class FileLocationValidator implements EntityValidator<FileLocation> {

    @Override
    public void correct(FileLocation entity) {
        correctCreatedAt(entity);
    }
}
