package com.orion.anibelika.repository;

import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.entity.DataUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AudioBookRepository extends JpaRepository<AudioBook, Long> {
    @Override
    @Query(value = "select b from AudioBook as b join fetch b.bookRating join fetch b.user left join fetch b.genres",
            countQuery = "select count(b) from AudioBook as b")
    Page<AudioBook> findAll(Pageable pageable);

    @Query(value = "select b from DataUser as u join u.favouriteBooks b join fetch b.bookRating br where u.id = ?1",
            countQuery = "select count(b) from DataUser as u join u.favouriteBooks b where u.id = ?1")
    Page<AudioBook> findAllFavouriteByUser(Long userId, Pageable pageable);

    @Query(value = "select b from AudioBook as b join fetch b.bookRating join fetch b.user left join fetch b.genres join b.bookHistory as h " +
            "where h.user = ?1 order by h.lastVisit desc ",
            countQuery = "select count(bhe.book) from BookHistoryEntity bhe where bhe.user = ?1")
    Page<AudioBook> findBookInHistory(DataUser user, Pageable pageable);

    @Query(value = "select count(b) from AudioBook as b join b.user as u join b.audios as a where a.id = ?1 and u.id = ?2")
    Integer countBookByUserAndAudio(Long audioId, Long userId);

    @Query(value = "SELECT b.id " +
            "FROM books b JOIN books_genres bg ON b.id = bg.audio_book_id " +
            "WHERE bg.genres_id IN ?1 " +
            "GROUP BY b.id " +
            "HAVING ( COUNT(b.id) >= ?2 )", nativeQuery = true)
    List<Long> filterBooksByGenre(List<Long> genreIds, Integer genreSize);

    @Query(value = "select count(*) from data_user_favourite_books as fb where fb.data_user_id = ?1 and fb.favourite_books_id = ?2",
            nativeQuery = true)
    Long isBookFavouriteByUser(Long userId, Long bookId);

    @Query(value = "select dufb.favourite_books_id, count(dufb.data_user_id) from data_user_favourite_books as dufb where dufb.favourite_books_id in ?1 group by dufb.favourite_books_id", nativeQuery = true)
    List<Long[]> getNumberOfUserSelectedAsFavourite(List<Long> ids);

    @Query(value = "delete from data_user_favourite_books as dufb where dufb.data_user_id = ?1", nativeQuery = true)
    @Modifying
    void removeAllFavouriteBookByUserId(Long userId);

    @Query(value = "select u.favouriteBooks.size from DataUser u where u.id = ?1")
    Integer getNumberOfFavouriteBooks(Long userId);

    @Query(value = "select u.audioBooks.size from DataUser u where u.id = ?1")
    Integer getNumberOfCreatedBooks(Long userId);
}
