package com.orion.anibelika.service;

import com.orion.anibelika.entity.AudioBook;

public interface BookHistoryService {
    void updateUserHistory(AudioBook audioBook);

    void removeUserHistory(Long bookId);

    void removeAllByUser();
}
