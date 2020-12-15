package com.orion.anibelika.service.impl;

import com.orion.anibelika.dto.RegisterUserDTO;
import com.orion.anibelika.entity.AuthUser;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.entity.social.SimpleUser;
import com.orion.anibelika.exception.RegistrationException;
import com.orion.anibelika.repository.DataUserRepository;
import com.orion.anibelika.repository.UserRepository;
import com.orion.anibelika.security.PasswordConfig;
import com.orion.anibelika.service.UserService;
import com.orion.anibelika.service.impl.login.LoginClientId;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository<AuthUser> userRepository;
    private final DataUserRepository dataUserRepository;
    private final PasswordConfig passwordConfig;

    public UserServiceImpl(UserRepository<AuthUser> userRepository, DataUserRepository dataUserRepository, PasswordConfig passwordConfig) {
        this.userRepository = userRepository;
        this.dataUserRepository = dataUserRepository;
        this.passwordConfig = passwordConfig;
    }

    @Override
    public UserDetails loadUserByUsername(String identification) {
        AuthUser user = userRepository.findUserByIdentificationName(identification);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("No user with email: " + identification);
        }

        return user;
    }

    @Override
    @Transactional
    public AuthUser addUser(RegisterUserDTO registerUserDTO) {
        boolean emailExist = dataUserRepository.existsDataUserByEmailOrNickName(registerUserDTO.getEmail(), registerUserDTO.getNickName());
        if (emailExist) {
            throw new RegistrationException("There is an account with that email address: " + registerUserDTO.getEmail());
        }
        DataUser dataUser = new DataUser();
        dataUser.setNickName(registerUserDTO.getNickName());
        dataUser.setEmail(registerUserDTO.getEmail());
        dataUser = dataUserRepository.save(dataUser);

        SimpleUser user = new SimpleUser();
        user.setUser(dataUser);
        user.setPassword(passwordConfig.passwordEncoder().encode(registerUserDTO.getPassword()));
        user.setIdentificationName(registerUserDTO.getEmail());
        user.setType(passwordConfig.passwordEncoder().encode(LoginClientId.SIMPLE.getClientId()));
        user.setConfirmed(false);
        return userRepository.save(user);
    }

    @Override
    public DataUser getDataUser(AuthUser user) {
        return dataUserRepository.getDataUserByAuthUser(user);
    }

    @Override
    @Transactional
    public void confirmUser(String uuid) {
        AuthUser user = userRepository.findUserByEmailConfirmationUuid(uuid);
        if (Objects.isNull(user)) {
            throw new RegistrationException("Confirmation Uuid is invalid: " + uuid);
        }
        user.setConfirmed(true);
        userRepository.save(user);
    }
}
