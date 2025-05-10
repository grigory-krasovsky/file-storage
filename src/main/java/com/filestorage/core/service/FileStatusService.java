package com.filestorage.core.service;

import com.filestorage.domain.entity.FileLocation;
import com.filestorage.domain.entity.FileStatus;

import java.util.List;

public interface FileStatusService extends CRUDService<FileStatus> {
    List<FileStatus> findAllByFileLocation(FileLocation fileLocation);
}
