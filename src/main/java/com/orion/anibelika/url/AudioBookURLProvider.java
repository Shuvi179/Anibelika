package com.orion.anibelika.url;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AudioBookURLProvider implements URLProvider {

    private static final String DEFAULT_IMAGE_FORMAT = ".jpg";

    @Value("${anibelica.image.book.url.root}")
    private String bookImageRootPropertyKey;

    @Override
    public String getURLById(Long authorId) {
        return bookImageRootPropertyKey +
                authorId +
                LocalDateTime.now().withNano(0).toString().replace(":", "v") +
                DEFAULT_IMAGE_FORMAT;
    }
}
