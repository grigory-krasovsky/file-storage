package com.filestorage.core.service.impl;

import com.filestorage.adapter.dto.FileLocationDTO;
import com.filestorage.adapter.dto.FileMetadataDTO;
import com.filestorage.adapter.dto.converter.FileLocationConverter;
import com.filestorage.adapter.dto.converter.FileMetadataConverter;
import com.filestorage.adapter.dto.request.FileCreateRequest;
import com.filestorage.adapter.dto.response.FileLocationResponse;
import com.filestorage.core.service.FileCreator;
import com.filestorage.core.service.FileLocationService;
import com.filestorage.core.service.FileMetadataService;
import com.filestorage.domain.FileLocation;
import com.filestorage.domain.FileMetadata;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FileCreatorImpl implements FileCreator {

    private final FileLocationService fileLocationService;
    private final FileMetadataService fileMetadataService;
    private final FileLocationConverter fileLocationConverter;
    private final FileMetadataConverter fileMetadataConverter;

    @Transactional(rollbackOn = Throwable.class)
    public FileLocationResponse create(FileCreateRequest request) {
        FileMetadata fileMetadata = fileMetadataConverter.toEntity(request.getFileMetadataDTO());

        FileLocation savedFileLocation = fileLocationService.create(fileLocationConverter.toEntity(request.getFileLocationDTO()));
        fileMetadata.setFileLocation(savedFileLocation);

        FileMetadata savedFileMetadata = fileMetadataService.create(fileMetadata);

        return FileLocationResponse
                .builder()
                .fileLocationUUID(savedFileLocation.getId())
                .fileMetadataResponse(
                        FileLocationResponse.FileMetadataResponse
                                .builder()
                                .fileMetadataUUID(savedFileMetadata.getId())
                                .build())
                .build();
    }
}
