package com.filestorage.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UploadStatus {

    SUCCESS("Успешно"),
    ERROR("Ошибка"),
    UPLOAD_STARTED("Загрузка началась");
    private final String representation;
}
