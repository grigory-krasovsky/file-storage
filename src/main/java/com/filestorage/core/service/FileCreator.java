package com.filestorage.core.service;

import com.filestorage.adapter.request.FileCreateRequest;
import com.filestorage.adapter.response.FileLocationResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FileCreator {

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
