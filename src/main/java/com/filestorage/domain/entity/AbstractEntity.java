package com.filestorage.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()")
    UUID id;
    OffsetDateTime createdAt;
}
