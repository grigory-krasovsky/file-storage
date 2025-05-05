package com.filestorage.core.service.impl;

import com.filestorage.core.service.FileStatusService;
import com.filestorage.core.service.validator.FileStatusValidator;
import com.filestorage.domain.FileStatus;
import com.filestorage.domain.repository.FileUploadStatusRepository;
import org.springframework.stereotype.Service;

@Service
public class FileStatusServiceImpl extends AbstractEntityService<FileStatus, FileStatusValidator, FileUploadStatusRepository>
        implements FileStatusService {

    public FileStatusServiceImpl(FileStatusValidator validator, FileUploadStatusRepository repository) {
        super(validator, repository);
    }

}
