package com.orion.anibelika.service.impl.login;

import com.orion.anibelika.dto.CustomUserInfoDTO;
import com.orion.anibelika.entity.AuthUser;
import com.orion.anibelika.repository.UserRepository;
import com.orion.anibelika.security.CustomAuthenticationProvider;
import com.orion.anibelika.service.CustomLoginService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Objects;

import static com.orion.anibelika.service.impl.login.AttributeProvider.*;

@Service
public class CustomLoginServiceImpl implements CustomLoginService {

    private final UserRepository<AuthUser> userRepository;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    public CustomLoginServiceImpl(UserRepository<AuthUser> userRepository,
                                  CustomAuthenticationProvider customAuthenticationProvider) {
        this.userRepository = userRepository;
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @Override
    @Transactional
    public CustomUserInfoDTO login(Map<String, Object> attributes, String clientID) {
        Map<String, String> params = getAttribute(clientID);
        String socialId = (String) attributes.get(params.get(KEY_SOCIAL_ID));
        AuthUser currentUser = userRepository.findUserByIdentificationNameAndType(socialId, clientID);
        if (Objects.isNull(currentUser)) {
            return buildNewUser(attributes, params);
        }
        customAuthenticationProvider.trust(currentUser);
        return null;
    }

    private CustomUserInfoDTO buildNewUser(Map<String, Object> attributes, Map<String, String> param) {
        return CustomUserInfoDTO.builder()
                .clientId(param.get(KEY_PASSWORD))
                .email((String) attributes.get(param.get(KEY_EMAIL)))
                .identification((String) attributes.get(param.get(KEY_SOCIAL_ID))).build();
    }
}
