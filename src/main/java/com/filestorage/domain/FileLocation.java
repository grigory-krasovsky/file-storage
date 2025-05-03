package com.filestorage.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Entity
@Table(name = "file_location")
@SuperBuilder
@Getter
@Setter
public class FileLocation extends AbstractEntity {

    String filePath;
    @NonNull
    OffsetDateTime createdAt;

    public FileLocation() {
        super();
    }
}
