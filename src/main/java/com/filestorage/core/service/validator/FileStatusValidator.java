package com.filestorage.core.service.validator;

import com.filestorage.domain.entity.FileStatus;
import org.springframework.stereotype.Service;

@Service
public class FileStatusValidator implements EntityValidator<FileStatus>{
    @Override
    public void correct(FileStatus entity) {
        correctCreatedAt(entity);
    }
}
