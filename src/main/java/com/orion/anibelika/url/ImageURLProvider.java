package com.orion.anibelika.url;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageURLProvider implements URLProvider {

    private static final String DEFAULT_IMAGE_FORMAT = ".jpg";
    private static final String SMALL_PREFIX = "/small";
    private static final String DEFAULT_PREFIX = "/default";

    @Value("${anibelica.image.url.root}")
    private String imageRootPropertyKey;

    @Override
    public String getPath(URLPrefix prefix, Long id) {
        return imageRootPropertyKey + DEFAULT_PREFIX + getUri(prefix, id);
    }

    @Override
    public String getSmallPath(URLPrefix prefix, Long id) {
        return imageRootPropertyKey + SMALL_PREFIX + getUri(prefix, id);
    }

    @Override
    public String getUri(URLPrefix prefix, Long id) {
        return prefix.getPrefix() + id + DEFAULT_IMAGE_FORMAT;
    }
}
