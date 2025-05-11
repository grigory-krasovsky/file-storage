package com.filestorage.core.service;

import com.filestorage.domain.entity.FileLocation;
import com.filestorage.domain.entity.FileStatus;
import com.filestorage.domain.enums.FileStatusType;

import java.util.List;

public interface FileStatusService extends CRUDService<FileStatus> {
    List<FileStatus> findAllByFileLocation(FileLocation fileLocation);
    Boolean statusExistsForFileLocation(FileLocation fileLocation, FileStatusType fileStatusType);
}
