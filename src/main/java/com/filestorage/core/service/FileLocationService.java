package com.filestorage.core.service;

import com.filestorage.domain.entity.FileLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;


public interface FileLocationService extends CRUDService<FileLocation> {

    List<FileLocation> getPotentialPreviews(UUID parentId);
    Page<FileLocation> findAllByParentIsNull(Pageable pageable);

}
