package com.orion.anibelika.security;

import com.orion.anibelika.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.mvc.servlet.path}")
    private String servletPath;

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final UserService userDetailsService;
    private final PasswordConfig passwordConfig;

    public ApplicationSecurityConfig(CustomAuthenticationProvider customAuthenticationProvider,
                                     UserService userDetailsService, PasswordConfig passwordConfig) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.userDetailsService = userDetailsService;
        this.passwordConfig = passwordConfig;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(customAuthenticationProvider)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordConfig.passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/css/*", "/js/*").permitAll()
                .antMatchers(servletPath + "/book/*").authenticated()
                //.antMatchers(servletPath + "/book/*").authenticated()
                .anyRequest().permitAll()
                .and().formLogin()
                .and().oauth2Login().defaultSuccessUrl(servletPath + "/oauth2Login");
    }
}
