package com.filestorage.adapter.dto;

import com.filestorage.domain.AbstractEntity;

public interface Converter<E extends AbstractEntity, D extends AbstractDTO> {
    D toDto(E entity);
    E toEntity(D dto);
}
