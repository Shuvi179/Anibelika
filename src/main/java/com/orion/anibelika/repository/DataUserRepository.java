package com.orion.anibelika.repository;

import com.orion.anibelika.entity.DataUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataUserRepository extends JpaRepository<DataUser, Long> {
    DataUser getDataUserByAuthUserId(Long id);

    boolean existsDataUserByNickNameIgnoreCase(String nickName);
}
