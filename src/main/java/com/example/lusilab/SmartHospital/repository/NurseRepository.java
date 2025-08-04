package com.example.lusilab.SmartHospital.repository;

import com.example.lusilab.SmartHospital.entity.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {

    Optional<Nurse> findByEmail(String email);


    boolean existsByEmail(String email);

    Optional<Nurse> findById(Long id);
}
