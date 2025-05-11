package com.filestorage.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FileMetadataDTO extends AbstractDTO {

    FileLocationDTO fileLocationDTO;
    String author;
    String comment;
    String fileName;
    String contentType;
    OffsetDateTime fileDateTime;
}
