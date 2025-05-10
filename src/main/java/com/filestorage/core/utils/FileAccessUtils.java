package com.filestorage.core.utils;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
@AllArgsConstructor
public class FileAccessUtils {
    private Environment env;
    public String getDefaultDelimiter() {
        return File.separator;
    }

    public String getDefaultRootPath() {
        String rootPath = env.getProperty("ROOT_PATH");
        if (rootPath != null && !rootPath.isBlank()) {
            return rootPath;
        }
        return "C:\\Users\\User\\IdeaProjects\\file-storage-default-volume";
    }

    public Path buildPath(List<String> pathChunks) {

        return pathChunks.stream().reduce(Paths.get(""), Path::resolve, Path::resolve);

//        for (String chunk : pathChunks) {
//            path = path.resolve(chunk);
//        }
//        return path;
    }
}
