package com.orion.anibelika.parser;

import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;

public interface ParseService {
    DefaultAudioBookInfoDTO parseBookByUrl(String url);
}
