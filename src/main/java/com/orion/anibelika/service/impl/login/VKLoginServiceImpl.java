package com.orion.anibelika.service.impl.login;

import com.orion.anibelika.dto.CustomUserInfoDTO;
import com.orion.anibelika.dto.VkTokenDTO;
import com.orion.anibelika.entity.AuthUser;
import com.orion.anibelika.exception.CustomUserLoginException;
import com.orion.anibelika.exception.PermissionException;
import com.orion.anibelika.repository.UserRepository;
import com.orion.anibelika.security.CustomAuthenticationProvider;
import com.orion.anibelika.service.VKLoginService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

import static com.orion.anibelika.service.impl.login.LoginClientId.VK;

@Service
public class VKLoginServiceImpl implements VKLoginService {
    private static final String REDIRECT_URL = "http://anibelika:8081/vk/redirect";
    private static final String AUTHORIZE_URL = "https://oauth.vk.com/authorize";
    private static final String ACCESS_TOKEN_URL = "https://oauth.vk.com/access_token";
    private static final String PROFILE_URL = "https://api.vk.com/method/getProfiles";
    private static final String VK_LOGIN_URL = "/vk/login";
    private static final String DISPLAY = "page";
    private static final String API_VERSION = "5.130";
    private static final String RESPONSE_TYPE = "code";

    private final UserRepository userRepository;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    public VKLoginServiceImpl(UserRepository userRepository, CustomAuthenticationProvider customAuthenticationProvider) {
        this.userRepository = userRepository;
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @Value("${anibelika.vk.client.id}")
    private String clientId;

    @Value("${anibelika.vk.client.secret}")
    private String clientSecret;

    @Override
    public RedirectView authorize() {
        URI uri = URIBuilder.fromUri(AUTHORIZE_URL)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", REDIRECT_URL)
                .queryParam("display", DISPLAY)
                .queryParam("v", API_VERSION)
                .queryParam("response_type", RESPONSE_TYPE).build();
        return new RedirectView(uri.toString());
    }

    @Override
    public RedirectView redirectToLogin(VkTokenDTO dto) {
        URI uri = URIBuilder.fromUri(VK_LOGIN_URL)
                .queryParam("token", dto.getAccess_token())
                .queryParam("userId", dto.getUser_id())
                .build();
        return new RedirectView(uri.toString());
    }

    @Override
    public void accessToken(String code) {
        URI uri = URIBuilder.fromUri(ACCESS_TOKEN_URL)
                .queryParam("CLIENT_ID", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("v", API_VERSION)
                .queryParam("redirect_uri", REDIRECT_URL)
                .queryParam("code", code)
                .build();
        HttpResponse response = sendGetRequest(uri);
        if (Objects.isNull(response) || Objects.isNull(response.getEntity())) {
            System.out.println("Response is null");
            return;
        }
        try {
            System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CustomUserInfoDTO login(String userId, String token) {
        if (!validateToken(userId, token)) {
            throw new PermissionException("No access to this data");
        }
        AuthUser currentUser = userRepository.findUserByIdentificationNameAndType(userId, VK.getClientId());
        if (Objects.isNull(currentUser)) {
            return buildUser(userId);
        }
        if (!currentUser.isEnabled()) {
            throw new CustomUserLoginException("User is not enabled");
        }
        customAuthenticationProvider.trust(currentUser);
        return null;
    }

    private boolean validateToken(String userId, String token) {
        URI uri = URIBuilder.fromUri(PROFILE_URL)
                .queryParam("v", API_VERSION)
                .queryParam("uuid", userId)
                .queryParam("access_token", token)
                .build();
        HttpResponse response = sendGetRequest(uri);
        return Objects.nonNull(response) && response.getStatusLine().getStatusCode() == 200;
    }

    private HttpResponse sendGetRequest(URI uri) {
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri);
        try {
            return client.execute(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private CustomUserInfoDTO buildUser(String userId) {
        return CustomUserInfoDTO.builder()
                .identification(userId)
                .clientId(VK.getClientId())
                .build();
    }
}
