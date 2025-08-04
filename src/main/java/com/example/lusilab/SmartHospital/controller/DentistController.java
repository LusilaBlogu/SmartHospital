package com.example.lusilab.SmartHospital.controller;

import com.example.lusilab.SmartHospital.modelDTO.DoctorDTO;
import com.example.lusilab.SmartHospital.service.DentistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dentists")
public class DentistController {

    private final DentistService dentistService;

    @Autowired
    public DentistController(DentistService dentistService) {
        this.dentistService = dentistService;
    }


    @PostMapping
    public ResponseEntity<DoctorDTO> createDentist(@RequestBody DoctorDTO creationDto) {
        var createdDoctorEntity = dentistService.createDentist(creationDto);
        DoctorDTO responseDto = convertToDto(createdDoctorEntity);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<DoctorDTO> getAllDentists() {
        return dentistService.getAllDentists().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDentistById(@PathVariable Long id) {
        var dentistEntity = dentistService.getDentistById(id);
        DoctorDTO responseDto = convertToDto(dentistEntity);
        return ResponseEntity.ok(responseDto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateDentist(@PathVariable Long id, @RequestBody DoctorDTO updateDto) {
        var updatedDoctorEntity = dentistService.updateDentist(id, updateDto);
        DoctorDTO responseDto = convertToDto(updatedDoctorEntity);
        return ResponseEntity.ok(responseDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDentist(@PathVariable Long id) {
        dentistService.deleteDentist(id);
        return ResponseEntity.noContent().build();
    }


    private DoctorDTO convertToDto(com.example.lusilab.SmartHospital.entity.Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setFirstName(doctor.getFirstName());
        dto.setLastName(doctor.getLastName());
        dto.setEmail(doctor.getEmail());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setDoctorRole(doctor.getDoctorRole());

        return dto;
    }
}