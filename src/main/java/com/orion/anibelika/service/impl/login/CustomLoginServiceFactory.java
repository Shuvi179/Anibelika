package com.orion.anibelika.service.impl.login;

import com.orion.anibelika.service.CustomLoginService;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomLoginServiceFactory {

    private final ApplicationContext applicationContext;

    public CustomLoginServiceFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public CustomLoginService getLoginService(String clientId) {
        if (LoginClientId.FACEBOOK.getClientId().equals(clientId)) {
            return applicationContext.getBean(FacebookCustomLoginService.class);
        } else {
            throw new NoSuchBeanDefinitionException("No bean for client: " + clientId);
        }
    }
}
