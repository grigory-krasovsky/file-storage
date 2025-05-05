package com.filestorage.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "error_log")
public class ErrorLog extends AbstractEntity {
    @NonNull
    private String serviceName;

    private String stackTrace;
    private String requestEndpoint;
    private String details;
}
