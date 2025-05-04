package com.filestorage.core.service;

import com.filestorage.adapter.dto.AbstractDTO;
import com.filestorage.domain.AbstractEntity;

import java.util.Optional;
import java.util.UUID;

public interface CRUDService<E extends AbstractEntity> {
    E create(E entity);
    Optional<E> findById(UUID uuid);
    Optional<E> update(E entity);
    Boolean delete(UUID uuid);
}
