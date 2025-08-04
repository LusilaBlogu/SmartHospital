package com.example.lusilab.SmartHospital.service;

import com.example.lusilab.SmartHospital.entity.Appointment;
import com.example.lusilab.SmartHospital.entity.Diagnosis;
import com.example.lusilab.SmartHospital.entity.Patient;
import com.example.lusilab.SmartHospital.entity.Prescription;
import com.example.lusilab.SmartHospital.exception.ResourceNotFoundException;
import com.example.lusilab.SmartHospital.modelDTO.PatientDTO; // Përdorim DTO-në e saktë
import com.example.lusilab.SmartHospital.repository.AppointmentRepository;
import com.example.lusilab.SmartHospital.repository.DiagnosisRepository;
import com.example.lusilab.SmartHospital.repository.PatientRepository;
import com.example.lusilab.SmartHospital.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final PrescriptionRepository prescriptionRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository,
                          AppointmentRepository appointmentRepository,
                          DiagnosisRepository diagnosisRepository,
                          PrescriptionRepository prescriptionRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.diagnosisRepository = diagnosisRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    public List<Appointment> getAppointments(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }


    public List<Diagnosis> getDiagnoses(Long patientId) {
        return diagnosisRepository.findByPatientId(patientId);
    }


    public List<Prescription> getPrescriptions(Long patientId) {
        return prescriptionRepository.findByPatientId(patientId);
    }


    @Transactional
    public Patient updatePatient(Long patientId, PatientDTO updatedData) {

        Patient existingPatient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Pacienti me ID " + patientId + " nuk u gjet."));

        existingPatient.setFirstName(updatedData.getFirstName());
        existingPatient.setLastName(updatedData.getLastName());
        existingPatient.setEmail(updatedData.getEmail());
        existingPatient.setBirthDate(updatedData.getBirthDate());
        existingPatient.setGender(updatedData.getGender());

        return patientRepository.save(existingPatient);
    }


    public Patient getPatientById(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Pacienti me ID " + patientId + " nuk u gjet."));
    }


    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
}