package com.example.lusilab.SmartHospital.controller;

import com.example.lusilab.SmartHospital.modelDTO.PrescriptionDTO;
import com.example.lusilab.SmartHospital.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping
    public ResponseEntity<PrescriptionDTO> createPrescription(@RequestBody PrescriptionDTO creationDto) {
        var createdPrescriptionEntity = prescriptionService.addPrescription(creationDto);
        PrescriptionDTO responseDto = convertToDto(createdPrescriptionEntity);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<PrescriptionDTO> getAllPrescriptions() {
        return prescriptionService.getAllPrescriptions().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionDTO> getPrescriptionById(@PathVariable Long id) {
        var prescriptionEntity = prescriptionService.getPrescriptionById(id);
        PrescriptionDTO responseDto = convertToDto(prescriptionEntity);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/diagnosis/{diagnosisId}")
    public List<PrescriptionDTO> getPrescriptionsByDiagnosis(@PathVariable Long diagnosisId) {
        return prescriptionService.getPrescriptionsByDiagnosisId(diagnosisId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return ResponseEntity.noContent().build();
    }

    private PrescriptionDTO convertToDto(com.example.lusilab.SmartHospital.entity.Prescription prescription) {
        PrescriptionDTO dto = new PrescriptionDTO();
        dto.setId(prescription.getId());
        dto.setMedication(prescription.getMedication());
        dto.setDosage(prescription.getDosage());
        dto.setInstructions(prescription.getInstructions());
        if (prescription.getDiagnosis() != null) {
            dto.setDiagnosisId(prescription.getDiagnosis().getId());
        }
        return dto;
    }
}