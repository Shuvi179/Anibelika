package com.orion.anibelika.repository;

import com.orion.anibelika.entity.BaseAudio;
import org.springframework.content.commons.repository.ContentStore;
import org.springframework.content.rest.StoreRestResource;

@StoreRestResource(path = "audio")
public interface BaseAudioStore extends ContentStore<BaseAudio, String> {
}
