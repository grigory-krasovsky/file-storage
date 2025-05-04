package com.filestorage.core.service.impl;

import com.filestorage.adapter.dto.converter.FileLocationConverter;
import com.filestorage.adapter.dto.converter.FileMetadataConverter;
import com.filestorage.adapter.dto.request.FileCreateRequest;
import com.filestorage.adapter.dto.response.FileLocationResponse;
import com.filestorage.core.exception.FileUploadException;
import com.filestorage.core.exception.enums.ErrorType;
import com.filestorage.core.service.FileCreator;
import com.filestorage.core.service.FileLocationService;
import com.filestorage.core.service.FileMetadataService;
import com.filestorage.core.service.FileUploadStatusService;
import com.filestorage.domain.FileLocation;
import com.filestorage.domain.FileMetadata;
import com.filestorage.domain.FileUploadStatus;
import com.filestorage.domain.enums.UploadStatus;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.OffsetDateTime;

@Service
@AllArgsConstructor
public class FileCreatorImpl implements FileCreator {

    private final FileLocationService fileLocationService;
    private final FileMetadataService fileMetadataService;
    private final FileUploadStatusService fileUploadStatusService;
    private final FileLocationConverter fileLocationConverter;
    private final FileMetadataConverter fileMetadataConverter;
    private final TransactionTemplate transactionTemplate;


    @Transactional
    public FileLocationResponse create(FileCreateRequest request) {

        FileMetadata fileMetadata = fileMetadataConverter.toEntity(request.getFileMetadataDTO());

        FileLocation savedFileLocation = fileLocationService.create(fileLocationConverter.toEntity(request.getFileLocationDTO()));
        fileMetadata.setFileLocation(savedFileLocation);

        FileMetadata savedFileMetadata = fileMetadataService.create(fileMetadata);
        FileUploadStatus savedFileUploadStatus = fileUploadStatusService.create(FileUploadStatus
                .builder()
                .createdAt(OffsetDateTime.now())
                .fileLocation(savedFileLocation)
                .status(UploadStatus.UPLOAD_STARTED)
                .build());

        return FileLocationResponse
                .builder()
                .fileLocationUUID(savedFileLocation.getId())
                .fileMetadataResponse(
                        FileLocationResponse.FileMetadataResponse
                                .builder()
                                .fileMetadataUUID(savedFileMetadata.getId())
                                .build())
                .fileUploadStatusResponse(
                        FileLocationResponse.FileUploadStatusResponse
                                .builder()
                                .fileUploadStatusUUID(savedFileUploadStatus.getId())
                                .build()
                ).build();
    }
}
