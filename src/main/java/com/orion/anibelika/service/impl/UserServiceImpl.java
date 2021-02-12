package com.orion.anibelika.service.impl;

import com.orion.anibelika.dto.NewUserDTO;
import com.orion.anibelika.dto.PasswordResetDTO;
import com.orion.anibelika.dto.UpdatePasswordDTO;
import com.orion.anibelika.dto.UserDTO;
import com.orion.anibelika.entity.AuthUser;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.exception.PermissionException;
import com.orion.anibelika.exception.RegistrationException;
import com.orion.anibelika.exception.UserNotFoundException;
import com.orion.anibelika.image.ImageService;
import com.orion.anibelika.mapper.UserMapper;
import com.orion.anibelika.repository.DataUserRepository;
import com.orion.anibelika.repository.UserRepository;
import com.orion.anibelika.security.PasswordConfig;
import com.orion.anibelika.service.PasswordResetService;
import com.orion.anibelika.service.UserHelper;
import com.orion.anibelika.service.UserService;
import com.orion.anibelika.url.URLPrefix;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DataUserRepository dataUserRepository;
    private final PasswordConfig passwordConfig;
    private final UserMapper userMapper;
    private final UserHelper userHelper;
    private final ImageService imageService;
    private final PasswordResetService passwordResetService;

    public UserServiceImpl(UserRepository userRepository, DataUserRepository dataUserRepository, PasswordConfig passwordConfig,
                           UserMapper userMapper, UserHelper userHelper, ImageService imageService,
                           PasswordResetService passwordResetService) {
        this.userRepository = userRepository;
        this.dataUserRepository = dataUserRepository;
        this.passwordConfig = passwordConfig;
        this.userMapper = userMapper;
        this.userHelper = userHelper;
        this.imageService = imageService;
        this.passwordResetService = passwordResetService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new UsernameNotFoundException("email is invalid: " + email);
        }
        AuthUser user = userRepository.findUserByEmail(email);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("No user with email: " + email);
        }
        return user;
    }

    @Override
    @Transactional
    public DataUser addUser(NewUserDTO newUserDTO) {
        validateEmail(newUserDTO.getEmail());
        validateNickName(newUserDTO.getNickName());

        AuthUser user = new AuthUser();
        user.setPassword(passwordConfig.passwordEncoder().encode(newUserDTO.getPassword()));
        user.setEmail(newUserDTO.getEmail());
        user.setIdentificationName(newUserDTO.getIdentification());
        user.setType(newUserDTO.getType());
        user.setConfirmed(false);
        user = userRepository.save(user);

        DataUser dataUser = new DataUser();
        dataUser.setNickName(newUserDTO.getNickName());
        dataUser.setAuthUser(user);
        dataUser = dataUserRepository.save(dataUser);
        user.setUser(dataUser);
        return dataUser;
    }

    @Override
    public UserDTO getUserDataById(Long id) {
        DataUser user = validateDataUserId(id);
        return userMapper.map(user);
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

    private void validateEmail(String email) {
        if (userRepository.existsUserByEmail(email)) {
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
        imageService.saveSmallImage(URLPrefix.USER, id, image);
    }

    @Override
    public byte[] getUserImage(Long id) {
        return imageService.getImage(URLPrefix.USER, id);
    }

    @Override
    public byte[] getSmallUserImage(Long id) {
        return imageService.getSmallImage(URLPrefix.USER, id);
    }

    @Override
    public void startResetPasswordProcess(String email) {
        if (!userRepository.existsUserByEmail(email)) {
            throw new UserNotFoundException("No user with email: " + email);
        }
        passwordResetService.createResetEvent(userRepository.findUserByEmail(email));
    }

    @Override
    @Transactional
    public void resetUserPassword(String uuid, PasswordResetDTO dto) {
        AuthUser user = passwordResetService.validateResetToken(uuid);
        updatePassword(user, dto.getPassword());
        passwordResetService.deleteResetToken(uuid);
    }

    @Override
    @Transactional
    public void updateUserPassword(UpdatePasswordDTO dto) {
        AuthUser user = userHelper.getCurrentUser();
        validatePassword(dto.getOldPassword(), user.getPassword());
        updatePassword(user, dto.getNewPassword());
    }

    @Override
    @Transactional
    public void updateUserNickName(String nickName) {
        DataUser user = userHelper.getCurrentDataUser();
        if (!user.getNickName().equalsIgnoreCase(nickName)) {
            validateNickName(nickName);
        }
        user.setNickName(nickName);
        dataUserRepository.save(user);
    }

    private void updatePassword(AuthUser user, String password) {
        user.setPassword(passwordConfig.passwordEncoder().encode(password));
        userRepository.save(user);
    }

    private void validatePassword(String password, String encodedPassword) {
        if (!passwordConfig.passwordEncoder().matches(password, encodedPassword)) {
            throw new PermissionException("Password mismatch");
        }
    }
}
