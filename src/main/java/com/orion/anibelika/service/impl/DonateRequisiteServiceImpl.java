package com.orion.anibelika.service.impl;

import com.orion.anibelika.dto.DonateRequisiteDTO;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.entity.DonateRequisite;
import com.orion.anibelika.exception.PermissionException;
import com.orion.anibelika.mapper.DonateRequisiteMapper;
import com.orion.anibelika.repository.DonateRequisiteRepository;
import com.orion.anibelika.service.DonateRequisiteService;
import com.orion.anibelika.service.UserHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DonateRequisiteServiceImpl implements DonateRequisiteService {

    private final DonateRequisiteMapper mapper;
    private final DonateRequisiteRepository requisiteRepository;
    private final UserHelper userHelper;

    public DonateRequisiteServiceImpl(DonateRequisiteMapper mapper, DonateRequisiteRepository requisiteRepository,
                                      UserHelper userHelper) {
        this.mapper = mapper;
        this.requisiteRepository = requisiteRepository;
        this.userHelper = userHelper;
    }

    @Override
    public DonateRequisiteDTO addNewDonateRequisite(DonateRequisiteDTO dto) {
        DataUser user = userHelper.getCurrentDataUser();
        DonateRequisite requisite = mapper.map(dto);
        requisite.setUser(user);
        return mapper.map(requisiteRepository.save(requisite));
    }

    @Override
    @Transactional
    public DonateRequisiteDTO updateDonateRequisite(DonateRequisiteDTO dto) {
        DonateRequisite previous = validateUpdate(dto.getId());
        DonateRequisite current = mapper.map(dto);
        current.setUser(previous.getUser());
        return mapper.map(requisiteRepository.save(current));
    }

    @Override
    @Transactional
    public void deleteDonateRequisite(Long id) {
        DonateRequisite current = validateUpdate(id);
        requisiteRepository.delete(current);
    }

    @Override
    public List<DonateRequisiteDTO> getAllRequisiteByUser(Long userId) {
        return mapper.mapAll(requisiteRepository.getAllByUserId(userId));
    }

    private DonateRequisite validateUpdate(Long id) {
        if (Objects.isNull(id) || id <= 0) {
            throw new IllegalArgumentException("Invalid donate requisite id: " + id);
        }
        Optional<DonateRequisite> donateRequisite = requisiteRepository.findById(id);
        if (donateRequisite.isEmpty()) {
            throw new IllegalArgumentException("No donate requisite with id: " + id);
        }
        if (!userHelper.authenticatedWithId(donateRequisite.get().getUser().getId())) {
            throw new PermissionException("No permission for this data");
        }
        return donateRequisite.get();
    }
}
