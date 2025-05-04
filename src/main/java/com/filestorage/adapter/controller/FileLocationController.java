package com.filestorage.adapter.controller;

import com.filestorage.adapter.dto.FileLocationDTO;
import com.filestorage.adapter.request.FileCreateRequest;
import com.filestorage.adapter.response.FileLocationResponse;
import com.filestorage.core.service.CRUDService;
import com.filestorage.core.service.FileCreator;
import com.filestorage.domain.FileLocation;
import lombok.AllArgsConstructor;
import lombok.NonNull;
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
