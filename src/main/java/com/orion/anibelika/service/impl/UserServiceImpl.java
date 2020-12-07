package com.orion.anibelika.service.impl;

import com.orion.anibelika.entity.AuthUser;
import com.orion.anibelika.repository.UserRepository;
import com.orion.anibelika.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository<AuthUser> userRepository;

    public UserServiceImpl(UserRepository<AuthUser> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String identification) {
        AuthUser user = userRepository.findUserByIdentificationName(identification);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("No user with email: " + identification);
        }

        return user;
    }
}
