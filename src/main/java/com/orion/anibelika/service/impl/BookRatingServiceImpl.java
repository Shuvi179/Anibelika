package com.orion.anibelika.service.impl;

import com.orion.anibelika.dto.RatingDTO;
import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.entity.BookRating;
import com.orion.anibelika.entity.BookRatingVote;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.exception.PermissionException;
import com.orion.anibelika.repository.BookRatingRepository;
import com.orion.anibelika.repository.BookRatingVoteRepository;
import com.orion.anibelika.service.BookRatingService;
import com.orion.anibelika.service.UserHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class BookRatingServiceImpl implements BookRatingService {
    private final UserHelper userHelper;
    private final BookRatingRepository bookRatingRepository;
    private final BookRatingVoteRepository bookRatingVoteRepository;
    private static final Long DEFAULT_BOOK_RATING = 3L;

    public BookRatingServiceImpl(UserHelper userHelper, BookRatingRepository bookRatingRepository,
                                 BookRatingVoteRepository bookRatingVoteRepository) {
        this.userHelper = userHelper;
        this.bookRatingRepository = bookRatingRepository;
        this.bookRatingVoteRepository = bookRatingVoteRepository;
    }

    @Override
    @Transactional
    public RatingDTO voteForBook(Long bookId, Long rating) {
        DataUser user = userHelper.getCurrentDataUser();
        BookRating bookRating = validateRating(bookId);
        BookRatingVote vote = bookRatingVoteRepository.findByUserAndBookRating(user, bookRating);
        return Objects.isNull(vote) ? addNewVote(user, rating, bookRating) : updateVote(vote, rating, bookRating);
    }

    @Override
    public RatingDTO getRating(Long bookId) {
        return getDto(validateRating(bookId));
    }

    @Override
    public Long getUserVoteByBook(Long bookId) {
        if (!userHelper.isCurrentUserAuthenticated()) {
            return DEFAULT_BOOK_RATING;
        }
        DataUser user = userHelper.getCurrentDataUser();
        return bookRatingVoteRepository.findByBookAndUserId(bookId, user.getId()).getRating();
    }

    @Override
    public void createBookRating(AudioBook audioBook) {
        BookRating rating = new BookRating();
        rating.setBook(audioBook);
        bookRatingRepository.save(rating);
    }

    private Double calculateRating(BookRating rating) {
        if (rating.getNumberOfVotes() == 0L) {
            return 0.;
        }
        return rating.getRatingSum() * 1. / rating.getNumberOfVotes();
    }

    private RatingDTO updateVote(BookRatingVote vote, Long rating, BookRating bookRating) {
        Long ratingDelta = rating - vote.getRating();
        vote.setRating(rating);
        bookRatingVoteRepository.save(vote);
        return updateBookRating(bookRating, ratingDelta, 0L);
    }

    private RatingDTO addNewVote(DataUser user, Long rating, BookRating bookRating) {
        BookRatingVote vote = new BookRatingVote();
        vote.setRating(rating);
        vote.setUser(user);
        vote.setBookRating(bookRating);
        bookRatingVoteRepository.save(vote);
        return updateBookRating(bookRating, rating, 1L);
    }

    private RatingDTO updateBookRating(BookRating bookRating, Long ratingDelta, Long userDelta) {
        bookRating.setRatingSum(bookRating.getRatingSum() + ratingDelta);
        bookRating.setNumberOfVotes(bookRating.getNumberOfVotes() + userDelta);
        bookRating.setRating(calculateRating(bookRating));
        return getDto(bookRatingRepository.save(bookRating));
    }

    private BookRating validateRating(Long bookId) {
        BookRating rating = bookRatingRepository.findByBookId(bookId);
        if (Objects.isNull(rating)) {
            throw new PermissionException("No book with id: " + bookId);
        }
        return rating;
    }

    private RatingDTO getDto(BookRating rating) {
        return new RatingDTO(rating.getRating(), rating.getNumberOfVotes());
    }
}
