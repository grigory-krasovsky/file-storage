package com.filestorage.core.service.impl;

import com.filestorage.core.exception.DataBaseException;
import com.filestorage.core.exception.enums.ErrorType;
import com.filestorage.core.service.FileMetadataService;
import com.filestorage.core.service.validator.FileMetadataValidator;
import com.filestorage.domain.entity.FileLocation;
import com.filestorage.domain.entity.FileMetadata;
import com.filestorage.domain.repository.FileMetadataRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FileMetadataServiceImpl extends AbstractEntityService<FileMetadata, FileMetadataValidator, FileMetadataRepository>
        implements FileMetadataService {

    public FileMetadataServiceImpl(FileMetadataValidator validator, FileMetadataRepository repository) {
        super(validator, repository);
    }

    @Override
    public FileMetadata findByLocationAndRelevant(FileLocation fileLocation) {

        return this.repository.findByFileLocationAndRelevant(fileLocation, true)
                .orElseThrow(() -> new DataBaseException(ErrorType.SYSTEM_ERROR, DataBaseException.FILE_METADATA_IS_ABSENT_MESSAGE(fileLocation.getId())));
    }

    @Override
    public Map<FileLocation, FileMetadata> findByLocationsAndRelevant(List<FileLocation> fileLocations) {
        List<FileMetadata> allMetadata = this.repository.findByFileLocationInAndRelevant(fileLocations, true);
        return allMetadata.stream().collect(Collectors.toMap(FileMetadata::getFileLocation, Function.identity()));
    }
}
