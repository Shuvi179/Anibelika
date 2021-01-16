package com.orion.anibelika.service.impl;

import com.orion.anibelika.dto.RegisterUserDTO;
import com.orion.anibelika.dto.UserDTO;
import com.orion.anibelika.entity.AuthUser;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.entity.SimpleUser;
import com.orion.anibelika.exception.PermissionException;
import com.orion.anibelika.exception.RegistrationException;
import com.orion.anibelika.image.ImageService;
import com.orion.anibelika.mapper.UserMapper;
import com.orion.anibelika.repository.DataUserRepository;
import com.orion.anibelika.repository.UserRepository;
import com.orion.anibelika.security.PasswordConfig;
import com.orion.anibelika.service.UserHelper;
import com.orion.anibelika.service.UserService;
import com.orion.anibelika.service.impl.login.LoginClientId;
import com.orion.anibelika.url.URLPrefix;
import com.orion.anibelika.url.URLProvider;
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
    private final UserHelper userHelper;
    private final URLProvider urlProvider;
    private final ImageService imageService;

    public UserServiceImpl(UserRepository<AuthUser> userRepository, DataUserRepository dataUserRepository, PasswordConfig passwordConfig,
                           UserMapper userMapper, UserHelper userHelper, URLProvider urlProvider, ImageService imageService) {
        this.userRepository = userRepository;
        this.dataUserRepository = dataUserRepository;
        this.passwordConfig = passwordConfig;
        this.userMapper = userMapper;
        this.userHelper = userHelper;
        this.urlProvider = urlProvider;
        this.imageService = imageService;
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
        validateEmail(registerUserDTO.getEmail());
        validateNickName(registerUserDTO.getNickName());

        DataUser dataUser = new DataUser();
        dataUser.setNickName(registerUserDTO.getNickName());
        dataUser.setEmail(registerUserDTO.getEmail());
        dataUser = dataUserRepository.save(dataUser);

        dataUser.setImageURL(urlProvider.getUri(URLPrefix.USER, dataUser.getId()));
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
    public UserDTO getUserDataById(Long id) {
        DataUser user = validateDataUserId(id);
        return userMapper.map(user);
    }

    @Override
    @Transactional
    public void updateUser(UserDTO dto) {
        DataUser user = validateUserDTO(dto);
        user.setEmail(dto.getEmail());
        user.setNickName(dto.getNickName());
        user.setFullName(dto.getFullName());
        user.getAuthUser().setIdentificationName(dto.getEmail());
        dataUserRepository.save(user);
    }

    private DataUser validateUserDTO(UserDTO dto) {
        DataUser user = validateDataUserId(dto.getId());
        validateNickNameAndEmailUpdate(dto, user);
        return user;
    }

    private DataUser validateDataUserId(Long id) {
        if (Objects.isNull(id) || id <= 0) {
            throw new IllegalArgumentException("DataUser id is incorrect: " + id);
        }
        Optional<DataUser> user = dataUserRepository.findById(id);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("No DataUser with id: " + id);
        }
        if (!userHelper.authenticatedWithId(id)) {
            throw new PermissionException("Don't have an access for this data");
        }
        return user.get();
    }

    private void validateNickNameAndEmailUpdate(UserDTO dto, DataUser user) {
        if (!dto.getEmail().equalsIgnoreCase(user.getEmail())) {
            validateEmail(dto.getEmail());
        }
        if (!dto.getNickName().equalsIgnoreCase(user.getNickName())) {
            validateNickName(dto.getNickName());
        }
    }

    private void validateEmail(String email) {
        if (dataUserRepository.existsDataUserByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException("Email is already in use: " + email);
        }
    }

    private void validateNickName(String nickName) {
        if (dataUserRepository.existsDataUserByNickNameIgnoreCase(nickName)) {
            throw new IllegalArgumentException("NickName is already in use: " + nickName);
        }
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

    @Override
    public void saveUserImage(Long id, byte[] image) {
        if (!userHelper.authenticatedWithId(id)) {
            throw new PermissionException("Don't have access to this data");
        }
        imageService.saveImage(URLPrefix.USER, id, image);
    }

    @Override
    public byte[] getUserImage(Long id) {
        return imageService.getImage(URLPrefix.USER, id);
    }
}
