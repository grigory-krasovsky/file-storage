package com.filestorage.core.service;

import com.filestorage.adapter.dto.FileLocationConverter;
import com.filestorage.adapter.dto.FileLocationDTO;
import com.filestorage.core.repository.FileLocationRepository;
import com.filestorage.domain.FileLocation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FileLocationServiceImpl implements FileLocationService {

    FileLocationRepository repository;
    FileLocationConverter converter;

    //todo probably add additional actions like saving other corresponding entities
    @Override
    public UUID create(FileLocationDTO dto) {
        FileLocation fileLocation = converter.toEntity(dto);
        //todo move to validator
        if (fileLocation.getCreatedAt() == null) {
            fileLocation.setCreatedAt(OffsetDateTime.now());
        }

        return repository.save(fileLocation).getId();
    }

    @Override
    public Optional<FileLocationDTO> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public Optional<FileLocationDTO> update(FileLocationDTO dto) {
        return Optional.empty();
    }

    @Override
    public Boolean delete(UUID uuid) {
        return null;
    }

}
