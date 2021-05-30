package com.orion.anibelika.repository;

import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.entity.BookHistoryEntity;
import com.orion.anibelika.entity.DataUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface BookHistoryRepository extends JpaRepository<BookHistoryEntity, Long> {
    BookHistoryEntity getByUserAndBook(DataUser user, AudioBook book);

    @Modifying
    void removeAllByUser(DataUser user);

    @Modifying
    void removeByUserAndBookId(DataUser user, Long bookId);
}
