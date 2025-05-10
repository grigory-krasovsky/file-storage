package com.filestorage.adapter.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileAccessCreateRequest {
    @NonNull
    UUID id;
    @NonNull
    MultipartFile contents;

}
