package com.filestorage.core.service.impl;

import com.filestorage.adapter.dto.FileLocationDTO;
import com.filestorage.adapter.dto.FileMetadataDTO;
import com.filestorage.adapter.dto.converter.FileLocationConverter;
import com.filestorage.adapter.dto.converter.FileMetadataConverter;
import com.filestorage.adapter.dto.request.FileAccessCreateRequest;
import com.filestorage.adapter.dto.request.FileLocationCreateRequest;
import com.filestorage.adapter.dto.response.FileLocationGetResponse;
import com.filestorage.core.exception.DataBaseException;
import com.filestorage.core.exception.FileUploadException;
import com.filestorage.core.exception.enums.ErrorType;
import com.filestorage.core.service.FileLocationManager;
import com.filestorage.core.service.FileLocationService;
import com.filestorage.core.service.FileMetadataService;
import com.filestorage.core.service.FileStatusService;
import com.filestorage.core.utils.CommonUtils;
import com.filestorage.core.utils.FileAccessUtils;
import com.filestorage.domain.entity.FileLocation;
import com.filestorage.domain.entity.FileMetadata;
import com.filestorage.domain.entity.FileStatus;
import com.filestorage.domain.enums.FileStatusType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.filestorage.core.exception.DataBaseException.*;
import static com.filestorage.core.exception.FileUploadException.*;
import static com.filestorage.domain.enums.FileStatusType.*;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileLocationManagerImpl implements FileLocationManager {

    FileLocationService fileLocationService;
    FileMetadataService fileMetadataService;
    FileStatusService fileStatusService;
    FileLocationConverter fileLocationConverter;
    FileMetadataConverter fileMetadataConverter;
    FileAccessUtils fileAccessUtils;
    CommonUtils commonUtils;


    @Override
    @Transactional
    public FileLocationGetResponse createFileLocation(@NonNull FileLocationCreateRequest request) {

        FileMetadata fileMetadata = fileMetadataConverter.toEntity(request.getFileMetadataDTO());

        FileLocationDTO fileLocationDTO = FileLocationDTO.builder()
                .filePath("")
                .build();

        FileLocation savedFileLocation = fileLocationService.create(fileLocationConverter.toEntity(fileLocationDTO));

        String filePath = getFilePath(request.getFileMetadataDTO(), savedFileLocation.getId());
        savedFileLocation.setFilePath(filePath);
        fileLocationService.update(savedFileLocation);

        fileMetadata.setFileLocation(savedFileLocation);

        FileMetadata savedFileMetadata = fileMetadataService.create(fileMetadata);
        FileStatus savedFileStatus = fileStatusService.create(FileStatus
                .builder()
                .fileLocation(savedFileLocation)
                .status(UPLOAD_READY)
                .build());

        return FileLocationGetResponse
                .builder()
                .fileLocationUUID(savedFileLocation.getId())
                .fileMetadataResponse(
                        FileLocationGetResponse.FileMetadataResponse
                                .builder()
                                .fileMetadataUUID(savedFileMetadata.getId())
                                .build())
                .fileStatusResponse(
                        FileLocationGetResponse.FileStatusResponse
                                .builder()
                                .fileUploadStatusUUID(savedFileStatus.getId())
                                .build()
                ).build();
    }

    @Override
    public FileLocation beforeCreate(@NonNull FileAccessCreateRequest request) {
        FileLocation fileLocation = fileLocationService.findById(request.getId());

        validateUploadIsPossible(fileLocation);

        fileStatusService.create(FileStatus.builder()
                .fileLocation(fileLocation)
                .status(UPLOAD_STARTED).build());

        FileMetadata fileMetadata = fileMetadataService.findByLocation(fileLocation)
                .orElseThrow(() -> new DataBaseException(ErrorType.SYSTEM_ERROR, FILE_METADATA_IS_ABSENT_MESSAGE(request.getId())));
        if (request.getContents().isEmpty()) {
            throw new FileUploadException(fileLocation.getId(), ErrorType.VALIDATION, EMPTY_FILE(fileLocation.getId()));
        }

        fileMetadata.setFileName(request.getContents().getOriginalFilename());
        fileMetadata.setContentType(request.getContents().getContentType());

        fileMetadataService.update(fileMetadata);
        return fileLocation;
    }

    private Boolean validateUploadIsPossible(FileLocation fileLocation) {
        List<FileStatus> allStatuses = fileStatusService.findAllByFileLocation(fileLocation);
        UUID fileLocationId = fileLocation.getId();

        if (allStatuses.isEmpty()) {
            throw new DataBaseException(ErrorType.SYSTEM_ERROR, FILE_STATUS_IS_ABSENT(fileLocationId));
        }

        if (allStatuses.stream().anyMatch(s -> s.getStatus() == UPLOAD_SUCCESS)) {
            throw new FileUploadException(fileLocationId, ErrorType.SYSTEM_ERROR, DUPLICATE_ID_UPLOAD(fileLocationId));
        }

        if (allStatuses.stream().anyMatch(s -> s.getStatus() == UPLOAD_STARTED)) {
            throw new FileUploadException(fileLocationId, ErrorType.SYSTEM_ERROR, MULTIPLE_UPLOADS(fileLocationId));
        }

        return allStatuses.stream()
                .max(Comparator.comparing(FileStatus::getCreatedAt))
                .filter(s -> s.getStatus() == UPLOAD_READY || s.getStatus() == UPLOAD_ERROR)
                .map(s -> true)
                .orElseThrow(() -> new FileUploadException(fileLocationId, ErrorType.SYSTEM_ERROR, UNKNOWN_STATUS_EXCEPTION(fileLocationId)));
    }

    @Override
    public FileLocation afterCreate(@NonNull FileAccessCreateRequest request) {
        FileLocation fileLocation = fileLocationService.findById(request.getId());

        fileStatusService.create(FileStatus.builder()
                .fileLocation(fileLocation)
                .status(UPLOAD_SUCCESS)
                .build());

        return fileLocation;
    }

    private String getFilePath(@NonNull FileMetadataDTO fileMetadataDTO, @NonNull UUID id) {

        String rootPath = fileAccessUtils.getDefaultRootPath();
        OffsetDateTime dateTime = fileMetadataDTO.getFileDateTime();
        String datePart = commonUtils.getDatePart(dateTime);
        String timePart = commonUtils.getTimePart(dateTime);

        Path path = fileAccessUtils.buildPath(List.of(rootPath, datePart, timePart, id.toString()));

        return path.toString();
    }
}
