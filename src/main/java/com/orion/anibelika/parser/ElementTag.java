package com.orion.anibelika.parser;

public enum ElementTag {
    IMG("img"), H1("h1"), BR("br");

    private final String element;

    public String getElement() {
        return this.element;
    }

    ElementTag(String element) {
        this.element = element;
    }
}
