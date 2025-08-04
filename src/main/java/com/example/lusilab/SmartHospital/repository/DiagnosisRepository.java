package com.example.lusilab.SmartHospital.repository;

import com.example.lusilab.SmartHospital.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

    Optional<Diagnosis> findByAppointmentId(Long appointmentId);


    @Query("SELECT d FROM Diagnosis d JOIN d.appointment a WHERE a.patient.id = :patientId")
    List<Diagnosis> findByPatientId(@Param("patientId") Long patientId);

    @Query("SELECT d FROM Diagnosis d WHERE d.appointment.doctor.id = :doctorId")
    List<Diagnosis> findByDoctorId(@Param("doctorId") Long doctorId);

    boolean existsByAppointmentId(Long appointmentId);

    @Query("SELECT d FROM Diagnosis d ORDER BY d.appointment.appointmentTime DESC")
    List<Diagnosis> findAllOrderByAppointmentDateDesc();

    @Query("SELECT d FROM Diagnosis d WHERE d.appointment.patient.id = :patientId ORDER BY d.appointment.appointmentTime DESC")
    List<Diagnosis> findByPatientIdOrderByAppointmentDateDesc(@Param("patientId") Long patientId);
}