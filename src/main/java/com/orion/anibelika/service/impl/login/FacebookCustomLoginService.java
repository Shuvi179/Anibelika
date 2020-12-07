package com.orion.anibelika.service.impl.login;

import com.orion.anibelika.entity.AuthUser;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.repository.DataUserRepository;
import com.orion.anibelika.repository.UserRepository;
import com.orion.anibelika.security.CustomAuthenticationProvider;
import com.orion.anibelika.security.PasswordConfig;
import com.orion.anibelika.service.CustomLoginService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Objects;

@Service
public class FacebookCustomLoginService implements CustomLoginService {

    private static final String ID = "id";
    private static final String NAME = "name";

    private final UserRepository<AuthUser> userRepository;
    private final DataUserRepository dataUserRepository;
    private final PasswordConfig passwordConfig;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    public FacebookCustomLoginService(UserRepository<AuthUser> userRepository, DataUserRepository dataUserRepository,
                                      PasswordConfig passwordConfig, CustomAuthenticationProvider customAuthenticationProvider) {
        this.userRepository = userRepository;
        this.dataUserRepository = dataUserRepository;
        this.passwordConfig = passwordConfig;
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @Override
    @Transactional
    public void login(Map<String, Object> attributes) {
        String socialId = (String) attributes.get(ID);
        AuthUser currentUser = userRepository.findUserByIdentificationName(socialId);
        if (Objects.isNull(currentUser)) {
            currentUser = registerUser(attributes);
            currentUser = userRepository.save(currentUser);
        }
        customAuthenticationProvider.trust(currentUser);
    }

    private AuthUser registerUser(Map<String, Object> attributes) {
        DataUser dataUser = new DataUser();
        dataUser.setNickName((String) attributes.get(NAME));
        dataUser = dataUserRepository.save(dataUser);

        AuthUser facebookUser = new AuthUser();
        facebookUser.setIdentificationName((String) attributes.get(ID));
        facebookUser.setType(passwordConfig.passwordEncoder().encode(LoginClientId.FACEBOOK.getClientId()));
        facebookUser.setConfirmed(true);
        facebookUser.setUser(dataUser);
        return facebookUser;
    }
}
