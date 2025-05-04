package com.filestorage.adapter.dto;

import com.filestorage.domain.AbstractEntity;
import lombok.NonNull;

public interface Converter<E extends AbstractEntity, D extends AbstractDTO> {
    @NonNull
    D toDto(E entity);
    @NonNull
    E toEntity(D dto);
}
