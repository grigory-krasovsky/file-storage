package com.filestorage.core.service.impl;

import com.filestorage.adapter.dto.FileLocationDTO;
import com.filestorage.adapter.dto.FileMetadataDTO;
import com.filestorage.adapter.dto.converter.FileLocationConverter;
import com.filestorage.adapter.dto.converter.FileMetadataConverter;
import com.filestorage.adapter.dto.converter.FileStatusConverter;
import com.filestorage.adapter.dto.request.FileAccessSaveRequest;
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
import com.filestorage.grpc.GrpcFileAccessSaveRequest;
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
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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
    FileStatusConverter fileStatusConverter;


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
    public FileLocation beforeCreate(@NonNull FileAccessSaveRequest request) {
        if (request.getContents().isEmpty()) {
            throw new FileUploadException(request.getId(), ErrorType.VALIDATION, EMPTY_FILE(request.getId()));
        }

        FileLocation fileLocation = fileLocationService.findById(request.getId());

        validateUploadIsPossible(fileLocation);

        fileStatusService.create(FileStatus.builder()
                .fileLocation(fileLocation)
                .status(UPLOAD_STARTED).build());

        FileMetadata fileMetadata = fileMetadataService.findByLocationAndRelevant(fileLocation);

        fileMetadata.setFileName(request.getContents().getOriginalFilename());
        fileMetadata.setContentType(request.getContents().getContentType());

        fileMetadataService.update(fileMetadata);
        return fileLocation;
    }

    @Override
    public FileLocation beforeCreate(GrpcFileAccessSaveRequest request) {
        UUID uuid = UUID.fromString(request.getId());
        if (request.getContents().isEmpty()) {
            throw new FileUploadException(uuid, ErrorType.VALIDATION, EMPTY_FILE(uuid));
        }
        FileLocation fileLocation = fileLocationService.findById(uuid);

        validateUploadIsPossible(fileLocation);

        fileStatusService.create(FileStatus.builder()
                .fileLocation(fileLocation)
                .status(UPLOAD_STARTED).build());

        FileMetadata fileMetadata = fileMetadataService.findByLocationAndRelevant(fileLocation);

        fileMetadata.setFileName(request.getFilename());
        fileMetadata.setContentType(request.getContentType());
        fileMetadataService.update(fileMetadata);
        return fileLocation;
    }

    private Boolean validateUploadIsPossible(FileLocation fileLocation) {
        List<FileStatus> allStatuses = fileStatusService.findAllByFileLocation(fileLocation);
        UUID fileLocationId = fileLocation.getId();

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
    public FileLocation afterSave(@NonNull FileAccessSaveRequest request) {
        FileLocation fileLocation = fileLocationService.findById(request.getId());

        fileStatusService.create(FileStatus.builder()
                .fileLocation(fileLocation)
                .status(UPLOAD_SUCCESS)
                .build());

        return fileLocation;
    }

    @Override
    public FileLocation afterSave(GrpcFileAccessSaveRequest request) {
        UUID uuid = UUID.fromString(request.getId());

        FileLocation fileLocation = fileLocationService.findById(uuid);

        fileStatusService.create(FileStatus.builder()
                .fileLocation(fileLocation)
                .status(UPLOAD_SUCCESS)
                .build());

        return fileLocation;
    }

    @Override
    public FileMetadataDTO getFileLocationWithMetadata(UUID id) {
        FileLocation fileLocation = fileLocationService.findById(id);
        if (!fileStatusService.statusExistsForFileLocation(fileLocation, UPLOAD_SUCCESS)) {
            throw new DataBaseException(ErrorType.SYSTEM_ERROR, NO_SUCCESS_STATUS(id));
        }
        FileMetadata metadata = fileMetadataService.findByLocationAndRelevant(fileLocation);
        return fileMetadataConverter.toDto(metadata);
    }

    @Override
    public List<FileLocationGetResponse> getAllFileLocationWithMetadataWithStatuses() {
        List<FileLocation> allFileLocations = fileLocationService.findAll();
        Map<FileLocation, FileMetadata> locationMetadata = fileMetadataService.findByLocationsAndRelevant(allFileLocations);
        Map<FileLocation, List<FileStatus>> locationStatuses = fileStatusService.findAllByFileLocations(allFileLocations);

        return allFileLocations.stream().map(location -> {
            FileStatus fileStatus = locationStatuses.get(location).stream()
                    .max(Comparator.comparing(FileStatus::getCreatedAt)).stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("Unable to get the latest status"));
            return FileLocationGetResponse
                    .builder()
                    .fileLocationUUID(location.getId())
                    .fileMetadataResponse(
                            FileLocationGetResponse.FileMetadataResponse
                                    .builder()
                                    .fileMetadataUUID(locationMetadata.get(location).getId())
                                    .fileMetadataDTO(fileMetadataConverter.toDto(locationMetadata.get(location)))
                                    .build())
                    .fileStatusResponse(
                            FileLocationGetResponse.FileStatusResponse
                                    .builder()
                                    .fileUploadStatusUUID(fileStatus.getId())
                                    .fileStatusDTO(fileStatusConverter.toDto(fileStatus))
                                    .build()
                    ).build();
        }).collect(Collectors.toList());
    }

    //Todo probably wrong approach. Need to be carried out in 2 steps like creation
    @Override
    public FileLocation beforeUpdate(FileAccessSaveRequest request) {
        if (request.getContents().isEmpty()) {
            throw new FileUploadException(request.getId(), ErrorType.VALIDATION, EMPTY_FILE(request.getId()));
        }

        FileLocation fileLocation = fileLocationService.findById(request.getId());
        fileStatusService.create(FileStatus.builder()
                .fileLocation(fileLocation)
                .status(UPDATE_UPLOAD_STARTED).build());

        FileMetadata oldFileMetadata = fileMetadataService.findByLocationAndRelevant(fileLocation);
        oldFileMetadata.setRelevant(false);

        FileMetadata newFileMetadata = FileMetadata.builder()
                .relevant(true)
                .fileName(request.getContents().getOriginalFilename())
                .contentType(request.getContents().getContentType())
                .fileLocation(fileLocation)
                .author(oldFileMetadata.getAuthor()) //Todo probably wrong
                .build();

        fileMetadataService.batchSave(List.of(oldFileMetadata, newFileMetadata));

        return fileLocation;
    }

    @Override
    public FileLocation beforeDelete(UUID id) {
        FileLocation fileLocation = fileLocationService.findById(id);
        fileStatusService.create(FileStatus.builder()
                        .fileLocation(fileLocation)
                        .status(TO_BE_DELETED)
                .build());
        return fileLocation;
    }

    @Override
    public FileLocation afterDelete(UUID id) {
        FileLocation fileLocation = fileLocationService.findById(id);
        fileStatusService.create(FileStatus.builder()
                        .fileLocation(fileLocation)
                        .status(DELETED)
                .build());
        return fileLocation;
    }

    @Override
    public Boolean deletable(UUID id) {
        if (!fileLocationService.exists(id)) return false;

        return !fileStatusService.statusExistsForFileLocation(fileLocationService.findById(id), DELETED)
                && fileStatusService.statusExistsForFileLocation(fileLocationService.findById(id), UPLOAD_SUCCESS);
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
