package com.filestorage.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileStatusType {

    UPLOAD_READY("Ожидание загрузки"),
    UPLOAD_SUCCESS("Загрузка завершена"),
    UPLOAD_ERROR("Ошибка загрузки"),
    DELETION_ERROR("Ошибка удаления"),
    UPLOAD_STARTED("Загрузка началась"),
    TO_BE_DELETED("Ожидание удаления"),
    DELETED("Файл удален"),
    UPDATE_UPLOAD_STARTED("Обновление началось");
    private final String representation;
}
