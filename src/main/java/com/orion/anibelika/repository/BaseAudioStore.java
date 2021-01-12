package com.orion.anibelika.repository;

import com.orion.anibelika.entity.BaseAudio;
import org.springframework.content.commons.repository.ContentStore;
import org.springframework.content.rest.StoreRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

@StoreRestResource(path = "audio")
@CrossOrigin(origins = "http://localhost:8081",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT},
        maxAge = 3600)
public interface BaseAudioStore extends ContentStore<BaseAudio, String> {
}
