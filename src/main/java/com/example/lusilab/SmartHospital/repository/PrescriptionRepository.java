package com.example.lusilab.SmartHospital.repository;

import com.example.lusilab.SmartHospital.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findByDiagnosisId(Long diagnosisId);


    @Query("SELECT p FROM Prescription p JOIN p.diagnosis d JOIN d.appointment a WHERE a.patient.id = :patientId")
    List<Prescription> findByPatientId(@Param("patientId") Long patientId);

}