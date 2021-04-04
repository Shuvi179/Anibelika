package com.orion.anibelika.service.impl;

import com.orion.anibelika.dto.AudioDTO;
import com.orion.anibelika.dto.BookAudioDTO;
import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.entity.BaseAudio;
import com.orion.anibelika.repository.BaseAudioRepository;
import com.orion.anibelika.service.AudioBookService;
import com.orion.anibelika.service.BaseAudioService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BaseAudioServiceImpl implements BaseAudioService {

    private final BaseAudioRepository baseAudioRepository;
    private final AudioBookService audioBookService;

    public BaseAudioServiceImpl(BaseAudioRepository baseAudioRepository, AudioBookService audioBookService) {
        this.baseAudioRepository = baseAudioRepository;
        this.audioBookService = audioBookService;
    }

    @Override
    public Long addNewAudio(AudioDTO audioDTO, Long bookId) {
        AudioBook book = audioBookService.getPermittedBookEntityById(bookId);
        book.setLastUpdate(new Date());
        BaseAudio baseAudio = new BaseAudio();
        baseAudio.setName(audioDTO.getName());
        baseAudio.setTomeNumber(audioDTO.getTomeNumber());
        baseAudio.setBook(book);
        return baseAudioRepository.save(baseAudio).getId();
    }

    @Override
    public AudioDTO updateAudio(AudioDTO audioDTO) {
        audioBookService.validateAudioAccess(audioDTO.getId());
        BaseAudio audio = baseAudioRepository.getOne(audioDTO.getId());
        audio.setName(audioDTO.getName());
        audio.setTomeNumber(audioDTO.getTomeNumber());
        return getDto(baseAudioRepository.save(audio));
    }

    @Override
    public void removeAudio(Long audioId) {
        audioBookService.validateAudioAccess(audioId);
        baseAudioRepository.deleteById(audioId);
    }

    @Override
    public BookAudioDTO getAudioByBook(Long bookId) {
        List<BaseAudio> audioList = baseAudioRepository.getAllByBookId(bookId);
        Map<Long, List<AudioDTO>> audioMap = audioList.stream()
                .collect(Collectors.groupingBy(BaseAudio::getTomeNumber, Collectors.mapping(this::getDto, Collectors.toList())));
        return new BookAudioDTO(audioMap);
    }

    private AudioDTO getDto(BaseAudio audio) {
        return new AudioDTO(audio.getId(), audio.getName(), audio.getTomeNumber());
    }
}
