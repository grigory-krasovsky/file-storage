package com.filestorage.adapter.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileAccessCreateRequest {
    UUID id;

}
