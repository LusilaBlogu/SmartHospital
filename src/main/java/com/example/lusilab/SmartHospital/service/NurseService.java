package com.example.lusilab.SmartHospital.service;

import com.example.lusilab.SmartHospital.entity.Appointment;
import com.example.lusilab.SmartHospital.entity.AppointmentStatus;
import com.example.lusilab.SmartHospital.entity.Doctor;
import com.example.lusilab.SmartHospital.entity.Patient;
import com.example.lusilab.SmartHospital.exception.ResourceNotFoundException;
import com.example.lusilab.SmartHospital.modelDTO.AppointmentDTO;
import com.example.lusilab.SmartHospital.modelDTO.PatientDTO;
import com.example.lusilab.SmartHospital.repository.AppointmentRepository;
import com.example.lusilab.SmartHospital.repository.DoctorRepository;
import com.example.lusilab.SmartHospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NurseService {


    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;


    @Autowired
    public NurseService(PatientRepository patientRepository,
                        DoctorRepository doctorRepository,
                        AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }


    @Transactional
    public Patient registerNewPatient(PatientDTO patientDto) {
        if (patientRepository.existsByEmail(patientDto.getEmail())) {
            throw new IllegalArgumentException("Një pacient me email '" + patientDto.getEmail() + "' ekziston tashmë.");
        }

        Patient newPatient = new Patient();
        newPatient.setFirstName(patientDto.getFirstName());
        newPatient.setLastName(patientDto.getLastName());
        newPatient.setEmail(patientDto.getEmail());
        newPatient.setBirthDate(patientDto.getBirthDate());
        newPatient.setGender(patientDto.getGender());

        return patientRepository.save(newPatient);
    }


    @Transactional
    public Appointment createAppointment(AppointmentDTO appointmentDto) {
        Patient patient = patientRepository.findById(appointmentDto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Pacienti me ID " + appointmentDto.getPatientId() + " nuk u gjet."));

        Doctor doctor = doctorRepository.findById(appointmentDto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doktori me ID " + appointmentDto.getDoctorId() + " nuk u gjet."));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(appointmentDto.getAppointmentTime());
        appointment.setStatus(AppointmentStatus.PLANNED);

        return appointmentRepository.save(appointment);
    }
}