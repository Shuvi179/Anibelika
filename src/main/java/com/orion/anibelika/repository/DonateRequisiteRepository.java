package com.orion.anibelika.repository;

import com.orion.anibelika.entity.DonateRequisite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonateRequisiteRepository extends JpaRepository<DonateRequisite, Long> {
    List<DonateRequisite> getAllByUserId(Long id);
}
