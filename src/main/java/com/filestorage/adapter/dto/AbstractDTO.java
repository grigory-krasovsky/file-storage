package com.filestorage.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor  // ← Required for Jackson
@AllArgsConstructor // ← Optional but useful
public class AbstractDTO {
    UUID id;
}
