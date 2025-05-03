package com.filestorage.adapter.controller;

import com.filestorage.adapter.dto.FileLocationDTO;
import com.filestorage.core.service.CRUDService;
import com.filestorage.domain.FileLocation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/files")
public class FileLocationController extends AbstractController<FileLocation, FileLocationDTO> {

    public FileLocationController(CRUDService<FileLocation, FileLocationDTO> service) {
        super(service);
    }
}
