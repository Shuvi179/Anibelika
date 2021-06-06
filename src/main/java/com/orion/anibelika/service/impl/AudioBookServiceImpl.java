package com.orion.anibelika.service.impl;

import com.orion.anibelika.dto.*;
import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.entity.Genre;
import com.orion.anibelika.exception.PermissionException;
import com.orion.anibelika.image.ImageService;
import com.orion.anibelika.mapper.BookMapper;
import com.orion.anibelika.repository.AudioBookRepository;
import com.orion.anibelika.repository.DataUserRepository;
import com.orion.anibelika.service.*;
import com.orion.anibelika.url.URLPrefix;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.*;

@Service
public class AudioBookServiceImpl implements AudioBookService {

    private final AudioBookRepository audioBookRepository;
    private final BookMapper mapper;
    private final UserHelper userHelper;
    private final ImageService imageService;
    private final BookRatingService bookRatingService;
    private final DataUserRepository dataUserRepository;
    private final GenreService genreService;
    private final BookHistoryService bookHistoryService;

    @PersistenceContext
    private EntityManager entityManager;

    public AudioBookServiceImpl(AudioBookRepository audioBookRepository, BookMapper mapper,
                                UserHelper userHelper, ImageService imageService, BookRatingService bookRatingService,
                                DataUserRepository dataUserRepository, GenreService genreService, BookHistoryService bookHistoryService) {
        this.audioBookRepository = audioBookRepository;
        this.mapper = mapper;
        this.userHelper = userHelper;
        this.imageService = imageService;
        this.bookRatingService = bookRatingService;
        this.dataUserRepository = dataUserRepository;
        this.genreService = genreService;
        this.bookHistoryService = bookHistoryService;
    }

    @Override
    public FullAudioBookInfoDTO getBookById(Long id) {
        AudioBook book = validateGetById(id);
        bookHistoryService.updateUserHistory(book);
        return mapper.map(book);
    }

    @Override
    public AudioBook getPermittedBookEntityById(Long id) {
        return validateBookById(id);
    }

    @Override
    public AudioBook getBookEntityById(Long id) {
        return validateGetById(id);
    }

    @Override
    @Transactional
    public Long addAudioBook(@NotNull DefaultAudioBookInfoDTO dto) {
        AudioBook book = validateAddBook(dto);
        AudioBook saved = audioBookRepository.save(book);
        if (!CollectionUtils.isEmpty(dto.getGenres())) {
            saved = addGenresToBook(saved, dto.getGenres());
        }
        bookRatingService.createBookRating(saved);
        saveBookImage(saved.getId(), dto.getImage());
        return book.getId();
    }

    private AudioBook addGenresToBook(AudioBook book, List<String> genres) {
        Set<Genre> currentGenres = genreService.getAllByNames(genres);
        book.setGenres(currentGenres);
        return audioBookRepository.save(book);
    }

    private AudioBook validateAddBook(@NotNull DefaultAudioBookInfoDTO dto) {
        if (Objects.nonNull(dto.getId())) {
            throw new IllegalArgumentException("book id must be null");
        }
        if (!userHelper.isCurrentUserAuthenticated()) {
            throw new PermissionException("User need to log in");
        }
        return mapper.map(dto);
    }

    @Override
    @Transactional
    public void updateAudioBook(@NotNull DefaultAudioBookInfoDTO dto) {
        AudioBook newBook = mapper.map(dto);
        AudioBook saved = audioBookRepository.save(newBook);
        addGenresToBook(saved, dto.getGenres());
    }

    private AudioBook validateBookById(Long id) {
        AudioBook currentBook = validateGetById(id);
        if (!userHelper.authenticatedWithId(currentBook.getUser().getId())) {
            throw new PermissionException("You don't have permission to access this data");
        }
        return currentBook;
    }

    private AudioBook validateGetById(Long id) {
        if (Objects.isNull(id) || id <= 0) {
            throw new IllegalArgumentException("book id is incorrect: " + id);
        }
        Optional<AudioBook> currentBook = audioBookRepository.findById(id);
        if (currentBook.isEmpty()) {
            throw new IllegalArgumentException("book id is incorrect: " + id);
        }
        return currentBook.get();
    }

    @Override
    @Transactional
    public PaginationAudioBookInfoDTO getAudioBookPage(AudioBookFilterDTO dto, Integer pageNumber, Integer numberOfElementsByPage) {
        Page<AudioBook> result = getAudioPageWithFilter(dto, pageNumber, numberOfElementsByPage);
        return new PaginationAudioBookInfoDTO(mapper.mapAll(result.getContent()), result.getTotalPages(), result.getTotalElements());
    }

    private Page<AudioBook> getAudioPageWithFilter(AudioBookFilterDTO filterDTO, Integer pageNumber, Integer numberOfElementsByPage) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<AudioBook> query = cb.createQuery(AudioBook.class);
        Root<AudioBook> book = getFilterQueryRoot(query);
        List<Predicate> predicates = new ArrayList<>();
        if (!CollectionUtils.isEmpty(filterDTO.getAuthorsNickName())) {
            predicates.add(book.get("user").get("nickName").in(filterDTO.getAuthorsNickName()));
        }
        if (!CollectionUtils.isEmpty(filterDTO.getGenres())) {
            List<Long> genreIds = genreService.getIds(filterDTO.getGenres());
            List<Long> validBookList = audioBookRepository.filterBooksByGenre(genreIds, genreIds.size());
            if (CollectionUtils.isEmpty(validBookList)) {
                return new PageImpl<>(Collections.emptyList());
            }
            predicates.add(book.get("id").in(validBookList));
        }
        if (!StringUtils.isEmpty(filterDTO.getBookName())) {
            predicates.add(cb.like(book.get("name"), "%" + filterDTO.getBookName().toLowerCase() + "%"));
        }
        if (Objects.nonNull(filterDTO.getSortBy()) && filterDTO.getSortBy() == 1L) {
            query.orderBy(cb.desc(book.get("lastUpdate")));
        } else {
            query.orderBy(cb.desc(book.get("bookRating").get("rating")));
        }
        query.where(cb.and(predicates.toArray(new Predicate[0])));
        TypedQuery<AudioBook> resultQuery = entityManager.createQuery(query);
        resultQuery.setFirstResult((pageNumber - 1) * numberOfElementsByPage);
        resultQuery.setMaxResults(numberOfElementsByPage);

