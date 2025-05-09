package com.filestorage.core.service.impl;

import com.filestorage.core.service.CRUDService;
import com.filestorage.core.service.validator.EntityValidator;
import com.filestorage.domain.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class AbstractEntityService<E extends AbstractEntity, V extends EntityValidator<E>, R extends JpaRepository<E, UUID>> implements CRUDService<E> {

    private final V validator;
    private final R repository;

    @Override
    public E create(E entity) {
        validator.correct(entity);
        return repository.save(entity);
    }

    @Override
    public Optional<E> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public Optional<E> update(E entity) {
        return Optional.of(repository.save(entity));
    }

    @Override
    public Boolean delete(UUID uuid) {
        return null;
    }
}
