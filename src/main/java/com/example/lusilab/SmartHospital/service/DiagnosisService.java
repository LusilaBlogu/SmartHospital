package com.example.lusilab.SmartHospital.service;

import com.example.lusilab.SmartHospital.entity.Appointment;
import com.example.lusilab.SmartHospital.entity.Diagnosis;
import com.example.lusilab.SmartHospital.repository.AppointmentRepository;
import com.example.lusilab.SmartHospital.repository.DiagnosisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiagnosisService {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;


    public Diagnosis addDiagnosis(Long appointmentId, String description) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentId);

        if (appointmentOptional.isPresent()) {
            Appointment existingAppointment = appointmentOptional.get();

            Diagnosis diagnosis = new Diagnosis();
            diagnosis.setAppointment(existingAppointment);
            diagnosis.setDescription(description);

            return diagnosisRepository.save(diagnosis);
        } else {
            throw new RuntimeException("Takimi me ID " + appointmentId + " nuk u gjet, prandaj nuk mund të shtohet diagnoza.");
        }
    }

    public List<Diagnosis> getDiagnosisByPatientId(Long patientId) {
        return diagnosisRepository.findByPatientId(patientId);
    }


    public List<Diagnosis> getAllDiagnosis() {
        return diagnosisRepository.findAll();
    }
}