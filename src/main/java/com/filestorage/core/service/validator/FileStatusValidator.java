package com.filestorage.core.service.validator;

import com.filestorage.domain.entity.FileStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileStatusValidator implements EntityValidator<FileStatus>{
    @Override
    public void correctForCreate(FileStatus entity) {
        correctCreatedAt(entity);
    }

    @Override
    public void correctForUpdate(FileStatus entity) {

    }

    @Override
    public void correctForBatchSave(List<FileStatus> entities) {
        entities.forEach(this::correctCreatedAt);
    }
}
