package com.orion.anibelika.url;

public interface URLProvider {
    String getPath(URLPrefix prefix, Long id);

    String getSmallPath(URLPrefix prefix, Long id);

    String getUri(URLPrefix prefix, Long id);
}