        CriteriaQuery<Long> count = cb.createQuery(Long.class);
        Root<AudioBook> countRoot = getFilterCountQueryRoot(count);
        count.select(cb.countDistinct(countRoot)).where(cb.and(predicates.toArray(new Predicate[0])));
        Long haveBooks = entityManager.createQuery(count).getSingleResult();
        return new PageImpl<>(resultQuery.getResultList(), PageRequest.of(pageNumber - 1, numberOfElementsByPage), haveBooks);
    }

    private Root<AudioBook> getFilterQueryRoot(CriteriaQuery<?> query) {
        Root<AudioBook> book = query.from(AudioBook.class);
        book.fetch("user", JoinType.INNER);
        book.fetch("bookRating", JoinType.INNER);
        book.fetch("genres", JoinType.LEFT);
        return book;
    }

    private Root<AudioBook> getFilterCountQueryRoot(CriteriaQuery<?> query) {
        Root<AudioBook> book = query.from(AudioBook.class);
        book.join("user", JoinType.INNER);
        book.join("genres", JoinType.LEFT);
        return book;
    }


    @Override
    public void saveBookImage(Long id, byte[] image) {
        validateBookById(id);
        imageService.saveImage(URLPrefix.BOOK, id, image);
        imageService.saveSmallImage(URLPrefix.BOOK, id, image);
    }

    @Override
    public byte[] getBookImage(Long id) {
        return imageService.getImage(URLPrefix.BOOK, id);
    }

    @Override
    public byte[] getSmallBookImage(Long id) {
        return imageService.getSmallImage(URLPrefix.BOOK, id);
    }

    @Override
    @Transactional
    public void markBookAsFavourite(Long bookId) {
        DataUser user = userHelper.getCurrentDataUser();
        AudioBook book = validateGetById(bookId);
        user.addFavouriteBook(book);
        dataUserRepository.save(user);
    }

    @Override
    @Transactional
    public void unMarkBookAsFavourite(Long bookId) {
        DataUser user = userHelper.getCurrentDataUser();
        AudioBook book = validateGetById(bookId);
        user.removeFavouriteBook(book);
        dataUserRepository.save(user);
    }

    @Override
    @Transactional
    public void removeAllFavouriteBooksByUser() {
        DataUser user = userHelper.getCurrentDataUser();
        audioBookRepository.removeAllFavouriteBookByUserId(user.getId());
    }

    @Override
    public PaginationAudioBookInfoDTO getFavouriteBooksByPage(Integer pageNumber, Integer numberOfElementsByPage) {
        DataUser user = userHelper.getCurrentDataUser();
        Pageable request = PageRequest.of(pageNumber - 1, numberOfElementsByPage);
        Page<AudioBook> favouriteAudioBooks = audioBookRepository.findAllFavouriteByUser(user.getId(), request);
        return new PaginationAudioBookInfoDTO(mapper.mapAll(favouriteAudioBooks.getContent()), favouriteAudioBooks.getTotalPages(), favouriteAudioBooks.getTotalElements());
    }

    @Override
    public PaginationAudioBookInfoDTO getBooksHistoryByPage(Integer pageNumber, Integer numberOfElementsByPage) {
        DataUser user = userHelper.getCurrentDataUser();
        Pageable request = PageRequest.of(pageNumber - 1, numberOfElementsByPage);
        Page<AudioBook> lastViewedBooks = audioBookRepository.findBookInHistory(user, request);
        return new PaginationAudioBookInfoDTO(mapper.mapAll(lastViewedBooks.getContent()), lastViewedBooks.getTotalPages(), lastViewedBooks.getTotalElements());
    }

    @Override
    public PaginationAudioBookInfoDTO getMyBooksByPage(Integer pageNumber, Integer numberOfElementsByPage) {
        DataUser user = userHelper.getCurrentDataUser();
        Page<AudioBook> result = getAudioPageWithFilter(new AudioBookFilterDTO("", List.of(user.getNickName()),
                1L, Collections.emptyList()), pageNumber, numberOfElementsByPage);
        return new PaginationAudioBookInfoDTO(mapper.mapAll(result.getContent()), result.getTotalPages(), result.getTotalElements());
    }

    @Override
    public FullFilterDTO getFilterDto() {
        return new FullFilterDTO(genreService.getAllGenresName(), dataUserRepository.getAllAuthorsNickName());
    }

    @Override
    public MyBookCountDTO getMyBookCountDto() {
        DataUser user = userHelper.getCurrentDataUser();
        return new MyBookCountDTO(audioBookRepository.getNumberOfFavouriteBooks(user.getId()),
                audioBookRepository.getNumberOfCreatedBooks(user.getId()));
    }
}
