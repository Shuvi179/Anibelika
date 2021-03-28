package com.orion.anibelika.repository.events;

import com.orion.anibelika.entity.BaseAudio;
import com.orion.anibelika.service.AudioBookService;
import org.springframework.content.commons.annotations.HandleBeforeSetContent;
import org.springframework.content.commons.annotations.HandleBeforeUnsetContent;
import org.springframework.content.commons.annotations.StoreEventHandler;
import org.springframework.stereotype.Component;

@Component
@StoreEventHandler
public class AudioStoreEventListener {
    private final AudioBookService audioBookService;

    public AudioStoreEventListener(AudioBookService audioBookService) {
        this.audioBookService = audioBookService;
    }

    @HandleBeforeUnsetContent
    public void handleBeforeContentDelete(BaseAudio baseAudio) {
        audioBookService.validateAudioAccess(baseAudio.getId());
    }

    @HandleBeforeSetContent
    public void handleBeforeContentUpdate(BaseAudio baseAudio) {
        audioBookService.validateAudioAccess(baseAudio.getId());
    }

//    @HandleAfterUnsetContent
//    public void handleAfterContentDelete(BaseAudio baseAudio) {
//        baseAudioRepository.delete(baseAudio);
//    }
}
