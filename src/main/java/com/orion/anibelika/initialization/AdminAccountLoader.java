package com.orion.anibelika.initialization;

import com.orion.anibelika.entity.Role;
import com.orion.anibelika.entity.User;
import com.orion.anibelika.helper.DataLoader;
import com.orion.anibelika.repository.RoleRepository;
import com.orion.anibelika.repository.UserRepository;
import com.orion.anibelika.security.PasswordConfig;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

import static com.orion.anibelika.security.ApplicationSecurityRole.ROLE_ADMIN;
import static com.orion.anibelika.security.ApplicationSecurityRole.getRole;

@Component
public class AdminAccountLoader implements ApplicationRunner {

    private static final String ADMIN_EMAIL_KEY = "admin.account.email";
    private static final String ADMIN_NICKNAME_KEY = "admin.account.nickname";
    private static final String ADMIN_PASSWORD_KEY = "admin.account.password";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordConfig passwordConfig;
    private final DataLoader dataLoader;

    public AdminAccountLoader(UserRepository userRepository, RoleRepository roleRepository,
                              PasswordConfig passwordConfig, DataLoader dataLoader) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordConfig = passwordConfig;
        this.dataLoader = dataLoader;
    }

    @Override
    public void run(ApplicationArguments args) {
        Role adminRole = roleRepository.getRoleByName(ROLE_ADMIN.name());
        if (Objects.isNull(adminRole)) {
            adminRole = roleRepository.save(getRole(ROLE_ADMIN));
        }

        String email = dataLoader.load(ADMIN_EMAIL_KEY);
        String nickName = dataLoader.load(ADMIN_NICKNAME_KEY);
        String passWord = dataLoader.load(ADMIN_PASSWORD_KEY);

        User admin = userRepository.findUserByEmail(email);
        if (Objects.isNull(admin)) {
            admin = new User();
            admin.setEmail(email);
            admin.setNickName(nickName);
            admin.setPassword(passwordConfig.passwordEncoder().encode(passWord));
            admin.setConfirmed(true);
            admin.setRoles(Set.of(adminRole));
        } else {
            admin.setEmail(email);
            admin.setNickName(nickName);
            admin.setPassword(passwordConfig.passwordEncoder().encode(passWord));
        }
        userRepository.save(admin);
    }
}
