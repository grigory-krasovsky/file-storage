package com.filestorage.adapter.dto.converter;

import com.filestorage.adapter.dto.FileLocationDTO;
import com.filestorage.domain.FileLocation;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class FileLocationConverter implements Converter<FileLocation, FileLocationDTO> {
    @Override
    public @NonNull FileLocation toEntity(FileLocationDTO fileLocationDTO) {
        return FileLocation.builder()
                .filePath(fileLocationDTO.getFilePath())
                .id(fileLocationDTO.getId())
                .createdAt(fileLocationDTO.getCreatedAt())
                .build();
    }
    @Override
    public @NonNull FileLocationDTO toDto(FileLocation fileLocation) {
        return FileLocationDTO.builder()
                .id(fileLocation.getId())
                .createdAt(fileLocation.getCreatedAt())
                .filePath(fileLocation.getFilePath())
                .build();
    }


}
