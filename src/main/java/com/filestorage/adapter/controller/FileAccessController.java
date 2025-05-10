package com.filestorage.adapter.controller;

import com.filestorage.adapter.dto.request.FileAccessCreateRequest;
import com.filestorage.core.service.FileAccessManager;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
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
}
