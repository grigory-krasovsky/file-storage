package com.filestorage.adapter.dto.converter;

import com.filestorage.adapter.dto.FileMetadataDTO;
import com.filestorage.adapter.dto.FileStatusDTO;
import com.filestorage.domain.entity.FileMetadata;
import com.filestorage.domain.entity.FileStatus;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class FileStatusConverter implements Converter<FileStatus, FileStatusDTO>{
    @Override
    public @NonNull FileStatusDTO toDto(FileStatus entity) {
        return FileStatusDTO.builder()
                .fileStatusType(entity.getStatus())
                .build();
    }

    @Override
    public @NonNull FileStatus toEntity(FileStatusDTO dto) {
        return null;
    }
}
