package com.filestorage.core.service;

import com.filestorage.adapter.dto.FileMetadataConverter;
import com.filestorage.adapter.dto.FileMetadataDTO;
import com.filestorage.core.repository.FileMetadataRepository;
import com.filestorage.domain.FileMetadata;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FileMetadataServiceImpl implements FileMetadataService {

    private final FileMetadataRepository repository;
    private final FileMetadataConverter converter;

    @Override
    public UUID create(FileMetadataDTO dto) {
        FileMetadata fileMetadata = converter.toEntity(dto);
        return repository.save(fileMetadata).getId();
    }

    @Override
    public Optional<FileMetadataDTO> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public Optional<FileMetadataDTO> update(FileMetadataDTO dto) {
        return Optional.empty();
    }

    @Override
    public Boolean delete(UUID uuid) {
        return null;
    }

}
