package com.orion.anibelika.image;

public enum ImageFormat {
    JPG("jpg");

    private final String type;

    ImageFormat(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
