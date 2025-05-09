package com.filestorage.core.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class CommonUtils {
    private final static DateTimeFormatter filePathDateFormatter = DateTimeFormatter.ofPattern("yyyy_MM_dd");
    private final static DateTimeFormatter filePathTimeFormatter = DateTimeFormatter.ofPattern("HH_mm");
    public String format(OffsetDateTime offsetDateTime, DateTimeFormatter formatter) {
        return offsetDateTime.format(formatter);
    }

    public String getDatePart(OffsetDateTime offsetDateTime) {
        return format(offsetDateTime, filePathDateFormatter);
    }

    public String getTimePart(OffsetDateTime offsetDateTime) {
        return format(offsetDateTime, filePathTimeFormatter);
    }
}
