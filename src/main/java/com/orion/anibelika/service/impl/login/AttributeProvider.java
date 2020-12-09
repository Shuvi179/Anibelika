package com.orion.anibelika.service.impl.login;

import java.util.HashMap;
import java.util.Map;

public class AttributeProvider {
    public static final String SOCIAL_ID = "id";
    public static final String NAME = "name";
    public static final String PASSWORD = "password";

    private static final String ID = "id";
    private static final String EMAIL = "email";

    private static final Map<String, String> FACEBOOK_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String> GOOGLE_ATTRIBUTES = new HashMap<>();

    private AttributeProvider() {
    }

    static {
        initFacebook();
        initGoogle();
    }

    public static Map<String, String> getAttribute(String clientID) {
        if (LoginClientId.FACEBOOK.getClientId().equals(clientID)) {
            return FACEBOOK_ATTRIBUTES;
        } else if (LoginClientId.GOOGLE.getClientId().equals(clientID)) {
            return GOOGLE_ATTRIBUTES;
        } else {
            throw new IllegalArgumentException("Incorrect clientID: " + clientID);
        }
    }

    private static void initFacebook() {
        FACEBOOK_ATTRIBUTES.put(SOCIAL_ID, ID);
        FACEBOOK_ATTRIBUTES.put(NAME, NAME);
        FACEBOOK_ATTRIBUTES.put(PASSWORD, LoginClientId.FACEBOOK.getClientId());
    }

    private static void initGoogle() {
        GOOGLE_ATTRIBUTES.put(SOCIAL_ID, EMAIL);
        GOOGLE_ATTRIBUTES.put(NAME, NAME);
        GOOGLE_ATTRIBUTES.put(PASSWORD, LoginClientId.GOOGLE.getClientId());
    }
}
