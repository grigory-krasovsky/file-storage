package com.filestorage.adapter.dto.converter;

import com.filestorage.adapter.dto.FileLocationDTO;
import com.filestorage.domain.entity.FileLocation;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FileLocationConverter implements Converter<FileLocation, FileLocationDTO> {
    @Override
    public @NonNull FileLocation toEntity(FileLocationDTO fileLocationDTO) {
        return FileLocation.builder()
                .filePath(fileLocationDTO.getFilePath())
                .id(fileLocationDTO.getId())
                .createdAt(fileLocationDTO.getCreatedAt())
                .parent(fileLocationDTO.getParent())
                .build();
    }

    @Override
    public @NonNull FileLocationDTO toDto(FileLocation fileLocation) {
        return FileLocationDTO.builder()
                .id(fileLocation.getId())
                .createdAt(fileLocation.getCreatedAt())
                .parent(fileLocation.getParent())
                .filePath(fileLocation.getFilePath())
                .build();
    }


}
