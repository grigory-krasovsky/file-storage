package com.filestorage.core.service.impl;

import com.filestorage.core.exception.DataBaseException;
import com.filestorage.core.exception.enums.ErrorType;
import com.filestorage.core.service.FileStatusService;
import com.filestorage.core.service.validator.FileStatusValidator;
import com.filestorage.domain.entity.FileLocation;
import com.filestorage.domain.entity.FileStatus;
import com.filestorage.domain.enums.FileStatusType;
import com.filestorage.domain.repository.FileUploadStatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public Map<FileLocation, List<FileStatus>> findAllByFileLocations(List<FileLocation> fileLocations) {
        List<FileStatus> allByFileLocationIn = this.repository.findAllByFileLocationIn(fileLocations);
        return allByFileLocationIn.stream()
                .collect(Collectors.groupingBy(status -> fileLocations.stream().filter(l ->
                        l.getId().equals(status.getFileLocation().getId()))
                        .findFirst().orElseThrow(() -> new RuntimeException("No such element"))));
    }

    @Override
    public Boolean statusExistsForFileLocation(FileLocation fileLocation, FileStatusType fileStatusType) {
        List<FileStatus> allStatuses = findAllByFileLocation(fileLocation);
        return allStatuses.stream().anyMatch(fileStatus -> fileStatus.getStatus().equals(fileStatusType));
    }
}
