package com.orion.anibelika.repository;

import com.orion.anibelika.entity.DataUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DataUserRepository extends JpaRepository<DataUser, Long> {
    DataUser getDataUserByAuthUserId(Long id);

    boolean existsDataUserByNickNameIgnoreCase(String nickName);

    @Query("select u.nickName from DataUser u where size(u.audioBooks) > 0")
    List<String> getAllAuthorsNickName();
}
