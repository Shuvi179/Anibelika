package com.orion.anibelika.repository;

import com.orion.anibelika.entity.BaseAudio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseAudioRepository extends JpaRepository<BaseAudio, Long> {
    List<BaseAudio> getAllByBookId(Long bookId);
}
