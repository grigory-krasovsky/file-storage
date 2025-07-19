package com.filestorage.adapter.controller;

import com.filestorage.adapter.dto.request.FileLocationCreateRequest;
import com.filestorage.adapter.dto.response.FileLocationGetResponse;
import com.filestorage.core.service.FileLocationManager;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;


@RestController
@AllArgsConstructor
@RequestMapping("/api/file/location")
public class FileLocationController extends AbstractController {

    private final FileLocationManager fileLocationManager;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FileLocationGetResponse create(@RequestBody FileLocationCreateRequest fileLocationCreateRequest) {
        return fileLocationManager.createFileLocation(fileLocationCreateRequest);
    }

    @GetMapping
    public Page<FileLocationGetResponse> getAllFiles(@RequestParam Integer pageSize, @RequestParam Integer pageNumber) {
        Random r = new Random();
        if (r.nextInt() % 2 == 0) {
            throw new RuntimeException("");
        }
        return fileLocationManager.getAllFileLocationWithMetadataWithStatusesPageable(Pageable.ofSize(pageSize).withPage(pageNumber));
    }
}
