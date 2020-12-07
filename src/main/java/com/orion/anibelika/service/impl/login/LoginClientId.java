package com.orion.anibelika.service.impl.login;

public enum LoginClientId {
    FACEBOOK("facebook"), GOOGLE("google"), SIMPLE("simple");

    private String clientId;

    LoginClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }
}
