package com.orion.anibelika.parser;

public enum Attribute {
    SRC("src");

    private final String attr;

    Attribute(String attr) {
        this.attr = attr;
    }

    public final String getAttr() {
        return this.attr;
    }
}
