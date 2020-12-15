package com.orion.anibelika.repository;

import com.orion.anibelika.entity.AuthUser;
import com.orion.anibelika.entity.DataUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataUserRepository extends JpaRepository<DataUser, Long> {
    DataUser getDataUserByAuthUser(AuthUser user);

    boolean existsDataUserByEmailOrNickName(String email, String nickName);
}
