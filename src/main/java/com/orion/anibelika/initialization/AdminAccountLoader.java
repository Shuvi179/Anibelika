package com.orion.anibelika.initialization;

import com.orion.anibelika.entity.AuthUser;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.entity.Role;
import com.orion.anibelika.repository.DataUserRepository;
import com.orion.anibelika.repository.RoleRepository;
import com.orion.anibelika.repository.UserRepository;
import com.orion.anibelika.security.PasswordConfig;
import com.orion.anibelika.service.impl.login.LoginClientId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Set;

import static com.orion.anibelika.security.ApplicationSecurityRole.ROLE_ADMIN;
import static com.orion.anibelika.security.ApplicationSecurityRole.getRole;

@Component
public class AdminAccountLoader implements ApplicationRunner {

    @Value("${admin.account.email}")
    private String email;

    @Value("${admin.account.nickname}")
    private String nickName;

    @Value("${admin.account.password}")
    private String password;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordConfig passwordConfig;
    private final DataUserRepository dataUserRepository;

    public AdminAccountLoader(UserRepository userRepository, RoleRepository roleRepository,
                              PasswordConfig passwordConfig, DataUserRepository dataUserRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordConfig = passwordConfig;
        this.dataUserRepository = dataUserRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        Role adminRole = roleRepository.getRoleByName(ROLE_ADMIN.name());
        if (Objects.isNull(adminRole)) {
            adminRole = roleRepository.save(getRole(ROLE_ADMIN));
        }

        AuthUser admin = userRepository.findUserByEmail(email);
        if (Objects.isNull(admin)) {
            DataUser dataUser = new DataUser();
            dataUser.setNickName(nickName);
            dataUser = dataUserRepository.save(dataUser);

            admin = new AuthUser();
            admin.setIdentificationName(null);
            admin.setEmail(email);
            admin.setType(LoginClientId.SIMPLE.getClientId());
            admin.setPassword(passwordConfig.passwordEncoder().encode(password));
            admin.setConfirmed(true);
            admin.setRoles(Set.of(adminRole));
            admin.setUser(dataUser);
        } else {
            admin.setIdentificationName(null);
            admin.getUser().setNickName(nickName);
            admin.setPassword(passwordConfig.passwordEncoder().encode(password));
        }
        userRepository.save(admin);
    }
}
