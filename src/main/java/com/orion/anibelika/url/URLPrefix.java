package com.orion.anibelika.url;

public enum URLPrefix {
    BOOK("/book/"), USER("/user/");

    private final String prefix;

    URLPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
