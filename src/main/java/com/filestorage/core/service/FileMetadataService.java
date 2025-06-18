package com.filestorage.core.service;

import com.filestorage.domain.entity.FileLocation;
import com.filestorage.domain.entity.FileMetadata;

import java.util.List;
import java.util.Map;

public interface FileMetadataService extends CRUDService<FileMetadata> {
    FileMetadata findByLocationAndRelevant(FileLocation fileLocation);
    Map<FileLocation, FileMetadata> findByLocationsAndRelevant(List<FileLocation> fileLocations);
}
