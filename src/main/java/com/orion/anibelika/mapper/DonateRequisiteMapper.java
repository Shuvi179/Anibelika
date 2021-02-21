package com.orion.anibelika.mapper;

import com.orion.anibelika.dto.DonateRequisiteDTO;
import com.orion.anibelika.entity.DonateRequisite;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DonateRequisiteMapper {
    public DonateRequisiteDTO map(DonateRequisite donateRequisite) {
        DonateRequisiteDTO dto = new DonateRequisiteDTO();
        dto.setId(donateRequisite.getId());
        dto.setName(donateRequisite.getName());
        dto.setValue(donateRequisite.getValue());
        return dto;
    }

    public DonateRequisite map(DonateRequisiteDTO dto) {
        DonateRequisite donateRequisite = new DonateRequisite();
        donateRequisite.setId(dto.getId());
        donateRequisite.setName(dto.getName());
        donateRequisite.setValue(dto.getValue());
        return donateRequisite;
    }

    public List<DonateRequisiteDTO> mapAll(List<DonateRequisite> donateRequisites) {
        return donateRequisites.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
