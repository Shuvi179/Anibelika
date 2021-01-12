package com.orion.anibelika.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.content.fs.config.EnableFilesystemStores;
import org.springframework.content.fs.io.FileSystemResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
@EnableFilesystemStores
public class AudioStorageConfig {

    @Value("${anibelica.image.audio.url.root}")
    private String audioPath;

    @Bean
    File filesystemRoot() {
        try {
            return Files.createDirectories(Paths.get(audioPath)).toFile();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    @Bean
    public FileSystemResourceLoader fsResourceLoader() {
        return new FileSystemResourceLoader(filesystemRoot().getAbsolutePath());
    }
}

