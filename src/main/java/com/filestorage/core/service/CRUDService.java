package com.filestorage.core.service;

import com.filestorage.adapter.dto.AbstractDTO;
import com.filestorage.domain.AbstractEntity;

import java.util.Optional;
import java.util.UUID;

public interface CRUDService<E extends AbstractEntity, D extends AbstractDTO> {
    UUID create(D dto);
    Optional<D> findById(UUID uuid);
    Optional<D> update(D dto);
    Boolean delete(UUID uuid);
}
