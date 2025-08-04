package com.example.lusilab.SmartHospital.modelDTO;


import java.time.LocalDateTime;

public class AppointmentDTO {

    private Long patientId;
    private Long doctorId;
    private LocalDateTime appointmentTime;

    public AppointmentDTO() {
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }


}