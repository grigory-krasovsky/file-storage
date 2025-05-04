package com.filestorage.adapter.dto.request;

import com.filestorage.adapter.dto.FileLocationDTO;
import com.filestorage.adapter.dto.FileMetadataDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FileCreateRequest {
    private final FileLocationDTO fileLocationDTO;
    private final FileMetadataDTO fileMetadataDTO;
}
