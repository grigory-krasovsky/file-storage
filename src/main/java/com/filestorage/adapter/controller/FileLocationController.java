package com.filestorage.adapter.controller;

import com.filestorage.adapter.dto.request.FileLocationCreateRequest;
import com.filestorage.adapter.dto.response.FileLocationGetResponse;
import com.filestorage.core.service.FileLocationManager;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/files")
public class FileLocationController extends AbstractController {

    private final FileLocationManager fileLocationManager;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FileLocationGetResponse create(@RequestBody FileLocationCreateRequest fileLocationCreateRequest) {
        return fileLocationManager.createFileLocation(fileLocationCreateRequest);
    }
}
