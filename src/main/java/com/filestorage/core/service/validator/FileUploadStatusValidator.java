package com.filestorage.core.service.validator;

import com.filestorage.domain.FileUploadStatus;
import org.springframework.stereotype.Service;

@Service
public class FileUploadStatusValidator implements EntityValidator<FileUploadStatus>{
    @Override
    public void correct(FileUploadStatus entity) {
        correctCreatedAt(entity);
    }
}
