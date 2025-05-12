package com.filestorage.core.service.validator;

import com.filestorage.domain.entity.ErrorLog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ErrorValidator implements EntityValidator<ErrorLog> {
    @Override
    public void correctForCreate(ErrorLog entity) {
        correctCreatedAt(entity);
    }

    @Override
    public void correctForUpdate(ErrorLog entity) {

    }

    @Override
    public void correctForBatchSave(List<ErrorLog> entities) {
        entities.forEach(this::correctCreatedAt);
    }
}
