package com.example.lusilab.SmartHospital.service;

import com.example.lusilab.SmartHospital.entity.Appointment;
import com.example.lusilab.SmartHospital.entity.Diagnosis;
import com.example.lusilab.SmartHospital.entity.Prescription;
import com.example.lusilab.SmartHospital.exception.ResourceNotFoundException;
import com.example.lusilab.SmartHospital.repository.AppointmentRepository;
import com.example.lusilab.SmartHospital.repository.DiagnosisRepository;
import com.example.lusilab.SmartHospital.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorService {

    private final AppointmentRepository appointmentRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final DiagnosisRepository diagnosisRepository;


    @Autowired
    public DoctorService(AppointmentRepository appointmentRepository,
                         PrescriptionRepository prescriptionRepository,
                         DiagnosisRepository diagnosisRepository) {
        this.appointmentRepository = appointmentRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.diagnosisRepository = diagnosisRepository;
    }


    public List<Appointment> getDoctorAppointments(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }


    @Transactional
    public Long addDiagnosisAndReturnPatientId(Long appointmentId, String description) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Takimi me ID " + appointmentId + " nuk u gjet."));

        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setAppointment(appointment);
        diagnosis.setDescription(description);

        diagnosisRepository.save(diagnosis);

        return appointment.getPatient().getId();
    }


    @Transactional
    public Long addPrescriptionAndReturnPatientId(Long diagnosisId, String medication, String dosage) {
        Diagnosis diagnosis = diagnosisRepository.findById(diagnosisId)
                .orElseThrow(() -> new ResourceNotFoundException("Diagnoza me ID " + diagnosisId + " nuk u gjet."));

        Prescription prescription = new Prescription();
        prescription.setDiagnosis(diagnosis);
        prescription.setMedication(medication);
        prescription.setDosage(dosage);

        prescriptionRepository.save(prescription);


        return diagnosis.getAppointment().getPatient().getId();
    }


    public List<Diagnosis> getPatientMedicalHistory(Long patientId) {
        return diagnosisRepository.findByPatientIdOrderByAppointmentDateDesc(patientId);
    }
}