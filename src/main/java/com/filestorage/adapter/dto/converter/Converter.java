package com.filestorage.adapter.dto.converter;

import com.filestorage.adapter.dto.AbstractDTO;
import com.filestorage.domain.entity.AbstractEntity;
import lombok.NonNull;

import java.util.UUID;

public interface Converter<E extends AbstractEntity, D extends AbstractDTO> {
    @NonNull
    D toDto(E entity);
    @NonNull
    E toEntity(D dto);
}
