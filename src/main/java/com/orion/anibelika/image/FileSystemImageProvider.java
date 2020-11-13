package com.orion.anibelika.image;

public interface FileSystemImageProvider extends ImageProvider {
    boolean saveImage(String path, byte[] image);
}
