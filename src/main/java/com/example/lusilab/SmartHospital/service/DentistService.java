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
public class DentistService {

    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public DentistService(DoctorRepository doctorRepository, PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<Doctor> getAllDentists() {
        return doctorRepository.findByDoctorRole(DoctorRole.DENTIST);
    }


    public Doctor getDentistById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dentisti me ID " + id + " nuk u gjet."));

        if (doctor.getDoctorRole() != DoctorRole.DENTIST) {
            throw new ResourceNotFoundException("Doktori me ID " + id + " nuk është Dentist.");
        }

        return doctor;
    }

    @Transactional
    public Doctor createDentist(DoctorDTO dto) {
        if (doctorRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Një doktor me email '" + dto.getEmail() + "' ekziston tashmë.");
        }

        Doctor doctor = new Doctor();
        doctor.setFirstName(dto.getFirstName());
        doctor.setLastName(dto.getLastName());
        doctor.setEmail(dto.getEmail());
        doctor.setSpecialization(dto.getSpecialization());

        doctor.setDoctorRole(DoctorRole.DENTIST);
        doctor.setPassword(passwordEncoder.encode(dto.getPassword()));

        return doctorRepository.save(doctor);
    }

    @Transactional
    public Doctor updateDentist(Long id, DoctorDTO updatedData) {
        Doctor existingDoctor = getDentistById(id);

        existingDoctor.setFirstName(updatedData.getFirstName());
        existingDoctor.setLastName(updatedData.getLastName());
        existingDoctor.setEmail(updatedData.getEmail());
        existingDoctor.setSpecialization(updatedData.getSpecialization());

        return doctorRepository.save(existingDoctor);
    }


    public void deleteDentist(Long id) {
        Doctor doctorToDelete = getDentistById(id);
        doctorRepository.delete(doctorToDelete);
    }
}