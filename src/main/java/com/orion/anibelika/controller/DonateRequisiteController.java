package com.orion.anibelika.controller;

import com.orion.anibelika.dto.DonateRequisiteDTO;
import com.orion.anibelika.service.DonateRequisiteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DonateRequisiteController {

    private final DonateRequisiteService donateRequisiteService;

    public DonateRequisiteController(DonateRequisiteService donateRequisiteService) {
        this.donateRequisiteService = donateRequisiteService;
    }

    @PostMapping("/requisite")
    @Operation(summary = "Add new donation requisite")
    public ResponseEntity<DonateRequisiteDTO> addNewRequisite(@RequestBody DonateRequisiteDTO dto) {
        return new ResponseEntity<>(donateRequisiteService.addNewDonateRequisite(dto), HttpStatus.OK);
    }

    @PutMapping("/requisite")
    @Operation(summary = "Update donation requisite")
    public ResponseEntity<DonateRequisiteDTO> updateRequisite(@RequestBody DonateRequisiteDTO dto) {
        return new ResponseEntity<>(donateRequisiteService.updateDonateRequisite(dto), HttpStatus.OK);
    }

    @DeleteMapping("/requisite/{id}")
    @Operation(summary = "Delete donation requisite by id")
    public void deleteRequisite(@PathVariable Long id) {
        donateRequisiteService.deleteDonateRequisite(id);
    }

    @GetMapping("/user/{id}/requisite")
    @Operation(summary = "Get all donation requisites by user id")
    public ResponseEntity<List<DonateRequisiteDTO>> getRequisitesByUser(@PathVariable Long id) {
        return new ResponseEntity<>(donateRequisiteService.getAllRequisiteByUser(id), HttpStatus.OK);
    }
}
