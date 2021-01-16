package com.orion.anibelika.image;

public interface ImageProvider {
    byte[] getImage(String path);

    byte[] EMPTY_RESULT = new byte[0];
}
