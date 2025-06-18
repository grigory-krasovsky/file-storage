package com.filestorage.adapter.dto;

import com.filestorage.domain.enums.FileStatusType;
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
public class FileStatusDTO extends AbstractDTO {

    FileLocationDTO fileLocationDTO;
    FileStatusType fileStatusType;
}
