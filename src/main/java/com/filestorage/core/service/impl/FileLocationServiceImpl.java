package com.filestorage.core.service.impl;

import com.filestorage.core.service.FileLocationService;
import com.filestorage.core.service.validator.FileLocationValidator;
import com.filestorage.domain.FileLocation;
import com.filestorage.domain.repository.FileLocationRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FileLocationServiceImpl extends AbstractEntityService<FileLocation, FileLocationValidator, FileLocationRepository>
        implements FileLocationService {

    public FileLocationServiceImpl(FileLocationValidator validator, FileLocationRepository repository) {
        super(validator, repository);
    }

}
