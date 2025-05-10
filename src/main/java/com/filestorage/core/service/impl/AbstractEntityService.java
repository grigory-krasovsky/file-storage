package com.filestorage.core.service.impl;

import com.filestorage.core.exception.DataBaseException;
import com.filestorage.core.exception.enums.ErrorType;
import com.filestorage.core.service.CRUDService;
import com.filestorage.core.service.validator.EntityValidator;
import com.filestorage.domain.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

import static com.filestorage.core.exception.DataBaseException.ENTITY_IS_ABSENT_MESSAGE;

@AllArgsConstructor
public class AbstractEntityService<E extends AbstractEntity, V extends EntityValidator<E>, R extends JpaRepository<E, UUID>> implements CRUDService<E> {

    protected final V validator;
    protected final R repository;

    @Override
    public E create(E entity) {
        validator.correct(entity);
        return repository.save(entity);
    }

    @Override
    public E findById(UUID uuid) {
        return repository.findById(uuid).orElseThrow(() -> new DataBaseException(ErrorType.SYSTEM_ERROR, ENTITY_IS_ABSENT_MESSAGE(uuid, this.getClass())));
    }

    @Override
    public Optional<E> update(E entity) {
        return Optional.of(repository.save(entity));
    }

    @Override
    public Boolean delete(UUID uuid) {
        return null;
    }

    @Override
    public Boolean exists(UUID uuid) {
        return repository.existsById(uuid);
    }
}
