package com.orion.anibelika.helper;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationPropertyDataLoader implements DataLoader {

    private final Environment environment;

    public ApplicationPropertyDataLoader(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String load(String key) {
        return environment.getProperty(key);
    }
}
