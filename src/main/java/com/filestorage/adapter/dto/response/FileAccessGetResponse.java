package com.filestorage.adapter.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.core.io.Resource;

import java.util.UUID;

@Builder
@Getter
public class FileAccessGetResponse {
    UUID id;
    Resource resource;
    String fileName;
    String contentType;
}
