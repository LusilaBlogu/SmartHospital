package com.example.lusilab.SmartHospital.modelDTO;

import com.example.lusilab.SmartHospital.entity.Appointment;

public class DiadnosisDTO {

    private String description;
    private Appointment appointment;

    public DiadnosisDTO(String description, Appointment appointment) {
        this.description = description;
        this.appointment = appointment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
