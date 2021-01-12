package com.orion.anibelika.service.impl;

import com.orion.anibelika.dto.RegisterUserDTO;
import com.orion.anibelika.dto.UserDataDTO;
import com.orion.anibelika.entity.AuthUser;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.entity.social.SimpleUser;
import com.orion.anibelika.exception.RegistrationException;
import com.orion.anibelika.mapper.UserMapper;
import com.orion.anibelika.repository.DataUserRepository;
import com.orion.anibelika.repository.UserRepository;
import com.orion.anibelika.security.PasswordConfig;
import com.orion.anibelika.service.UserService;
import com.orion.anibelika.service.impl.login.LoginClientId;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository<AuthUser> userRepository;
    private final DataUserRepository dataUserRepository;
    private final PasswordConfig passwordConfig;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository<AuthUser> userRepository, DataUserRepository dataUserRepository, PasswordConfig passwordConfig,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.dataUserRepository = dataUserRepository;
        this.passwordConfig = passwordConfig;
        this.userMapper = userMapper;
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
    public UserDataDTO getUserDataById(Long id) {
        DataUser user = validateDataUserId(id);
        return userMapper.map(user);
    }

    @Override
    @Transactional
    public void updateUser(UserDataDTO userDataDTO) {
        DataUser user = validateUserDataDto(userDataDTO);
        dataUserRepository.save(user);
    }

    private DataUser validateUserDataDto(UserDataDTO userDataDTO) {
        if (Objects.isNull(userDataDTO.getId()) || userDataDTO.getId() <= 0) {
            throw new IllegalArgumentException("User id is invalid: " + userDataDTO.getId());
        }
        Optional<DataUser> user = dataUserRepository.findById(userDataDTO.getId());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("No DataUser with id: " + userDataDTO.getId());
        }
        if (!userDataDTO.getEmail().equals(user.get().getEmail())) {
            if (dataUserRepository.existsDataUserByEmail(userDataDTO.getEmail())) {
                throw new IllegalArgumentException("Email is already in use: " + userDataDTO.getEmail());
            }
        }
        if (!userDataDTO.getNickName().equals(user.get().getNickName())) {
            if (dataUserRepository.existsDataUserByNickName(userDataDTO.getNickName())) {
                throw new IllegalArgumentException("NickName is already in use: " + userDataDTO.getNickName());
            }
        }
        user.get().setEmail(userDataDTO.getEmail());
        user.get().setNickName(userDataDTO.getNickName());
        user.get().setFullName(userDataDTO.getFullName());
        user.get().getAuthUser().setIdentificationName(userDataDTO.getEmail());
        return user.get();
    }

    private DataUser validateDataUserId(Long id) {
        if (Objects.isNull(id) || id <= 0) {
            throw new IllegalArgumentException("DataUser id is incorrect: " + id);
        }
        Optional<DataUser> user = dataUserRepository.findById(id);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("No DataUser with id: " + id);
        }
        return user.get();
    }

    @Override
    @Transactional
    public void confirmUser(String uuid) {
        AuthUser user = validateUuid(uuid);
        user.setConfirmed(true);
        userRepository.save(user);
    }

    private AuthUser validateUuid(String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            throw new IllegalArgumentException("Uuid can not be empty");
        }
        AuthUser user = userRepository.findUserByEmailConfirmationUuid(uuid);
        if (Objects.isNull(user)) {
            throw new RegistrationException("Confirmation Uuid is invalid: " + uuid);
        }
        return user;
    }
}
