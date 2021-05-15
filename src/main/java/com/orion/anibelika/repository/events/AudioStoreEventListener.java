package com.orion.anibelika.repository.events;

import com.orion.anibelika.entity.BaseAudio;
import com.orion.anibelika.service.BaseAudioService;
import org.springframework.content.commons.annotations.HandleBeforeSetContent;
import org.springframework.content.commons.annotations.HandleBeforeUnsetContent;
import org.springframework.content.commons.annotations.StoreEventHandler;
import org.springframework.stereotype.Component;

@Component
@StoreEventHandler
public class AudioStoreEventListener {
    private final BaseAudioService baseAudioService;

    public AudioStoreEventListener(BaseAudioService baseAudioService) {
        this.baseAudioService = baseAudioService;
    }

    @HandleBeforeUnsetContent
    public void handleBeforeContentDelete(BaseAudio baseAudio) {
        baseAudioService.validateAudioAccess(baseAudio.getId());
    }

    @HandleBeforeSetContent
    public void handleBeforeContentUpdate(BaseAudio baseAudio) {
        baseAudioService.validateAudioAccess(baseAudio.getId());
    }
}
