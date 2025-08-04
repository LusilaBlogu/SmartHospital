package com.example.lusilab.SmartHospital.service;

import com.example.lusilab.SmartHospital.entity.Doctor;
import com.example.lusilab.SmartHospital.entity.DoctorRole;
import com.example.lusilab.SmartHospital.exception.ResourceNotFoundException;
import com.example.lusilab.SmartHospital.modelDTO.DoctorDTO;
import com.example.lusilab.SmartHospital.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GeneralPractitionerService {

    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public GeneralPractitionerService(DoctorRepository doctorRepository, PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<Doctor> getAllGeneralPractitioners() {
        return doctorRepository.findByDoctorRole(DoctorRole.GENERALPRACTITIONER);
    }


    public Doctor getGeneralPractitionerById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doktori me ID " + id + " nuk u gjet."));

        if (doctor.getDoctorRole() != DoctorRole.GENERALPRACTITIONER) {
            throw new ResourceNotFoundException("Doktori me ID " + id + " nuk është Mjek i Përgjithshëm.");
        }

        return doctor;
    }
    @Transactional
    public Doctor createGeneralPractitioner(DoctorDTO dto) {
        if (doctorRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Një doktor me email '" + dto.getEmail() + "' ekziston tashmë.");
        }

        Doctor doctor = new Doctor();
        doctor.setFirstName(dto.getFirstName());
        doctor.setLastName(dto.getLastName());
        doctor.setEmail(dto.getEmail());
        doctor.setSpecialization(dto.getSpecialization());

        doctor.setDoctorRole(DoctorRole.GENERALPRACTITIONER);
        doctor.setPassword(passwordEncoder.encode(dto.getPassword()));

        return doctorRepository.save(doctor);
    }

    @Transactional
    public Doctor updateGeneralPractitioner(Long id, DoctorDTO updatedData) {
        Doctor existingDoctor = getGeneralPractitionerById(id);

        existingDoctor.setFirstName(updatedData.getFirstName());
        existingDoctor.setLastName(updatedData.getLastName());
        existingDoctor.setEmail(updatedData.getEmail());
        existingDoctor.setSpecialization(updatedData.getSpecialization());

        return doctorRepository.save(existingDoctor);
    }

    public void deleteGeneralPractitioner(Long id) {
        Doctor doctorToDelete = getGeneralPractitionerById(id);

        doctorRepository.delete(doctorToDelete);
    }
}