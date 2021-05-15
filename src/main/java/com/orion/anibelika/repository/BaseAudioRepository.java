package com.orion.anibelika.repository;

import com.orion.anibelika.entity.BaseAudio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BaseAudioRepository extends JpaRepository<BaseAudio, Long> {
    List<BaseAudio> getAllByBookId(Long bookId);

    @Query("select count(a.id) from BaseAudio a where a.user.id = ?2 and a.id in ?1")
    Long validateListAudioIdsByUser(List<Long> audioIds, Long authorId);

    boolean existsByIdAndUserId(Long id, Long userId);
}
