package com.filestorage.core.service.impl;

import com.filestorage.adapter.dto.request.FileCreateRequest;
import com.filestorage.adapter.dto.response.FileLocationResponse;
import com.filestorage.core.service.FileCreator;
import com.filestorage.core.service.FileLocationService;
import com.filestorage.core.service.FileMetadataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FileCreatorImpl implements FileCreator {

    private final FileLocationService fileLocationService;
    private final FileMetadataService fileMetadataService;

    public FileLocationResponse create(FileCreateRequest request) {
        UUID fileLocationUUID = fileLocationService.create(request.getFileLocationDTO());
        request.getFileMetadataDTO().setId(fileLocationUUID);
        UUID fileMetadataUUID = fileMetadataService.create(request.getFileMetadataDTO());

        return FileLocationResponse
                .builder()
                .fileLocationUUID(fileLocationUUID)
                .fileMetadataResponse(
                        FileLocationResponse.FileMetadataResponse
                                .builder()
                                .fileMetadataUUID(fileMetadataUUID)
                                .build())
                .build();
    }
}
