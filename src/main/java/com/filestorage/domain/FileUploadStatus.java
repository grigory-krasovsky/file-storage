package com.filestorage.domain;

import com.filestorage.domain.enums.UploadStatus;
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
@Table(name = "file_upload_status")
public class FileUploadStatus extends AbstractEntity {
    @OneToOne
    @NonNull
    FileLocation fileLocation;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "TEXT")
    @NonNull
    private UploadStatus status;

    @Column(name = "stack_trace")
    private String stacktrace;
}
