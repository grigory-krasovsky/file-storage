package com.filestorage.core.service.impl;

import com.filestorage.adapter.dto.FileLocationDTO;
import com.filestorage.adapter.dto.FileMetadataDTO;
import com.filestorage.adapter.dto.converter.FileLocationConverter;
import com.filestorage.adapter.dto.converter.FileMetadataConverter;
import com.filestorage.adapter.dto.request.FileLocationCreateRequest;
import com.filestorage.adapter.dto.response.FileLocationGetResponse;
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

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class FileLocationManagerImpl implements FileLocationManager {

    private final FileLocationService fileLocationService;
    private final FileMetadataService fileMetadataService;
    private final FileStatusService fileStatusService;
    private final FileLocationConverter fileLocationConverter;
    private final FileMetadataConverter fileMetadataConverter;
    private final FileAccessUtils fileAccessUtils;
    private final CommonUtils commonUtils;


    @Transactional
    public FileLocationGetResponse createFileLocation(FileLocationCreateRequest request) {

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
                .createdAt(OffsetDateTime.now())
                .fileLocation(savedFileLocation)
                .status(FileStatusType.UPLOAD_STARTED)
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

    private String getFilePath(@NonNull FileMetadataDTO fileMetadataDTO, @NonNull UUID id) {

        String rootPath = fileAccessUtils.getDefaultRootPath();
        OffsetDateTime dateTime = fileMetadataDTO.getFileDateTime();
        String datePart = commonUtils.getDatePart(dateTime);
        String timePart = commonUtils.getTimePart(dateTime);

        Path path = fileAccessUtils.buildPath(List.of(rootPath, datePart, timePart, id.toString()));

        return path.toString();
    }
}
