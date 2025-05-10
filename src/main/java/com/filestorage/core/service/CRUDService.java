package com.filestorage.core.service;

import com.filestorage.domain.entity.AbstractEntity;

import java.util.Optional;
import java.util.UUID;

public interface CRUDService<E extends AbstractEntity> {
    E create(E entity);
    E findById(UUID uuid);
    Optional<E> update(E entity);
    Boolean delete(UUID uuid);

    Boolean exists(UUID uuid);
}
