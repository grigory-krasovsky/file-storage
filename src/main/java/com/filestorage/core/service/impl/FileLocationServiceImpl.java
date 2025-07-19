package com.filestorage.core.service.impl;

import com.filestorage.core.service.FileLocationService;
import com.filestorage.core.service.FileMetadataService;
import com.filestorage.core.service.validator.FileLocationValidator;
import com.filestorage.domain.entity.FileLocation;
import com.filestorage.domain.repository.FileLocationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileLocationServiceImpl extends AbstractEntityService<FileLocation, FileLocationValidator, FileLocationRepository>
        implements FileLocationService {

    public FileLocationServiceImpl(FileLocationValidator validator, FileLocationRepository repository) {
        super(validator, repository);
    }

    @Override
    public List<FileLocation> getPotentialPreviews(UUID parentId) {
        return repository.findByParent(parentId);
    }

    @Override
    public Page<FileLocation> findAllByParentIsNull(Pageable pageable) {
        return repository.findAllByParentIsNull(pageable);
    }
}
