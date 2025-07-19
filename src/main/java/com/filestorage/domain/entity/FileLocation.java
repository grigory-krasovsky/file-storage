package com.filestorage.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "file_location")
public class FileLocation extends AbstractEntity {
    String filePath;
    UUID parent;
}
