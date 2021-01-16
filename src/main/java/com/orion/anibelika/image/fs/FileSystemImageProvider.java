package com.orion.anibelika.image.fs;

import com.orion.anibelika.image.ImageProvider;

public interface FileSystemImageProvider extends ImageProvider {
    void saveImage(String path, byte[] image);
}
