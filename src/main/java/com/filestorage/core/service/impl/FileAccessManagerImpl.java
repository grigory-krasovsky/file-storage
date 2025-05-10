package com.filestorage.core.service.impl;

import com.filestorage.adapter.dto.request.FileAccessCreateRequest;
import com.filestorage.core.exception.FileUploadException;
import com.filestorage.core.exception.enums.ErrorType;
import com.filestorage.core.service.FileAccessManager;
import com.filestorage.core.service.FileLocationManager;
import com.filestorage.domain.entity.FileLocation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
}
