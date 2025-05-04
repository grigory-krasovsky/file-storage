package com.filestorage.core.service.validator;

import com.filestorage.domain.ErrorLog;
import org.springframework.stereotype.Service;

@Service
public class ErrorValidator implements EntityValidator<ErrorLog> {
    @Override
    public void correct(ErrorLog entity) {
        correctCreatedAt(entity);
    }
}
