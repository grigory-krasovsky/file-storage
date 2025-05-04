package com.filestorage.core.service.validator;

import com.filestorage.domain.AbstractEntity;

public interface EntityValidator<E extends AbstractEntity> {
    void correct(E entity);
}
