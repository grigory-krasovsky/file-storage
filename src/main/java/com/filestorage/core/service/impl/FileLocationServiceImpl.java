package com.filestorage.core.service.impl;

import com.filestorage.core.service.FileLocationService;
import com.filestorage.core.service.validator.FileLocationValidator;
import com.filestorage.domain.entity.FileLocation;
import com.filestorage.domain.repository.FileLocationRepository;
import org.springframework.stereotype.Service;

@Service
public class FileLocationServiceImpl extends AbstractEntityService<FileLocation, FileLocationValidator, FileLocationRepository>
        implements FileLocationService {

    public FileLocationServiceImpl(FileLocationValidator validator, FileLocationRepository repository) {
        super(validator, repository);
    }

}
