package com.example.lusilab.SmartHospital.service;

import com.example.lusilab.SmartHospital.entity.Diagnosis;
import com.example.lusilab.SmartHospital.entity.Prescription;
import com.example.lusilab.SmartHospital.exception.ResourceNotFoundException;
import com.example.lusilab.SmartHospital.modelDTO.PrescriptionDTO;
import com.example.lusilab.SmartHospital.repository.DiagnosisRepository;
import com.example.lusilab.SmartHospital.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final DiagnosisRepository diagnosisRepository;

    @Autowired
    public PrescriptionService(PrescriptionRepository prescriptionRepository,
                               DiagnosisRepository diagnosisRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.diagnosisRepository = diagnosisRepository;
    }


    @Transactional
    public Prescription addPrescription(PrescriptionDTO dto) {
        Diagnosis diagnosis = diagnosisRepository.findById(dto.getDiagnosisId())
                .orElseThrow(() -> new ResourceNotFoundException("Diagnoza me ID " + dto.getDiagnosisId() + " nuk u gjet."));

        Prescription prescription = new Prescription();
        prescription.setDiagnosis(diagnosis);
        prescription.setMedication(dto.getMedication());
        prescription.setDosage(dto.getDosage());
        prescription.setInstructions(dto.getInstructions());

        return prescriptionRepository.save(prescription);
    }


    public List<Prescription> getPrescriptionsByDiagnosisId(Long diagnosisId) {
        return prescriptionRepository.findByDiagnosisId(diagnosisId);
    }


    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }


    public Prescription getPrescriptionById(Long prescriptionId) {
        return prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Receta me ID " + prescriptionId + " nuk u gjet."));
    }

    public void deletePrescription(Long prescriptionId) {
        if (!prescriptionRepository.existsById(prescriptionId)) {
            throw new ResourceNotFoundException("Nuk mund të fshihet. Receta me ID " + prescriptionId + " nuk u gjet.");
        }
        prescriptionRepository.deleteById(prescriptionId);
    }
}