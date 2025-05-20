package com.filestorage.core.service.impl;

import com.filestorage.adapter.dto.FileMetadataDTO;
import com.filestorage.adapter.dto.request.FileAccessBatchDeleteRequest;
import com.filestorage.adapter.dto.request.FileAccessDeleteRequest;
import com.filestorage.adapter.dto.request.FileAccessSaveRequest;
import com.filestorage.adapter.dto.request.FileAccessGetRequest;
import com.filestorage.adapter.dto.response.FileAccessGetResponse;
import com.filestorage.core.exception.FileAccessException;
import com.filestorage.core.exception.FileUploadException;
import com.filestorage.core.exception.enums.ErrorType;
import com.filestorage.core.service.FileAccessManager;
import com.filestorage.core.service.FileLocationManager;
import com.filestorage.domain.entity.FileLocation;
import com.filestorage.grpc.GrpcFileAccessSaveRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public void createFileAccess(FileAccessSaveRequest request) {

        FileLocation fileLocation = fileLocationManager.beforeCreate(request);

        createFile(fileLocation, request);

        fileLocationManager.afterSave(request);
    }

    @Override
    public void createFileAccess(GrpcFileAccessSaveRequest request) {
        FileLocation fileLocation = fileLocationManager.beforeCreate(request);

        createFile(fileLocation, request);

        fileLocationManager.afterSave(request);
        System.out.println("file saved");
    }

    @Override
    public void updateFileAccess(FileAccessSaveRequest request) {

        FileLocation fileLocation = fileLocationManager.beforeUpdate(request);

        createFile(fileLocation, request);

        fileLocationManager.afterSave(request);
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

    @Override
    public Boolean deleteFileAccess(UUID id) {

        FileLocation fileLocation = fileLocationManager.beforeDelete(id);
        deleteFile(fileLocation);
        fileLocationManager.afterDelete(id);
        return true;
    }

    @Override
    public List<UUID> deleteFileAccess(FileAccessBatchDeleteRequest request) {
        return request.getIds().stream().map(id -> {
            if (fileLocationManager.deletable(id)) {
                try {
                    deleteFileAccess(id);
                } catch (FileAccessException e) {
                    return null;
                }
                return null;
            }
            return id;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private void createFile(FileLocation fileLocation, FileAccessSaveRequest request) {
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
    }

    private void createFile(FileLocation fileLocation, GrpcFileAccessSaveRequest request) {
        Path filePath = Paths.get(fileLocation.getFilePath());

        try {
            Files.createDirectories(filePath.getParent());
        } catch (IOException e) {
            throw new FileUploadException(fileLocation.getId(), ErrorType.SYSTEM_ERROR, UNABLE_TO_CREATE_DIRECTORY(filePath.getParent().toString()));
        }

        try {
            Files.write(filePath, request.getContents().toByteArray());
        } catch (IOException e) {
            throw new FileUploadException(fileLocation.getId(), ErrorType.SYSTEM_ERROR, UNABLE_TO_CREATE_FILE(filePath.toString()));
        }
    }

    private void deleteFile(FileLocation fileLocation) {

        Path filePath = Paths.get(fileLocation.getFilePath()).normalize();
        try {
            if (!Files.exists(filePath)) {
                throw new FileAccessException(ErrorType.SYSTEM_ERROR, NO_FILE(filePath.toString()));
            }
            Files.delete(filePath);
            deleteDirectory(filePath);
        } catch (MalformedURLException e) {
            throw new FileAccessException(ErrorType.SYSTEM_ERROR, MALFORMED_PATH(filePath.toString()));
        } catch (IOException e) {
            throw new FileAccessException(ErrorType.SYSTEM_ERROR, UNABLE_TO_DELETE(filePath.toString()));
        }
    }

    private void deleteDirectory(Path filePath) {
        Path directory = filePath.getParent();
        while (directory != null && Files.isDirectory(directory)) {
            try {
                Files.delete(directory);
                directory = directory.getParent();
            } catch (DirectoryNotEmptyException e) {
                break;
            } catch (IOException e) {
                throw new FileAccessException(ErrorType.SYSTEM_ERROR, UNABLE_TO_DELETE(filePath.toString()));
            }
        }
    }
}
