package com.orion.anibelika.service.impl;

import com.orion.anibelika.dto.PaginationAudioBookInfoDTO;
import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.entity.FavouriteBook;
import com.orion.anibelika.mapper.BookMapper;
import com.orion.anibelika.repository.DataUserRepository;
import com.orion.anibelika.repository.FavouriteBookRepository;
import com.orion.anibelika.service.AudioBookService;
import com.orion.anibelika.service.FavouriteBookService;
import com.orion.anibelika.service.UserHelper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class FavouriteBookServiceImpl implements FavouriteBookService {
    private final UserHelper userHelper;
    private final FavouriteBookRepository favouriteBookRepository;
    private final AudioBookService audioBookService;
    private final DataUserRepository dataUserRepository;
    private final BookMapper bookMapper;

    public FavouriteBookServiceImpl(UserHelper userHelper, FavouriteBookRepository favouriteBookRepository,
                                    AudioBookService audioBookService, DataUserRepository dataUserRepository,
                                    BookMapper bookMapper) {
        this.audioBookService = audioBookService;
        this.userHelper = userHelper;
        this.favouriteBookRepository = favouriteBookRepository;
        this.dataUserRepository = dataUserRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    @Transactional
    public void markBookAsFavourite(Long bookId) {
        DataUser user = userHelper.getCurrentDataUser();
        FavouriteBook favouriteBook = favouriteBookRepository.save(getOrCreateBook(bookId));
        user.addFavouriteBook(favouriteBook);
        dataUserRepository.save(user);
    }

    private FavouriteBook getOrCreateBook(Long bookId) {
        FavouriteBook favouriteBook = favouriteBookRepository.findByBookId(bookId);
        if (Objects.isNull(favouriteBook)) {
            AudioBook book = audioBookService.getBookEntityById(bookId);
            FavouriteBook newBook = new FavouriteBook();
            newBook.setBook(book);
            return newBook;
        }
        return favouriteBook;
    }

    @Override
    @Transactional
    public void unMarkBookAsFavourite(Long bookId) {
        DataUser user = userHelper.getCurrentDataUser();
        FavouriteBook book = favouriteBookRepository.findByBookId(bookId);
        user.removeFavouriteBook(book);
        dataUserRepository.save(user);
    }

    @Override
    public PaginationAudioBookInfoDTO getFavouriteBooksByPage(Integer pageNumber, Integer numberOfElements) {
        Pageable request = PageRequest.of(pageNumber - 1, numberOfElements);
        List<AudioBook> favouriteAudioBooks = favouriteBookRepository.findAll(request)
                .getContent()
                .stream()
                .map(FavouriteBook::getBook)
                .collect(Collectors.toList());
        return new PaginationAudioBookInfoDTO(bookMapper.mapAll(favouriteAudioBooks));
    }
}
