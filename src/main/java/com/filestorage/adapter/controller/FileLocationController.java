package com.filestorage.adapter.controller;

import com.filestorage.adapter.dto.request.FileCreateRequest;
import com.filestorage.adapter.dto.response.FileLocationResponse;
import com.filestorage.core.service.FileCreator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/files")
public class FileLocationController extends AbstractController {

    private final FileCreator fileCreator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FileLocationResponse create(@RequestBody FileCreateRequest fileCreateRequest) {
        return fileCreator.create(fileCreateRequest);
    }
}
