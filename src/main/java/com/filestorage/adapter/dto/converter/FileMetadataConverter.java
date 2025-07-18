package com.filestorage.adapter.dto.converter;

import com.filestorage.adapter.dto.FileMetadataDTO;
import com.filestorage.domain.entity.FileLocation;
import com.filestorage.domain.entity.FileMetadata;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FileMetadataConverter implements Converter<FileMetadata, FileMetadataDTO> {

    private final FileLocationConverter fileLocationConverter;
    @Override
    public @NonNull FileMetadata toEntity(FileMetadataDTO fileMetadataDTO) {

        return FileMetadata.builder()
                .id(fileMetadataDTO.getId())
                .createdAt(fileMetadataDTO.getCreatedAt())
                .author(fileMetadataDTO.getAuthor())
                .comment(fileMetadataDTO.getComment())
                .fileDateTime(fileMetadataDTO.getFileDateTime())
                .size(fileMetadataDTO.getSize())
                .fileName(fileMetadataDTO.getFileName())
                .contentType(fileMetadataDTO.getContentType())
                .relevant(fileMetadataDTO.getRelevant())
                .fileLocation(
                        FileLocation.builder()
                                .id(fileMetadataDTO.getFileLocationDTO().getId())
                                .build()
                )
                .build();
    }

    @Override
    public @NonNull FileMetadataDTO toDto(FileMetadata fileMetadata) {
        return FileMetadataDTO.builder()
                .id(fileMetadata.getId())
                .createdAt(fileMetadata.getCreatedAt())
                .author(fileMetadata.getAuthor())
                .comment(fileMetadata.getComment())
                .fileDateTime(fileMetadata.getFileDateTime())
                .fileName(fileMetadata.getFileName())
                .size(fileMetadata.getSize())
                .contentType(fileMetadata.getContentType())
                .relevant(fileMetadata.getRelevant())
                .fileLocationDTO(fileLocationConverter.toDto(fileMetadata.getFileLocation()))
                .build();
    }
}
