package com.filestorage.adapter.controller;

import com.filestorage.adapter.dto.request.FileAccessBatchDeleteRequest;
import com.filestorage.adapter.dto.request.FileAccessDeleteRequest;
import com.filestorage.adapter.dto.request.FileAccessSaveRequest;
import com.filestorage.adapter.dto.request.FileAccessGetRequest;
import com.filestorage.adapter.dto.response.FileAccessGetResponse;
import com.filestorage.core.service.FileAccessManager;
import com.filestorage.core.service.FileLocationManager;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/file/access")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileAccessController extends AbstractController {

    FileAccessManager fileAccessManager;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createFileAccess(@ModelAttribute FileAccessSaveRequest fileAccessSaveRequest) {
        fileAccessManager.createFileAccess(fileAccessSaveRequest);
        return ResponseEntity.ok("Success");
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> updateFileAccess(@ModelAttribute FileAccessSaveRequest fileAccessSaveRequest) {
        fileAccessManager.updateFileAccess(fileAccessSaveRequest);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/download")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Resource> getFile(@RequestBody FileAccessGetRequest request) {
        FileAccessGetResponse fileAccess = fileAccessManager.getFileAccess(request);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileAccess.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        String.format("attachment; filename=\"%s\"", fileAccess.getFileName()))
                .body(fileAccess.getResource());
    }

    @GetMapping(value = "/preview/{parent}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> getPreview(@PathVariable UUID parent) {
        return fileAccessManager.getPreview(parent)
                .map(ResponseEntity::ok).orElse(
                ResponseEntity.notFound().build()
        );
    }


    @PostMapping("/stream")
    public ResponseEntity<String> handleLargeFileUpload(
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteFile(@RequestBody FileAccessDeleteRequest request) {
        fileAccessManager.deleteFileAccess(request.getId());
        return ResponseEntity.ok("Deleted");
    }
    @DeleteMapping("/batch")
    public ResponseEntity<String> deleteFiles(@RequestBody FileAccessBatchDeleteRequest request) {
        List<UUID> uuids = fileAccessManager.deleteFileAccess(request);
        String errorMsg = String.format("Unable to delete: %s", uuids);
        String successMsg = "Deleted";
        return ResponseEntity.ok(uuids.isEmpty() ? successMsg : errorMsg);
    }
}
