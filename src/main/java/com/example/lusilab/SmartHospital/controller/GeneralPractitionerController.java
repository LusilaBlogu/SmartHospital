package com.example.lusilab.SmartHospital.controller;

import com.example.lusilab.SmartHospital.modelDTO.DoctorDTO;
import com.example.lusilab.SmartHospital.service.GeneralPractitionerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gp")
public class GeneralPractitionerController {

    private final GeneralPractitionerService gpService;

    @Autowired
    public GeneralPractitionerController(GeneralPractitionerService gpService) {
        this.gpService = gpService;
    }


    @PostMapping
    public ResponseEntity<DoctorDTO> createGeneralPractitioner(@RequestBody DoctorDTO creationDto) {
        var createdDoctorEntity = gpService.createGeneralPractitioner(creationDto);
        DoctorDTO responseDto = convertToDto(createdDoctorEntity);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }


    @GetMapping
    public List<DoctorDTO> getAllGeneralPractitioners() {
        return gpService.getAllGeneralPractitioners().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getGeneralPractitionerById(@PathVariable Long id) {
        var gpEntity = gpService.getGeneralPractitionerById(id);
        DoctorDTO responseDto = convertToDto(gpEntity);
        return ResponseEntity.ok(responseDto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateGeneralPractitioner(@PathVariable Long id, @RequestBody DoctorDTO updateDto) {
        var updatedDoctorEntity = gpService.updateGeneralPractitioner(id, updateDto);
        DoctorDTO responseDto = convertToDto(updatedDoctorEntity);
        return ResponseEntity.ok(responseDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGeneralPractitioner(@PathVariable Long id) {
        gpService.deleteGeneralPractitioner(id);
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