package com.filestorage.adapter.dto;

import com.filestorage.domain.enums.UploadStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FileUpdateStatusDTO extends AbstractDTO {

    FileLocationDTO fileLocationDTO;
    UploadStatus uploadStatus;
    String stacktrace;
}
