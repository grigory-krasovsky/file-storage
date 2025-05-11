package com.filestorage.adapter.controller;

import com.filestorage.adapter.dto.request.FileAccessCreateRequest;
import com.filestorage.adapter.dto.request.FileAccessGetRequest;
import com.filestorage.adapter.dto.response.FileAccessGetResponse;
import com.filestorage.core.service.FileAccessManager;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/file/access")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileAccessController extends AbstractController {

    FileAccessManager fileAccessManager;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createFileAccess(@ModelAttribute FileAccessCreateRequest fileAccessCreateRequest) {
        fileAccessManager.createFileAccess(fileAccessCreateRequest);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/download")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Resource> getFile(@RequestBody FileAccessGetRequest request) {
        FileAccessGetResponse fileAccess = fileAccessManager.getFileAccess(request);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        String.format("attachment; filename=\"%s\"", fileAccess.getFileName()))
                .body(fileAccess.getResource());
    }
}
