package com.filestorage.core.service.impl;

import com.filestorage.core.service.FileMetadataService;
import com.filestorage.core.service.validator.FileMetadataValidator;
import com.filestorage.domain.FileMetadata;
import com.filestorage.domain.repository.FileMetadataRepository;
import org.springframework.stereotype.Service;

@Service
public class FileMetadataServiceImpl extends AbstractEntityService<FileMetadata, FileMetadataValidator, FileMetadataRepository>
        implements FileMetadataService {

    public FileMetadataServiceImpl(FileMetadataValidator validator, FileMetadataRepository repository) {
        super(validator, repository);
    }

}
