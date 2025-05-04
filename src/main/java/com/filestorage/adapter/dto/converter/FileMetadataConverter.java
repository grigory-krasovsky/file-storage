package com.filestorage.adapter.dto.converter;

import com.filestorage.adapter.dto.FileMetadataDTO;
import com.filestorage.domain.FileLocation;
import com.filestorage.domain.FileMetadata;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

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
                .fileLocationDTO(fileLocationConverter.toDto(fileMetadata.getFileLocation()))
                .build();
    }
}
