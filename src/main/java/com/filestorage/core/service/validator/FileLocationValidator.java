package com.filestorage.core.service.validator;

import com.filestorage.domain.entity.FileLocation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileLocationValidator implements EntityValidator<FileLocation> {

    @Override
    public void correctForCreate(FileLocation entity) {
        correctCreatedAt(entity);
    }

    @Override
    public void correctForUpdate(FileLocation entity) {

    }

    @Override
    public void correctForBatchSave(List<FileLocation> entities) {
        entities.forEach(this::correctCreatedAt);
    }
}
