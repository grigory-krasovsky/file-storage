package com.filestorage.adapter.controller;


import com.filestorage.adapter.dto.AbstractDTO;
import com.filestorage.core.service.CRUDService;
import com.filestorage.domain.AbstractEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
public class AbstractController<E extends AbstractEntity, D extends AbstractDTO> {
    protected final CRUDService<E, D> service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID create(@RequestBody D dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public D getById(@PathVariable UUID id) {
        return service.findById(id).orElseThrow(() -> new RuntimeException(""));
    }
}
