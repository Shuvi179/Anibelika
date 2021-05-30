package com.orion.anibelika.service.impl;

import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.entity.BookHistoryEntity;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.repository.BookHistoryRepository;
import com.orion.anibelika.service.BookHistoryService;
import com.orion.anibelika.service.UserHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Objects;

@Service
public class BookHistoryServiceImpl implements BookHistoryService {
    private final UserHelper userHelper;
    private final BookHistoryRepository bookHistoryRepository;

    public BookHistoryServiceImpl(UserHelper userHelper, BookHistoryRepository bookHistoryRepository) {
        this.userHelper = userHelper;
        this.bookHistoryRepository = bookHistoryRepository;
    }

    @Override
    public void updateUserHistory(AudioBook audioBook) {
        if (!userHelper.isCurrentUserAuthenticated()) {
            return;
        }

        DataUser user = userHelper.getCurrentDataUser();
        BookHistoryEntity history = bookHistoryRepository.getByUserAndBook(user, audioBook);
        if (Objects.isNull(history)) {
            history = new BookHistoryEntity();
            history.setBook(audioBook);
            history.setUser(user);
        }
        history.setLastVisit(new Date());
        bookHistoryRepository.save(history);
    }

    @Override
    @Transactional
    public void removeUserHistory(Long bookId) {
        DataUser user = userHelper.getCurrentDataUser();
        bookHistoryRepository.removeByUserAndBookId(user, bookId);
    }

    @Override
    @Transactional
    public void removeAllByUser() {
        DataUser user = userHelper.getCurrentDataUser();
        bookHistoryRepository.removeAllByUser(user);
    }
}
