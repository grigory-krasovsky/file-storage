package com.filestorage.core.service;

import com.filestorage.domain.entity.FileLocation;
import com.filestorage.domain.entity.FileMetadata;

public interface FileMetadataService extends CRUDService<FileMetadata> {
    FileMetadata findByLocationAndRelevant(FileLocation fileLocation);
}
