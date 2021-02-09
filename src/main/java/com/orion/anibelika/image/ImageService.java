package com.orion.anibelika.image;

import com.orion.anibelika.url.URLPrefix;

public interface ImageService {
    void saveImage(URLPrefix prefix, Long id, byte[] image);

    void saveSmallImage(URLPrefix prefix, Long id, byte[] image);

    byte[] getImage(URLPrefix prefix, Long id);

    byte[] getSmallImage(URLPrefix prefix, Long id);
}
