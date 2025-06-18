package com.filestorage.core.service;

import com.filestorage.domain.entity.FileLocation;
import com.filestorage.domain.entity.FileStatus;
import com.filestorage.domain.enums.FileStatusType;

import java.util.List;
import java.util.Map;

public interface FileStatusService extends CRUDService<FileStatus> {
    List<FileStatus> findAllByFileLocation(FileLocation fileLocation);
    Map<FileLocation, List<FileStatus>> findAllByFileLocations(List<FileLocation> fileLocation);
    Boolean statusExistsForFileLocation(FileLocation fileLocation, FileStatusType fileStatusType);
}
