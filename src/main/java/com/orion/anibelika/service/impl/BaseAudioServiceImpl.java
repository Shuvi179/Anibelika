package com.orion.anibelika.service.impl;

import com.orion.anibelika.dto.AudioDTO;
import com.orion.anibelika.dto.BookAudioDTO;
import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.entity.BaseAudio;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.exception.PermissionException;
import com.orion.anibelika.repository.BaseAudioRepository;
import com.orion.anibelika.service.AudioBookService;
import com.orion.anibelika.service.BaseAudioService;
import com.orion.anibelika.service.UserHelper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.*;

@Service
public class BaseAudioServiceImpl implements BaseAudioService {

    private final BaseAudioRepository baseAudioRepository;
    private final AudioBookService audioBookService;
    private final UserHelper userHelper;

    public BaseAudioServiceImpl(BaseAudioRepository baseAudioRepository, AudioBookService audioBookService, UserHelper userHelper) {
        this.baseAudioRepository = baseAudioRepository;
        this.audioBookService = audioBookService;
        this.userHelper = userHelper;
    }

    @Override
    public Long addNewAudio(AudioDTO audioDTO, Long bookId) {
        AudioBook book = audioBookService.getPermittedBookEntityById(bookId);
        return addNewAudio(audioDTO, book);
    }

    @Override
    public List<Long> addNewAudioList(List<AudioDTO> audioDTOList, Long bookId) {
        AudioBook book = audioBookService.getPermittedBookEntityById(bookId);
        return audioDTOList.stream().map(audioDTO -> addNewAudio(audioDTO, book)).collect(Collectors.toList());
    }

    private Long addNewAudio(AudioDTO audioDTO, AudioBook book) {
        book.setLastUpdate(new Date());
        BaseAudio baseAudio = new BaseAudio();
        baseAudio.setName(audioDTO.getName());
        baseAudio.setTomeNumber(audioDTO.getTomeNumber());
        baseAudio.setChapterNumber(audioDTO.getChapterNumber());
        baseAudio.setBook(book);
        return baseAudioRepository.save(baseAudio).getId();
    }

    @Override
    public AudioDTO updateAudio(AudioDTO audioDTO) {
        validateAudioAccess(audioDTO.getId());
        return updateAudioPr(audioDTO);
    }

    @Override
    public List<AudioDTO> updateAudioList(List<AudioDTO> audioDTOList) {
        validateAudioListAccess(audioDTOList);
        return audioDTOList.stream().map(this::updateAudioPr).collect(toList());
    }

    private AudioDTO updateAudioPr(AudioDTO audioDTO) {
        BaseAudio audio = baseAudioRepository.getOne(audioDTO.getId());
        audio.setName(audioDTO.getName());
        audio.setTomeNumber(audioDTO.getTomeNumber());
        audio.setChapterNumber(audioDTO.getChapterNumber());
        return getDto(baseAudioRepository.save(audio));
    }

    @Override
    public void validateAudioAccess(Long audioId) {
        DataUser user = userHelper.getCurrentDataUser();
        if (baseAudioRepository.existsByIdAndUserId(audioId, user.getId())) {
            throw new PermissionException("No access to base audio");
        }
    }

    public void validateAudioListAccess(List<AudioDTO> audioDTOList) {
        List<Long> audioIds = audioDTOList.stream().map(AudioDTO::getId).collect(toList());
        DataUser user = userHelper.getCurrentDataUser();
        if (baseAudioRepository.validateListAudioIdsByUser(audioIds, user.getId()) != audioIds.size()) {
            throw new PermissionException("No access to base audio");
        }
    }

    @Override
    public void removeAudio(Long audioId) {
        validateAudioAccess(audioId);
        baseAudioRepository.deleteById(audioId);
    }

    @Override
    public BookAudioDTO getAudioByBook(Long bookId) {
        List<BaseAudio> audioList = baseAudioRepository.getAllByBookId(bookId);
        Map<Long, List<AudioDTO>> audioMap = audioList.stream()
                .collect(groupingBy(BaseAudio::getTomeNumber, mapping(this::getDto,
                        collectingAndThen(toList(), dtoList -> dtoList.stream()
                                .sorted(comparingLong(AudioDTO::getChapterNumber))
                                .collect(toList())))));
        return new BookAudioDTO(audioMap);
    }

    private AudioDTO getDto(BaseAudio audio) {
        return new AudioDTO(audio.getId(), audio.getName(), audio.getTomeNumber(), audio.getChapterNumber());
    }
}
