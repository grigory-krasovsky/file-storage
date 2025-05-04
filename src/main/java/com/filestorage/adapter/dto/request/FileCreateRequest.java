package com.filestorage.adapter.dto.request;

import com.filestorage.adapter.dto.FileLocationDTO;
import com.filestorage.adapter.dto.FileMetadataDTO;
import lombok.Getter;

@Getter
public class FileCreateRequest {
    private final FileLocationDTO fileLocationDTO;
    private final FileMetadataDTO fileMetadataDTO;

    public FileCreateRequest(FileLocationDTO fileLocationDTO, FileMetadataDTO fileMetadataDTO) {
        this.fileLocationDTO = fileLocationDTO;
        this.fileMetadataDTO = fileMetadataDTO;
        if (fileMetadataDTO.getFileLocationDTO() == null) {
            fileMetadataDTO.setFileLocationDTO(FileLocationDTO.builder().build());
        }
    }
}
