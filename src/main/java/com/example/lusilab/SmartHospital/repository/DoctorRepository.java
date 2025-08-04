package com.example.lusilab.SmartHospital.repository;

import com.example.lusilab.SmartHospital.entity.Doctor;
import com.example.lusilab.SmartHospital.entity.DoctorRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {


    List<Doctor> findByDoctorRole(DoctorRole doctorRole);


    List<Doctor> findByFirstNameAndLastName(String firstName, String lastName);

    boolean existsByEmail(String email);

    Optional<Doctor> findByEmail(String email);
}