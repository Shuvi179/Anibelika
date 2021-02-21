package com.orion.anibelika.service;

import com.orion.anibelika.dto.DonateRequisiteDTO;

import java.util.List;

public interface DonateRequisiteService {
    DonateRequisiteDTO addNewDonateRequisite(DonateRequisiteDTO dto);

    DonateRequisiteDTO updateDonateRequisite(DonateRequisiteDTO dto);

    void deleteDonateRequisite(Long id);

    List<DonateRequisiteDTO> getAllRequisiteByUser(Long userId);
}
