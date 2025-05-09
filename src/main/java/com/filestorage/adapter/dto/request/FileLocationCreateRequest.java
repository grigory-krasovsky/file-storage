package com.filestorage.adapter.dto.request;

import com.filestorage.adapter.dto.FileLocationDTO;
import com.filestorage.adapter.dto.FileMetadataDTO;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
public class FileLocationCreateRequest {
    private final FileMetadataDTO fileMetadataDTO;

    public FileLocationCreateRequest(FileMetadataDTO fileMetadataDTO) {
        this.fileMetadataDTO = fileMetadataDTO;

        if (fileMetadataDTO.getFileDateTime() == null) {
            fileMetadataDTO.setFileDateTime(OffsetDateTime.now());
        }

        if (fileMetadataDTO.getFileLocationDTO() == null) {
            fileMetadataDTO.setFileLocationDTO(FileLocationDTO.builder().build());
        }
    }
}
