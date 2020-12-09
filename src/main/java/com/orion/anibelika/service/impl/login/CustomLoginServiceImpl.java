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

import static com.orion.anibelika.service.impl.login.AttributeProvider.*;

@Service
public class CustomLoginServiceImpl implements CustomLoginService {

    private final UserRepository<AuthUser> userRepository;
    private final DataUserRepository dataUserRepository;
    private final PasswordConfig passwordConfig;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    public CustomLoginServiceImpl(UserRepository<AuthUser> userRepository, DataUserRepository dataUserRepository,
                                  PasswordConfig passwordConfig, CustomAuthenticationProvider customAuthenticationProvider) {
        this.userRepository = userRepository;
        this.dataUserRepository = dataUserRepository;
        this.passwordConfig = passwordConfig;
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @Override
    @Transactional
    public void login(Map<String, Object> attributes, String clientID) {
        Map<String, String> params = getAttribute(clientID);
        String socialId = (String) attributes.get(params.get(SOCIAL_ID));
        AuthUser currentUser = userRepository.findUserByIdentificationName(socialId);
        if (Objects.isNull(currentUser)) {
            currentUser = registerUser(attributes, params);
        }
        customAuthenticationProvider.trust(currentUser);
    }

    private AuthUser registerUser(Map<String, Object> attributes, Map<String, String> param) {
        DataUser dataUser = new DataUser();
        dataUser.setFullName((String) attributes.get(param.get(NAME)));
        dataUser = dataUserRepository.save(dataUser);

        AuthUser authUser = new AuthUser();
        authUser.setIdentificationName((String) attributes.get(param.get(SOCIAL_ID)));
        authUser.setType(passwordConfig.passwordEncoder().encode(param.get(PASSWORD)));
        authUser.setConfirmed(true);
        authUser.setUser(dataUser);
        return userRepository.save(authUser);
    }
}
