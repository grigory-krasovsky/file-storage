package com.filestorage.adapter.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class FileLocationResponse {
    private UUID fileLocationUUID;
    private FileMetadataResponse fileMetadataResponse;
    private FileStatusResponse fileStatusResponse;

    @Getter
    @Builder
    public static class FileMetadataResponse {
        private UUID fileMetadataUUID;
    }

    @Getter
    @Builder
    public static class FileStatusResponse {
        private UUID fileUploadStatusUUID;
    }
}
