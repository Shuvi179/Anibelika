package com.orion.anibelika.repository;

import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.entity.BookHistoryEntity;
import com.orion.anibelika.entity.DataUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookHistoryRepository extends JpaRepository<BookHistoryEntity, Long> {
    BookHistoryEntity getByUserAndBook(DataUser user, AudioBook book);
}
