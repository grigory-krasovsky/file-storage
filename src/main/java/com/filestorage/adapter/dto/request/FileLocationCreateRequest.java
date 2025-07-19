package com.filestorage.adapter.dto.request;

import com.filestorage.adapter.dto.FileLocationDTO;
import com.filestorage.adapter.dto.FileMetadataDTO;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
public class FileLocationCreateRequest {
    private final FileMetadataDTO fileMetadataDTO;
    private final UUID mainFileLocationUuid;

    public FileLocationCreateRequest(FileMetadataDTO fileMetadataDTO, UUID mainFileLocationUuid) {
        this.fileMetadataDTO = fileMetadataDTO;
        this.mainFileLocationUuid = mainFileLocationUuid;

        if (fileMetadataDTO.getFileDateTime() == null) {
            fileMetadataDTO.setFileDateTime(OffsetDateTime.now());
        }

        if (fileMetadataDTO.getFileLocationDTO() == null) {
            fileMetadataDTO.setFileLocationDTO(FileLocationDTO.builder().build());
        }
        if (fileMetadataDTO.getRelevant() == null) {
            fileMetadataDTO.setRelevant(true);
        }
    }
}
