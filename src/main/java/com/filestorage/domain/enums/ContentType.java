package com.filestorage.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContentType {
    VIDEO_MP4("video/mp4"),
    IMG_JPG("image/jpeg");
    private final String representation;

}
