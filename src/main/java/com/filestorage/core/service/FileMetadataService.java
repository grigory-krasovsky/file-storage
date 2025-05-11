package com.filestorage.core.service;

import com.filestorage.domain.entity.FileLocation;
import com.filestorage.domain.entity.FileMetadata;

import java.util.Optional;

public interface FileMetadataService extends CRUDService<FileMetadata> {
    FileMetadata findByLocation(FileLocation fileLocation);
}
