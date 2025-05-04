package com.filestorage.core.service.impl;

import com.filestorage.core.service.ErrorLogService;
import com.filestorage.core.service.validator.ErrorValidator;
import com.filestorage.domain.ErrorLog;
import com.filestorage.domain.repository.ErrorLogRepository;
import org.springframework.stereotype.Service;

@Service
public class ErrorLogServiceImpl extends AbstractEntityService<ErrorLog, ErrorValidator, ErrorLogRepository>
        implements ErrorLogService {
    public ErrorLogServiceImpl(ErrorValidator validator, ErrorLogRepository repository) {
        super(validator, repository);
    }
}
