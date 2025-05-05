package com.filestorage.core.service.validator;

import com.filestorage.domain.entity.AbstractEntity;

import java.time.OffsetDateTime;

public interface EntityValidator<E extends AbstractEntity> {
    void correct(E entity);

    default void correctCreatedAt(E entity) {
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(OffsetDateTime.now());
        }
    }
}
