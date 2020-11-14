package com.orion.anibelika.url;

import com.orion.anibelika.helper.ApplicationPropertyDataLoader;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AudioBookURLProvider implements URLProvider {

    private final ApplicationPropertyDataLoader applicationPropertyDataLoader;

    private static final String BOOK_IMAGE_ROOT_PROPERTY_KEY = "anibelica.image.book.url.root";
    private static final String DEFAULT_IMAGE_FORMAT = ".jpg";

    public AudioBookURLProvider(ApplicationPropertyDataLoader applicationPropertyDataLoader) {
        this.applicationPropertyDataLoader = applicationPropertyDataLoader;
    }

    @Override
    public String getURLById(Long authorId) {
        return applicationPropertyDataLoader.load(BOOK_IMAGE_ROOT_PROPERTY_KEY) +
                authorId +
                LocalDateTime.now().withNano(0).toString().replace(":", "v") +
                DEFAULT_IMAGE_FORMAT;
    }
}
