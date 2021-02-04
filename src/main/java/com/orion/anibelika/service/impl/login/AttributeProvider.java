package com.orion.anibelika.service.impl.login;

import java.util.HashMap;
import java.util.Map;

public class AttributeProvider {
    public static final String KEY_SOCIAL_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";

    private static final String VALUE_ID = "id";
    private static final String VALUE_EMAIL = "email";
    private static final String VALUE_SUB = "sub";
    private static final String VALUE_NAME = "name";

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
        FACEBOOK_ATTRIBUTES.put(KEY_SOCIAL_ID, VALUE_ID);
        FACEBOOK_ATTRIBUTES.put(KEY_NAME, VALUE_NAME);
        FACEBOOK_ATTRIBUTES.put(KEY_PASSWORD, LoginClientId.FACEBOOK.getClientId());
        FACEBOOK_ATTRIBUTES.put(KEY_EMAIL, VALUE_EMAIL);
    }

    private static void initGoogle() {
        GOOGLE_ATTRIBUTES.put(KEY_SOCIAL_ID, VALUE_SUB);
        GOOGLE_ATTRIBUTES.put(KEY_NAME, VALUE_NAME);
        GOOGLE_ATTRIBUTES.put(KEY_PASSWORD, LoginClientId.GOOGLE.getClientId());
        GOOGLE_ATTRIBUTES.put(KEY_EMAIL, VALUE_EMAIL);
    }
}
