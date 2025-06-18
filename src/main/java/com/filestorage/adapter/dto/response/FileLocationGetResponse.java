package com.filestorage.adapter.dto.response;

import com.filestorage.adapter.dto.FileMetadataDTO;
import com.filestorage.adapter.dto.FileStatusDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class FileLocationGetResponse {
    private UUID fileLocationUUID;
    private FileMetadataResponse fileMetadataResponse;
    private FileStatusResponse fileStatusResponse;

    @Getter
    @Builder
    public static class FileMetadataResponse {
        private UUID fileMetadataUUID;
        private FileMetadataDTO fileMetadataDTO;
    }

    @Getter
    @Builder
    public static class FileStatusResponse {
        private UUID fileUploadStatusUUID;
        private FileStatusDTO fileStatusDTO;
    }
}
