package com.example.lusilab.SmartHospital.service;

import com.example.lusilab.SmartHospital.entity.Appointment;
import com.example.lusilab.SmartHospital.entity.AppointmentStatus;
import com.example.lusilab.SmartHospital.entity.Doctor;
import com.example.lusilab.SmartHospital.entity.Patient;
import com.example.lusilab.SmartHospital.exception.ResourceNotFoundException;
import com.example.lusilab.SmartHospital.modelDTO.AppointmentDTO;
import com.example.lusilab.SmartHospital.repository.AppointmentRepository;
import com.example.lusilab.SmartHospital.repository.DoctorRepository;
import com.example.lusilab.SmartHospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository,
                              DoctorRepository doctorRepository,
                              PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public Appointment createAppointment(AppointmentDTO requestDTO) {
        Doctor doctor = doctorRepository.findById(requestDTO.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doktori me ID " + requestDTO.getDoctorId() + " nuk u gjet."));

        Patient patient = patientRepository.findById(requestDTO.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Pacienti me ID " + requestDTO.getPatientId() + " nuk u gjet."));

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentTime(requestDTO.getAppointmentTime());
        appointment.setStatus(AppointmentStatus.PLANNED);

        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Long appointmentId, AppointmentDTO requestDTO) {
        Appointment appointmentToUpdate = getAppointmentById(appointmentId);

        Doctor doctor = doctorRepository.findById(requestDTO.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doktori me ID " + requestDTO.getDoctorId() + " nuk u gjet."));

        Patient patient = patientRepository.findById(requestDTO.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Pacienti me ID " + requestDTO.getPatientId() + " nuk u gjet."));

        appointmentToUpdate.setDoctor(doctor);
        appointmentToUpdate.setPatient(patient);
        appointmentToUpdate.setAppointmentTime(requestDTO.getAppointmentTime());

        return appointmentRepository.save(appointmentToUpdate);
    }


    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Takimi me ID " + appointmentId + " nuk u gjet."));
    }

    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public void deleteAppointmentById(Long appointmentId) {
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new ResourceNotFoundException("Nuk mund të fshihet. Takimi me ID " + appointmentId + " nuk u gjet.");
        }
        appointmentRepository.deleteById(appointmentId);
    }
}