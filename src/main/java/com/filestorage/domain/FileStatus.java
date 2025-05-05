package com.filestorage.domain;

import com.filestorage.domain.enums.FileStatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "file_status")
public class FileStatus extends AbstractEntity {
    @OneToOne
    @NonNull
    FileLocation fileLocation;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "TEXT")
    @NonNull
    private FileStatusType status;

    @Column(name = "stack_trace")
    private String stacktrace;
}
