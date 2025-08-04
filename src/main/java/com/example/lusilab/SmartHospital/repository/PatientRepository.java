package com.example.lusilab.SmartHospital.repository;

import com.example.lusilab.SmartHospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository  extends JpaRepository<Patient, Long> {
Optional<Patient> findByEmail(String email);


    Optional<Patient> findById(Long Id);

    boolean existsByEmail(String email);
}
