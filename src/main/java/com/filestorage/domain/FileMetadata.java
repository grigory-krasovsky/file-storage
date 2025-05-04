package com.filestorage.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "file_metadata")
public class FileMetadata extends AbstractEntity {

    @OneToOne
    @JoinColumn
    @NonNull
    FileLocation fileLocation;

    String author;
    String comment;

    OffsetDateTime fileDateTime;
}
