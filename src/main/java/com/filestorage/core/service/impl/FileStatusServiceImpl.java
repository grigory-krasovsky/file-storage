package com.filestorage.core.service.impl;

import com.filestorage.core.exception.DataBaseException;
import com.filestorage.core.exception.enums.ErrorType;
import com.filestorage.core.service.FileStatusService;
import com.filestorage.core.service.validator.FileStatusValidator;
import com.filestorage.domain.entity.FileLocation;
import com.filestorage.domain.entity.FileStatus;
import com.filestorage.domain.repository.FileUploadStatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.filestorage.core.exception.DataBaseException.*;

@Service
public class FileStatusServiceImpl extends AbstractEntityService<FileStatus, FileStatusValidator, FileUploadStatusRepository>
        implements FileStatusService {

    public FileStatusServiceImpl(FileStatusValidator validator, FileUploadStatusRepository repository) {
        super(validator, repository);
    }

    @Override
    public List<FileStatus> findAllByFileLocation(FileLocation fileLocation) {
        List<FileStatus> statuses = this.repository.findAllByFileLocation(fileLocation);
        if (statuses.isEmpty()) {
            throw new DataBaseException(ErrorType.SYSTEM_ERROR, FILE_STATUS_IS_ABSENT(fileLocation.getId()));
        }
        return statuses;
    }
}
