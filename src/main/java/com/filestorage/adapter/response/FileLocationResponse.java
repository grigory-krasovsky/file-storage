package com.filestorage.adapter.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class FileLocationResponse {
    private UUID fileLocationUUID;
    private FileMetadataResponse fileMetadataResponse;
    @Getter
    @Builder
    public static class FileMetadataResponse {
        private UUID fileMetadataUUID;
    }
}
