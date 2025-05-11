package com.filestorage.core.service.impl;

import com.filestorage.adapter.dto.FileMetadataDTO;
import com.filestorage.adapter.dto.request.FileAccessCreateRequest;
import com.filestorage.adapter.dto.request.FileAccessGetRequest;
import com.filestorage.adapter.dto.response.FileAccessGetResponse;
import com.filestorage.core.exception.FileAccessException;
import com.filestorage.core.exception.FileUploadException;
import com.filestorage.core.exception.enums.ErrorType;
import com.filestorage.core.service.FileAccessManager;
import com.filestorage.core.service.FileLocationManager;
import com.filestorage.domain.entity.FileLocation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.filestorage.core.exception.FileAccessException.*;
import static com.filestorage.core.exception.FileUploadException.UNABLE_TO_CREATE_DIRECTORY;
import static com.filestorage.core.exception.FileUploadException.UNABLE_TO_CREATE_FILE;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileAccessManagerImpl implements FileAccessManager {
    FileLocationManager fileLocationManager;

    @Override
    @Transactional
    public void createFileAccess(FileAccessCreateRequest request) {

        FileLocation fileLocation = fileLocationManager.beforeCreate(request);

        Path filePath = Paths.get(fileLocation.getFilePath());

        try {
            Files.createDirectories(filePath.getParent());
        } catch (IOException e) {
            throw new FileUploadException(fileLocation.getId(), ErrorType.SYSTEM_ERROR, UNABLE_TO_CREATE_DIRECTORY(filePath.getParent().toString()));
        }

        try {
            Files.write(filePath, request.getContents().getBytes());
        } catch (IOException e) {
            throw new FileUploadException(fileLocation.getId(), ErrorType.SYSTEM_ERROR, UNABLE_TO_CREATE_FILE(filePath.toString()));
        }

        fileLocationManager.afterCreate(request);
    }

    @Override
    public FileAccessGetResponse getFileAccess(FileAccessGetRequest request) {
        UUID locationId = request.getId();
        FileMetadataDTO fileLocationWithMetadata = fileLocationManager.getFileLocationWithMetadata(locationId);

        Path filePath = Paths.get(fileLocationWithMetadata.getFileLocationDTO().getFilePath()).normalize();

        try {
            UrlResource resource = new UrlResource(filePath.toUri());
            if (!Files.exists(filePath)) {
                throw new FileAccessException(ErrorType.SYSTEM_ERROR, NO_FILE(filePath.toString()));
            }
            if (!resource.isReadable()) {
                throw new FileAccessException(ErrorType.SYSTEM_ERROR, UNABLE_TO_READ(filePath.toString()));
            }
            return FileAccessGetResponse.builder()
                    .id(locationId)
                    .contentType(fileLocationWithMetadata.getContentType())
                    .fileName(fileLocationWithMetadata.getFileName())
                    .resource(resource)
                    .build();

        } catch (MalformedURLException e) {
            throw new FileAccessException(ErrorType.SYSTEM_ERROR, MALFORMED_PATH(filePath.toString()));
        }
    }
}
