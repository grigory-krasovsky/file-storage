package com.filestorage.adapter.dto;

import com.filestorage.domain.FileLocation;
import org.springframework.stereotype.Service;

@Service
public class FileLocationConverter implements Converter<FileLocation, FileLocationDTO> {
    @Override
    public FileLocation toEntity(FileLocationDTO fileLocationDTO) {
        return FileLocation.builder()
                .filePath(fileLocationDTO.getFilePath())
                .id(fileLocationDTO.getId())
                .createdAt(fileLocationDTO.getCreatedAt())
                .build();
    }
    @Override
    public FileLocationDTO toDto(FileLocation fileLocation) {
        return FileLocationDTO.builder()
                .id(fileLocation.getId())
                .createdAt(fileLocation.getCreatedAt())
                .filePath(fileLocation.getFilePath())
                .build();
    }


}
