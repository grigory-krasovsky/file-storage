package com.filestorage.core.service.impl;

import com.filestorage.core.service.FileUploadStatusService;
import com.filestorage.core.service.validator.FileUploadStatusValidator;
import com.filestorage.domain.FileUploadStatus;
import com.filestorage.domain.repository.FileUploadStatusRepository;
import org.springframework.stereotype.Service;

@Service
public class FileUploadStatusServiceImpl extends AbstractEntityService<FileUploadStatus, FileUploadStatusValidator, FileUploadStatusRepository>
        implements FileUploadStatusService {

    public FileUploadStatusServiceImpl(FileUploadStatusValidator validator, FileUploadStatusRepository repository) {
        super(validator, repository);
    }

}
