package com.filestorage.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileStatusType {

    UPLOAD_READY("Ожидание загрузки"),
    UPLOAD_SUCCESS("Загрузка завершена"),
    UPLOAD_ERROR("Ошибка загрузки"),
    UPLOAD_STARTED("Загрузка началась");
    private final String representation;
}
