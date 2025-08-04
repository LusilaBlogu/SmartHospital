package com.example.lusilab.SmartHospital.repository;

import com.example.lusilab.SmartHospital.entity.Appointment;
import com.example.lusilab.SmartHospital.entity.Doctor;
import com.example.lusilab.SmartHospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.doctor = :doctor AND DATE(a.appointmentTime) = :date")
    List<Appointment> findByDoctorAndDate(@Param("doctor") Doctor doctor, @Param("date") LocalDate date);

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByDoctorId(Long doctorId);

    @Query("SELECT a FROM Appointment a WHERE a.patient = :patient AND DATE(a.appointmentTime) = :date")
    List<Appointment> findByPatientAndDate(@Param("patient") Patient patient, @Param("date") LocalDate date);
}